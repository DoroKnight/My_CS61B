public class ArrayExercises {
    /** Returns the second to last item in the given array.
     *  Assumes the array has at least 2 elements. */
    public static String secondToLastItem(String[] items) {
        // TODO: Implement this method
        int index = items.length;
        return items[index - 2];
    }    

    /** Returns the difference between the minimum and maximum item in the given array */
    public static int minMaxDifference(int[] items) {
        // TODO: Implement this method
        int min = 114514, max = 0;
        for (int num : items) {
            if (min > num) min = num;
            if (max < num) max = num;
        }
        return max - min;
    }
}
