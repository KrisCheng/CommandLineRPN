package rpn;

/**
 * @author: Peng Cheng
 * @description: single element bean.
 * @since: 2020/10/13 23:57
 */
public class Element {
    private String value;
    private boolean isUndo;

    public Element(String content, boolean isUndo) {
        this.value = content;
        this.isUndo = isUndo;
    }

    public boolean isUndo() {
        return isUndo;
    }

    public void setUndo(boolean undo) {
        isUndo = undo;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return ("Value :" + this.value + " isUndo : " + this.isUndo);
    }
}