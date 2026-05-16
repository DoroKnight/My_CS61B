public class Stack {
    private class Node {
        private int value;
        private Node next;

        Node(int x, Node r) {
            value = x;
            next = r;
        }
    }

    private Node top;
    private int size;
    private int sum;

    public Stack() {
        top = null;
        size = 0;
        sum = 0;
    }

    public void push(int x) {
        top = new Node(x, top);
        size += 1;
        sum += x;
    }

    public int pop() {
        int valueToReturn = top.value;
        top = top.next;
        size -= 1;
        sum -= valueToReturn;
        return valueToReturn;
    }

    public int size() {
        return size;
    }

    public int sum() {
        return sum;
    }

}
