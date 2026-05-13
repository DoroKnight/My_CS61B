public class StarTriangleN {
   /**
     * Prints a right-aligned triangle of stars ('*') with N lines.
     * The first row contains 1 star, the second 2 stars, and so on. 
     */
   public static void starTriangle(int N) {
      // TODO: Fill in this function
      // version1: Concatenates sapces and stars using repeat again.
      for (int i = 0; i < N; i++) {
         String line = " ".repeat(N - i) + "*".repeat(i + 1);
         System.out.println(line);
      }

      /* version2: use the "format"
      for (int i = 1; i <= N; i++) {
          String stars = "*".repeat(i);
          // Dynamically construct the format string, e.g., "%" + 5 + "s%n" -> "%5s%n"
          System.out.printf("%" + N + "s%n", stars);
      }
       */
   }
   
   public static void main(String[] args) {
      starTriangle(7);
   }
}