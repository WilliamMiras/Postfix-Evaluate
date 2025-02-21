import java.util.Scanner;

class Node{

    char value;
    Node next;
    Node prev;

    public Node(char value) {
        this.value = value;
    }
}

class Stack {

    Node top;

    void push(char value) {
        Node newNode = new Node(value);
        if(top == null) {
            top = newNode;
        }
        else {
            newNode.next = top;
            top.prev = newNode;
            top = newNode;
        }
    }

    char pop() {
        if(top != null) {
            char value = top.value;
            top = top.next;
            if(top != null) {
                top.prev = null;
            }
            return value;
        }
        return 'E';
    }

    char peek() {
        if(top != null) {
            return top.value;
        }
        return 'E';
    }

    boolean isEmpty() {
        return top == null;
    }
}

class Queue {
    Node head;
    Node tail;

    void enqueue(char value) {
        Node newNode = new Node(value);

        if(head == null) {
            head = newNode;
            tail = newNode;
        }
        else {
            tail.next = newNode;
            tail = newNode;
        }
    }

    char dequeue() {
        if(head == null) {
            return 'E';
        }
        char value = head.value;
        head = head.next;
        return value;
    }

    void display() {
        Node temp = head;
        while(temp != null) {
            System.out.print(temp.value);
            temp = temp.next;
        }
        System.out.println();
    }

}

class Postfix {

    Queue queue = new Queue();

    int getPrecedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '(':
                return 0;
            default:
                return -1;
        }
    }

    Queue infToPre(String expression) {
        Stack stack = new Stack();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c)) {
                queue.enqueue(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    queue.enqueue(stack.pop());
                }
                stack.pop();
            } else {
                while(!stack.isEmpty() && getPrecedence(c) <= getPrecedence(stack.peek())) {
                    queue.enqueue(stack.pop());
                }
                stack.push(c);
            }
        }

        while(!stack.isEmpty()) {
            queue.enqueue(stack.pop());
        }

        return queue;
    }


    int evaluatePostfix() {
        Stack stack = new Stack();
        char expr = queue.dequeue();

        while (expr != 'E') {
            if (Character.isDigit(expr)) {
                stack.push(expr);
            } else {
                int num2 = stack.pop() - '0';
                int num1 = stack.pop() - '0';
                int result = 0;

                if (expr == '*') {
                    result = num1 * num2;
                } else if (expr == '/') {
                    result = num1 / num2;
                } else if (expr == '+') {
                    result = num1 + num2;
                } else if (expr == '-') {
                    result = num1 - num2;
                }

                stack.push((char) (result + '0'));
            }
            expr = queue.dequeue();
        }
        return stack.pop() - '0';
    }
}

class Driver {
    public static void main(String[] args) {
        Postfix postfix = new Postfix();
        Scanner sc = new Scanner(System.in);

        System.out.print("Give me an expression: ");
        String expression = sc.nextLine();
        Queue queue = postfix.infToPre(expression);

        System.out.print("\nPostfix expression: ");
        queue.display();

        System.out.print("\nAnswer: " + postfix.evaluatePostfix());
    }
}
