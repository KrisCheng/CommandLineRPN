package rpn;

import java.util.Stack;

/**
 * @author: Peng Cheng
 * @description: the main calculate logic.
 * @since: 2020/10/13 21:19
 */
public class Calculator {
    private Stack<Double> numberStack = new Stack<Double>();
    private Stack<Instruction> instructionsStack = new Stack<Instruction>();
    private int currentElementIndex = 0;

    /**
     * Processe single element
     *
     * @param element current element
     * @param isUndoOperation indicates if the operation is an undo operation.
     * @throws CalculateException
     */
    private void processElement(String element, boolean isUndoOperation) throws CalculateException {
        if (isRealNumber(element)) {
            numberStack.push(Double.parseDouble(element));
            if (!isUndoOperation) {
                instructionsStack.push(null);
            }
        } else {
            processOperator(element, isUndoOperation);
        }
    }

    /**
     * Executes an operation on the stack
     *
     * @param operatorString RPN valid operator
     * @param isUndoOperation indicates if the operation is an undo operation.
     * @throws CalculateException
     */
    private void processOperator(String operatorString, boolean isUndoOperation) throws CalculateException {
        if (numberStack.isEmpty()) {
            throw new CalculateException("empty number stack");
        }

        // searching for the operator
        Operator operator = Operator.getEnum(operatorString);
        if (operator == null) {
            throw new CalculateException("invalid operator");
        }

        // clear value stack and instructions stack
        if (operator == Operator.CLEAR) {
            clearStacks();
            return;
        }

        // undo evaluates the last instruction in stack
        if (operator == Operator.UNDO) {
            undoLastProcess();
            return;
        }

        // Checking that there are enough operand for the operation
        if (operator.getOperandsNumber() > numberStack.size()) {
            throwInvalidOperand(operatorString);
        }

        // getting operands
        Double firstOperand = numberStack.pop();
        Double secondOperand = (operator.getOperandsNumber() > 1) ? numberStack.pop() : null;
        // calculate
        Double result = operator.calculate(firstOperand, secondOperand);
        if (result != null) {
            numberStack.push(result);
            if (!isUndoOperation) {
                instructionsStack.push(new Instruction(Operator.getEnum(operatorString), firstOperand));
            }
        }
    }

    private void undoLastProcess() throws CalculateException {
        if (instructionsStack.isEmpty()) {
            throw new CalculateException("no operations to undo");
        }

        Instruction lastInstruction = instructionsStack.pop();
        if (lastInstruction == null) {
            numberStack.pop();
        } else {
            String tmp = lastInstruction.getReverseInstruction();
            processElement(tmp, true);
        }
    }

    private void clearStacks() {
        numberStack.clear();
        instructionsStack.clear();
    }

    private void throwInvalidOperand(String operator) throws CalculateException {
        throw new CalculateException(
                String.format("operator %s (position: %d): insufficient parameters", operator, currentElementIndex));
    }

    /**
     * Returns the values valuesStack
     */
    public Stack<Double> getValuesStack() {
        return numberStack;
    }

    /**
     * Helper method to return a specific item in the valuesStack
     *
     * @param index index of the element to return
     */
    public Double getStackItem(int index) {
        return numberStack.get(index);
    }

    /**
     *
     *
     * @param input valid RPN expression
     */
    public void processLine(String input) throws CalculateException {
        processLine(input, false);
    }

    /**
     * process the whole input line
     *
     * @param input           input line
     * @param isUndoOperation indicates if the operation is an undo operation.
     *                        undo operations use the same evaluation functions as the standard ones
     *                        but they are not pushed into instructionsStack
     */
    private void processLine(String input, boolean isUndoOperation) throws CalculateException {
        currentElementIndex = 0;
        String[] elements = input.split(" ");
        for (String element : elements) {
            currentElementIndex++;
            processElement(element, isUndoOperation);
        }
    }

    /**
     * Judge the element is real number or not.
     *
     * @param element single element
     */
    private boolean isRealNumber(String element) {
        try {
            Double.parseDouble(element);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

