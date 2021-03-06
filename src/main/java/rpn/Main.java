package rpn;

import java.util.Scanner;
import java.util.Stack;

/**
 * @author: Peng Cheng
 * @description: entrance of RPN.
 * @since: 2020/10/12 22:53
 */

public class Main {
    public static final String QUIT_INPUT = "quit";

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Calculator calculator = new Calculator();
        System.out.println("Please type your RPN calculate string (end with Enter, type `quit` for quit the whole program):");
        while (input.hasNextLine()) {
            String currentLine = input.nextLine();
            if (currentLine == null || currentLine.trim().equals(QUIT_INPUT) ) {
                break;
            } else {
                try {
                    calculator.processLine(currentLine);
                } catch (CalculateException e) {
                    System.out.println(e.getMessage());
                }
                Stack<Double> stack = calculator.getValueStack();
                System.out.print("stack: ");
                for (Double number : stack) {
                    System.out.print(Utils.formatDouble(number));
                    System.out.print(" ");
                }
                System.out.println();
            }
        }
    }
}