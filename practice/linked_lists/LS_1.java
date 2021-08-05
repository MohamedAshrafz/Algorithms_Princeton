package linked_lists;

public class LS_1 {
    public static void main(String[] args) {
        LinkedList list = new LinkedList();

        list.insertAtStart(5);
        list.insertAt(0, 500);
        list.insertAtStart(10);
        list.insertAt(5, 600);
        /*list.insertAtStart(20);
        list.insert(12);
        list.insert(15);
        list.insert(50);
        list.insertAt(4, 700);
        list.insert(12);
        list.insert(15);
        list.insert(55);*/



        list.show();
    }
}
