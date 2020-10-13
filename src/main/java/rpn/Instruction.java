package rpn;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Peng Cheng
 * @description:
 * @since: 2020/10/13 22:20
 */
public class Instruction {
    Operator operator;
    Double value;

    public Instruction(Operator operator, Double value) {
        this.operator = operator;
        this.value = value;
    }

    public List<Element> getReverseInstruction() throws CalculateException {
        List<Element> reverseElement = new ArrayList<Element>();
        if (operator.getOperandsNumber() < 1) {
            throw new CalculateException(String.format("invalid operation for operator %s", operator.getSymbol()));
        }
        if (operator.getOperandsNumber() == 2) {
            reverseElement.add(new Element(value.toString(), true));
            reverseElement.add(new Element(operator.getOpposite(), true));
            reverseElement.add(new Element(value.toString(), true));
        } else {
            reverseElement.add(new Element(operator.getOpposite(), true));
        }
        return reverseElement;
    }
}
