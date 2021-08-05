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
        } else {
            node.nextNode = head;
        }
        head = node;
    }

    public void insertAt(int index, int data) {
        //if the list is empty or data is to be added in the first index
        if(head == null || index == 0){
            insertAtStart(data);
           return;
        }
        //finding the previous node
        Node preNode = head;
        int i = 1;
        //(index) for previous node (not the current node)
        while (preNode != null && i != index) {
            preNode = preNode.nextNode;
            i++;
        }
        if (preNode == null) {
            System.out.println("no such index is in the list");
            return;
        } else if (preNode == tail)
            this.insert(data);
        else {
            Node node = new Node(data);
            node.nextNode = preNode.nextNode;
            preNode.nextNode = node;
        }
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
