package linked_lists;

public class LS_1 {
    public static void main(String[] args) {
        LinkedList list = new LinkedList();

        list.insert(12);
        list.insert(15);
        list.insert(50);
        list.insert(12);
        list.insert(15);
        list.insert(55);

        list.show();
    }
}
