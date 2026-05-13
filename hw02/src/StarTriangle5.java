public class StarTriangle5 {
   /**
     * Prints a right-aligned triangle of stars ('*') with 5 lines.
     * The first row contains 1 star, the second 2 stars, and so on. 
     */
   public static void starTriangle5() {
      // TODO: Fill in this function

      /* first try
      for (int i = 0; i < 5; i++) {
         String s = "";
         for (int j = 0; j < 4 - i; j++) {
            s += " ";
         }
         for (int j = 0; j <= i; j++) {
            s += "*";
         }
         System.out.println(s);
      } */

      // final version
      for (int i = 0; i < 5; i++) {
         // Concatenates spaces and stars using repeat
         String line = " ".repeat(4 - i) + "*".repeat(i + 1);
         System.out.println(line);
      }
   }
   
   public static void main(String[] args) {
      starTriangle5();
   }
}