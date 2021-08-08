package linked_lists;

public class LS_1 {
    public static void main(String[] args) {
        LinkedList list = new LinkedList();


        list.insert(5);
        list.insert(10);
        list.show();
        list.delete(1);
        list.show();

        list.show();
        list.insert(50);
        list.insert(60);
        //list.insertAtStart(10);
        list.show();
        list.delete(2);
        list.show();
        list.delete(0);
        list.show();
        list.delete(0);
        list.show();

    }
}
