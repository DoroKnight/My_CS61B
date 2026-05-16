public class DoubleUp {
    /**
     * Returns a new string where each character of the given string is repeated twice.
     * Example: doubleUp("hello") -> "hheelllloo"
     */
    public static String doubleUp(String s) {
        // TODO: Fill in this function
        int length = s.length();
        String result = "";
        for (int i = 0; i < length; i++) {
           result += String.valueOf(s.charAt(i)).repeat(2);
        }
        return result;
    }

    public static void main(String[] args) {
        String s = doubleUp("hello");
        System.out.println(s);

        System.out.println(doubleUp("cat"));
    }
}