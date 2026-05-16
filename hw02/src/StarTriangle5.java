public class StarTriangle5 {
   /**
     * Prints a right-aligned triangle of stars ('*') with 5 lines.
     * The first row contains 1 star, the second 2 stars, and so on. 
     */
   public static void starTriangle5() {
      // TODO: Fill in this function
      for (int i = 1; i <= 5; i++) {
         String line = " ".repeat(5 - i) + "*".repeat(i);
         System.out.println(line);
      }
   }
   
   public static void main(String[] args) {
      starTriangle5();
   }
}