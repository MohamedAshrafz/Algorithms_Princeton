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
        if (head == null || index == 0) {
            insertAtStart(data);
            return;
        }
        //finding the previous node
        Node preNode = head;
        int i = 1; //the index starting from 1 (as we handled the index 0 already)
        //(index) for previous node (not the current node)
        while (preNode != null && i != index) {
            preNode = preNode.nextNode;
            i++;
        }
        if (preNode == null) {
            System.out.println("no such index is in the list");
        } else if (preNode == tail)
            this.insert(data);
        else {
            Node node = new Node(data);
            node.nextNode = preNode.nextNode;
            preNode.nextNode = node;
        }
    }

    public void delete(int index) {
        //removing the head (index 0 node)
        if (index == 0) {
            if (head == null) {
                System.out.println("Cannot delete, no nodes in the list");
            }
            if (head != null && head.nextNode != null) {
                head = head.nextNode;
            } else {
                head = null;
                tail = null;
            }
        } else {
            //finding the previous node
            Node preNode = head;
            int i = 1;
            while (i < index && preNode != null) {
                preNode = preNode.nextNode;
                i++;
            }
            //if no previous node return from the function (no previous node means no current node)
            if (preNode == null){
                System.out.println("No such index node to be deleted");
                return;
            }

            if (preNode.nextNode == null) {
                System.out.println("No such index node to be deleted");
            } else if (preNode.nextNode == tail) {
                tail = preNode; //need to change the tail node because we deleted the last node in the list
                preNode.nextNode = null;
            } else {
                //(preNode.nextNode) is the current node (preNode.nextNode.nextNode) is the the next node
                preNode.nextNode = preNode.nextNode.nextNode;
            }
        }
    }

    public void show() {
        System.out.println("============================\nshow method is invoked\nThe nodes are:");
        Node node = head;
        if (node == null) {
            System.out.println("No nodes yet");
        }
        while (node != null) {
            System.out.println(node.data);
            node = node.nextNode;
        }
        System.out.println("============================");
    }


    class Node {
        private int data;
        private Node nextNode;


        public Node(int data) {
            this.data = data;
        }
    }
}
