package dm.models;

import dm.models.Entity;
import dm.models.Relation;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;

public class HashFile {
    
    //Padrão Iterator foi usado nesta classe, para percorrer os registros seguindo
    //a ordem de cálculo do hash

    class TupleIterator implements Iterator<Tuple> {

        private int step;
        private int position;
        private Value value;
        private int antecessorHash;

        public TupleIterator(Value value) {
            step = 0;
            this.value = value;
        }

        public int hash() {
            if (step == 0) {
                antecessorHash = value.getHash() % size;
            }
            else {
                antecessorHash = (antecessorHash + step) % size;//(hash(valor, passo - 1) + passo) % tamanho();
            }
            return antecessorHash;
        }

        public boolean hasNext() {
            return step < size;
        }

        public Tuple next() {
            position = hash();
            step += 1;
            try {
                return readTuple(position);
            } catch (IOException ex) {
                return null;
            }
        }

        public void endLoop() {
            step = size;
        }

        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public int getPosition() {
            return position;
        }
    }

    class IterableTuple implements Iterable<Tuple> {

        private TupleIterator iterator = null;

        public IterableTuple(Value value) {
            iterator = new TupleIterator(value);
        }

        public Iterator<Tuple> iterator() {
            return iterator;
        }

        public int getPosition() {
            return iterator.getPosition();
        }

        public void endLoop() {
            iterator.endLoop();
        }
    }
   
    

    
    private Entity entity;
    private int size;
    private RandomAccessFile file;
    private int tupleSize;
    private String prefix = "";
    private ConsistencyStrategy strategy = null;

    public HashFile(Entity entity) {
        this.entity = entity;
        this.file = null;
        this.tupleSize = entity.getSize() + 4;
        this.strategy = new Restrict(this);
    }

    public void open(String prefix, int size) throws IOException {
        this.setPrefix(prefix);
        File f = new File(prefix + entity.getName() + ".dat");
        this.setFile(new RandomAccessFile(f, "rw"));
        if (f.length() == 0) {
            this.size = size;
            this.create();
        }
        else {
            this.size = (int) (f.length() / tupleSize);
        }
        this.countTuples();
    }

    public void open(String prefix) throws IOException {
        this.open(prefix, 8);
    }

    public void open() throws IOException {
        this.open("");
    }

    public void close() throws IOException {
        this.getFile().close();
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void seek(int position) throws IOException {
        file.seek(position * tupleSize);
    }

    public Tuple readTuple() throws IOException {
        return new Tuple(entity, file);
    }

    public Tuple readTuple(int position) throws IOException {
        this.seek(position);
        return this.readTuple();
    }

    public void saveTuple(Tuple tuple) throws IOException {
        tuple.save(file);
    }

    public void saveTuple(Tuple tuple, int position) throws IOException {
        this.seek(position);
        tuple.save(file);
    }

    public Result find(Value value) throws IOException {
        Result resp = null;
        Result result = null;
        int removed = -1;

        IterableTuple iterator = new IterableTuple(value);
        for (Tuple tuple : iterator) {
            int position = iterator.getPosition();

            if (tuple.isEmpty()) {
                resp = result != null ? result : new Result(removed == -1 ? position : removed, false);
                iterator.endLoop();
            }
            if (tuple.getPK().equals(value)) {
                if (result == null) {
                    result = new Result(position, !tuple.isRemoved());
                }
                else if (!tuple.isRemoved() && !result.isFound()) {
                    result = new Result(position, true);
                }
            }
            if (tuple.isRemoved() && removed == -1) {
                removed = position;
            }

        }
        if (resp == null) {
            resp = result != null ? result : new Result(removed, false);
        }
        return resp;
    }

    public boolean insert(Tuple tuple, boolean ignore) throws IOException {
        boolean resp = true;
        Result result = find(tuple.getPK());

        Tuple current = this.readTuple(result.getPosition());

        if (!result.isFound() && !current.isUsed() && (ignore || referenceExists(tuple))) {
            this.saveTuple(tuple, result.getPosition());
            this.entity.setNumberOfTuples(this.entity.getNumberOfTuples() + 1);
        } else {
            resp = false;
        }

        if ((entity.getNumberOfTuples()) * 3 > size * 2) {
            reorganize();
        }

        return resp;
    }
    
    public boolean insert(Tuple tuple) throws IOException {
        return insert(tuple, false);
    }

    public boolean referenceExists(Tuple current) throws IOException {
        boolean resp = true;
        for (Relation relation : entity.getRelation()) {
            if (relation.getEntity() == entity) {
                Value value = current.getValueByAttributeName(relation.getField());

                HashFile temp = new HashFile(relation.getReferencedEntity());
                temp.open(prefix);
                resp = resp & temp.find(value).isFound();
                temp.close();
            }
        }
        return resp;
    }

    public void countTuples() throws IOException {
        int counter = 0;
        file.seek(0);
        while (file.getFilePointer() < file.length()) {
            if (this.readTuple().isUsed()) {
                counter++;
            }
        }
        entity.setNumberOfTuples(counter);
    }

    public void create() throws IOException {
        Tuple empty = new Tuple(entity);
        for (int i = 0; i < size; i++) {
            this.saveTuple(empty);
        }
    }

    public void reorganize() throws IOException {
        int number = entity.getNumberOfTuples();

        File oldFile = new File(prefix + entity.getName() + ".dat");
        File tempFile = new File(prefix + "temp" + entity.getName() + ".dat");
        tempFile.delete();

        HashFile temp = new HashFile(entity);
        temp.open(prefix + "temp", size * 2);
        for (int i = 0; i < size; i++) {
            Tuple tuple = this.readTuple(i);
            if (tuple.isUsed()) {
                temp.insert(tuple, true);
            }
        }
        temp.close();
        this.close();

        oldFile.delete();
        tempFile.renameTo(oldFile);
        this.setFile(new RandomAccessFile(oldFile, "rw"));

        this.setSize(size * 2);
        getEntity().setNumberOfTuples(number);
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public RandomAccessFile getFile() {
        return file;
    }

    public void setFile(RandomAccessFile file) {
        this.file = file;
    }

    public int getTupleSize() {
        return tupleSize;
    }

    public void setTupleSize(int tupleSize) {
        this.tupleSize = tupleSize;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

//    public boolean modify(Value value, int onUpdate, List<Value> changes) throws IOException {
//        this.setStrategy(onUpdate);
//        return strategy.modify(value, changes);
//    }
    
    public boolean modify(Value value, List<Value> changes) throws IOException {
        return strategy.modify(value, changes);
    }
    
    public boolean modify(Value value, List<Value> changes, boolean ignore) throws IOException {
        return strategy.modify(value, changes, ignore);
    }

//    public boolean remove(Value value, int onDelete) throws IOException {
//        this.setStrategy(onDelete);
//        return strategy.remove(value);
//    }

    public boolean remove(Value value) throws IOException {
        return strategy.remove(value);
    }
    
    public void setStrategy(int option){
        if (option == ConsistencyStrategy.RESTRICT) {
            strategy = new Restrict(this);
        } else {
            strategy = new Cascade(this);
        }
    }

}
