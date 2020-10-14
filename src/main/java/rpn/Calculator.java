package rpn;

import java.util.List;
import java.util.Stack;

/**
 * @author: Peng Cheng
 * @description: the main calculate logic.
 * @since: 2020/10/13 21:19
 */
public class Calculator {
    private Stack<Double> valueStack = new Stack<Double>();
    private Stack<Instruction> instructionStack = new Stack<Instruction>();
    private int currentElementIndex = 0;

    /**
     * process a whole input line
     *
     * @param input input line
     */
    public void processLine(String input) {
        currentElementIndex = 0;
        String[] values = input.split(" ");
        for (String value : values) {
            currentElementIndex++;
            processElement(new Element(value, false));
        }
    }

    /**
     * process single element
     *
     * @param element current element
     */
    private void processElement(Element element) {
        if (Utils.isRealNumber(element.getValue())) {
            valueStack.push(Double.parseDouble(element.getValue()));
            if (!element.isUndo()) {
                instructionStack.push(new Instruction(null, Double.parseDouble(element.getValue())));
            }
        } else {
            processOperator(element);
        }
    }

    /**
     * process operator
     *
     * @param element current element
     */
    private void processOperator(Element element) {
        Operator operator = Operator.getEnum(element.getValue());
        if (operator == null) {
            throw new CalculateException("invalid operator");
        } else if (operator == Operator.CLEAR) {
            clearStacks();
            return;
        } else if (operator == Operator.UNDO) {
            undoLastProcess();
            return;
        } else if (operator.getValueNumber() > valueStack.size()) {
            throw new CalculateException(String.format("operator %s (position: %d): insufficient parameters", operator, currentElementIndex));
        }
        Double firstValue = valueStack.pop();
        Double secondValue = (operator.getValueNumber() > 1) ? valueStack.pop() : null;
        Double result = operator.calculate(firstValue, secondValue);
        if (result != null) {
            valueStack.push(result);
            if (!element.isUndo()) {
                instructionStack.push(new Instruction(Operator.getEnum(element.getValue()), firstValue));
            }
        }
    }

    private void undoLastProcess() throws CalculateException {
        if (instructionStack.isEmpty()) {
            throw new CalculateException("no operation to undo");
        }
        Instruction lastInstruction = instructionStack.pop();
        if (lastInstruction.operator == null) {
            valueStack.pop();
        } else {
            List<Element> reversedElements = lastInstruction.getReversedElements();
            for (Element element : reversedElements) {
                processElement(element);
            }
        }
    }

    private void clearStacks() {
        valueStack.clear();
        instructionStack.clear();
    }

    public Stack<Double> getValueStack() {
        return valueStack;
    }
}