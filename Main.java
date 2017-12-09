import java.io.*;

public class Main {

    //If you want to check that it works correctly then debug it :)
    public static void main(String[] args) throws IOException {

        FileReader reader = new FileReader(new File("input.txt"));
        BufferedReader bf = new BufferedReader(reader);
        String input = bf.readLine(); // 19+(2*5)
        bf.close();
        Parser parser = new Parser(input);
        Expression expressionTree = parser.parse();
        FileWriter writer = new FileWriter(new File("output.txt"));
        int result = expressionTree.calculate();
        String serialized = expressionTree.toJSON();
        writer.write("Answer: " + String.valueOf(result));
        writer.write("\n\nJSON: \n");
        writer.write(serialized);
        writer.close();
    }
}
