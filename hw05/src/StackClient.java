public class StackClient {
    public static Stack flipped(Stack s) {
        Stack newStack = new Stack();
        while (s.size() != 0) {
            newStack.push(s.pop());
        }
        return newStack;
    }
}
