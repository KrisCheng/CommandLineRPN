package rpn;

import java.util.Scanner;
import java.util.Stack;

/**
 * @author: Peng Cheng
 * @description:
 * @since: 2020/10/12 22:53
 */
public class Main {
    public static void main(String[] args) {
        //1. bacic operation
        //2. sqrt
        //3. clear, undo
        //4. exception handle
        //5. test
        Scanner input = new Scanner(System.in);
        Stack<Double> currentStack = new Stack<>();
        while (input.hasNextLine()) {
            String currentLine = input.nextLine();
            if (currentLine.equals("")) {
                break;
            }
            String[] arr = currentLine.split(" ");

        }

    }
}
