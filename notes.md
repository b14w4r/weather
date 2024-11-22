```java
public class Solution {
   public void solve() throws IOException {
       Scanner scanner = new Scanner(new FileInputStream("Root/src/arithmetic.txt"));
       StringBuilder answers = new StringBuilder();

       while (scanner.hasNextLine()) {
           String[] element = scanner.nextLine().split(" "); // split to array

           int first = Integer.parseInt(element[0]);
           String symbol = element[1];
           int second = Integer.parseInt(element[2]);

           // check symbols and add task in answers
           switch (symbol) {
               case "+":
                   answers.append(first).append(" + ").append(second).append(" = ").append(first + second).append("\n");
                   break;
               case "-":
                   answers.append(first).append(" - ").append(second).append(" = ").append(first - second).append("\n");
                   break;
               case "*": 
                   answers.append(first).append(" * ").append(second).append(" = ").append(first * second).append("\n");
           }

           // write in file
           FileWriter writer = new FileWriter(new File("Root/src/answers.txt"), false);
           writer.write(answers.toString().trim());
           writer.flush();
       }
   }
}
```
