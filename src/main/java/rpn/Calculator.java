package rpn;

import java.util.List;
import java.util.Stack;

/**
 * @author: Peng Cheng
 * @description: the main calculate logic.
 * @since: 2020/10/13 21:19
 */
public class Calculator {
    private Stack<Double> numberStack = new Stack<Double>();
    private Stack<Instruction> instructionStack = new Stack<Instruction>();
    private int currentElementIndex = 0;

    /**
     * process a whole input line
     *
     * @param input input line
     */
    public void processLine(String input) {
        currentElementIndex = 0;
        String[] elements = input.split(" ");
        for (String element : elements) {
            currentElementIndex++;
            processElement(new Element(element, false));
        }
    }

    /**
     * process single element
     *
     * @param element current element
     * @throws CalculateException
     */
    private void processElement(Element element) {
        if (Utils.isRealNumber(element.getValue())) {
            numberStack.push(Double.parseDouble(element.getValue()));
            if (!element.isUndo()) {
                instructionStack.push(null);
            }
        } else {
            processOperator(element);
        }
    }

    /**
     * process operator
     *
     * @param element
     */
    private void processOperator(Element element) {
        if (numberStack.isEmpty()) {
            throw new CalculateException("empty number stack");
        }
        Operator operator = Operator.getEnum(element.getValue());
        if (operator == null) {
            throw new CalculateException("invalid operator");
        }
        if (operator == Operator.CLEAR) {
            clearStacks();
            return;
        }
        if (operator == Operator.UNDO) {
            undoLastProcess();
            return;
        }
        if (operator.getOperandsNumber() > numberStack.size()) {
            throwInvalidOperand(element.getValue());
        }
        Double firstOperand = numberStack.pop();
        Double secondOperand = (operator.getOperandsNumber() > 1) ? numberStack.pop() : null;
        Double result = operator.calculate(firstOperand, secondOperand);
        if (result != null) {
            numberStack.push(result);
            if (!element.isUndo()) {
                instructionStack.push(new Instruction(Operator.getEnum(element.getValue()), firstOperand));
            }
        }
    }

    private void undoLastProcess() throws CalculateException {
        if (instructionStack.isEmpty()) {
            throw new CalculateException("no operations to undo");
        }
        Instruction lastInstruction = instructionStack.pop();
        if (lastInstruction == null) {
            numberStack.pop();
        } else {
            List<Element> reverseElements = lastInstruction.getReverseInstruction();
            for (Element element : reverseElements) {
                processElement(element);
            }
        }
    }

    private void clearStacks() {
        numberStack.clear();
        instructionStack.clear();
    }

    private void throwInvalidOperand(String operator) throws CalculateException {
        throw new CalculateException(
            String.format("operator %s (position: %d): insufficient parameters", operator, currentElementIndex));
    }

    /**
     * Returns the values valuesStack
     */
    public Stack<Double> getValueStack() {
        return numberStack;
    }
}

