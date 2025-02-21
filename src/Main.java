class Node {

    int value;
    Node next;
    Node prev;

    public Node(int value) {
        this.value = value;
    }
}

class Stack {

    Node top;

    void push(int value) {
        if(top == null) {
            top = new Node(value);
            top.prev = null;
            top.next = null;
        }
        else {
            Node newNode = new Node(value);
            newNode.next = top;
            newNode.prev = null;
            top.prev = newNode;
            top = newNode;
        }
    }

    int pop() {
        if(top != null) {
            int value = top.value;
            top = top.next;
            top.prev = null;
            return value;
        }
        return 0;
    }
}

class Queue {
    Node head;
    Node tail;

    void enqueue(int value) {
        Node newNode = new Node(value);

        if(head == null) {
            head = newNode;
        }
        else {
            tail.next = newNode;
        }

        tail = newNode;
    }

    int dequeue() {
        if(head == null) {
            return 0;
        }
        else {
            int value = head.value;
            head = head.next;
            return value;
        }
    }
}

class Postfix {

    int getPrecedence(char operator) {
        switch (operator) {
            case '=':
                return 1;
            case '(':
                return 2;
            case '+':
            case '-':
                return 3;
            default:
                return 4;
        }
    }

    void evaluate(String expression) {
        Stack stack = new Stack();
        Queue queue = new Queue();

        for(int i = 0; i < expression.length(); i++){
            char c = expression.charAt(i);
            if(Character.isDigit(c)) {
                queue.enqueue(c);
            }
            else if(c == '(') {
                stack.push(c);
            }
            else if(c == ')') {
                char 
                while(stack.pop() != '(') {
                    queue.enqueue(stack.pop());
                }
            }
        }
    }
}

class Driver {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}