package linked_lists;

public class LinkedList {
    private Node head;
    private Node tail;

    public LinkedList() {
        this.head = null;
        this.tail = null;
    }

    public Node getHead() {
        return head;
    }

    public Node getTail() {
        return tail;
    }

    public void insert(int data) {
        Node node = new Node(data);

        if (head == null) {
            head = node;
        } else {
            tail.nextNode = node;
        }
        tail = node;
    }

    public void insertAtStart(int data) {
        Node node = new Node(data);

        if (head == null) {
            tail = node;
        }
        else{
            node.nextNode = head;
        }
        head = node;
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
