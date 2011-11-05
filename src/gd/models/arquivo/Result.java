package gd.models.arquivo;

public class Result {

    private int position;
    private boolean found;

    public Result(int position, boolean found) {
        this.position = position;
        this.found = found;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Result other = (Result) obj;
        if (this.position != other.position) {
            return false;
        }
        if (this.found != other.found) {
            return false;
        }
        return true;
    }

}