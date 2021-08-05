package linked_lists;

public class LinkedList {
    private Node head;
    private Node tail;

    public LinkedList() {
        this.head = null;
        this.tail = null;
    }

    public void insert(int data) {
        Node node = new Node(data);

        if (tail == null) {
            head = node;
        } else {
            tail.nextNode = node;
        }
        tail = node;
    }


    public void show() {
        Node node = head;
        while (node != null) {
            System.out.println(node.data);
            node = node.nextNode;
        }
    }

    class Node {
        private int data;
        private Node nextNode;

        public Node(int data) {
            this.data = data;
        }
    }
}
