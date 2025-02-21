import java.util.Scanner;

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
        return -1;
    }

    int peek() {
        if(top != null) {
            return top.value;
        }
        return -1;
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
        int value = head.value;
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

    Queue infToPre(String expression) {
        Stack stack = new Stack();

        for(int i = 0; i < expression.length(); i++){
            char c = expression.charAt(i);
            if(Character.isDigit(c)) {
                queue.enqueue(c);
            }
            else if(c == '(') {
                stack.push(c);
            }
            else if(c == ')') {
                char expr = (char)stack.pop();
                while(stack.pop() != '(') {
                    queue.enqueue(stack.pop());
                }
                stack.pop();
            }
            else {
                char operator = (char)stack.peek();
                if(operator == -1) {
                    stack.push(c);
                }
                else {
                    if(getPrecedence(c) > getPrecedence(operator)) {
                        stack.push(c);
                    }
                    else {
                        while(getPrecedence(c) <= getPrecedence(operator) && operator != -1) {
                            queue.enqueue(stack.pop());
                            operator = stack.peek();
                        }
                    }
                }
            }
        }
        return queue;
    }



    int evaluatePostfix() {
        Stack stack = new Stack();
        int expr = queue.dequeue();

        while(expr == '=') {
            if(Character.isDigit(expr)) {
                stack.push(expr);
            }
            else {
                int num1 = stack.pop();
                int num2 = stack.pop();
                if(expr == '*'){
                    stack.push(num1*num2);
                }
                else if(expr == '/'){
                    stack.push(num1/num2);
                }
                else if (expr == '+'){
                    stack.push(num1+num2);
                }
                else if (expr == '-'){
                    stack.push(num1-num2);
                }
            }
            expr = queue.dequeue();
    }
        return expr;
}

class Driver {
    public static void main(String[] args) {
        Postfix postfix = new Postfix();
        Scanner sc = new Scanner(System.in);

        System.out.println("Give me an expression: ");
        String expression = sc.nextLine();
        Queue queue = postfix.infToPre(expression);

        System.out.print("Postfix expression: ");
        queue.display();

        System.out.print("Answer: " + postfix.evaluatePostfix());
    }
}