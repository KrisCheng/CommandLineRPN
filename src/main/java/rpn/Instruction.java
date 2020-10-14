package rpn;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Peng Cheng
 * @description: real executed instruction
 * @since: 2020/10/13 22:20
 */
public class Instruction {
    Operator operator;
    Double value;

    public Instruction(Operator operator, Double value) {
        this.operator = operator;
        this.value = value;
    }

    public List<Element> getReversedElements() throws CalculateException {
        List<Element> reversedElements = new ArrayList<Element>();
        if (operator.getValueNumber() == 1) {
            reversedElements.add(new Element(operator.getOpposite(), true));
        } else if (operator.getValueNumber() == 2) {
            reversedElements.add(new Element(value.toString(), true));
            reversedElements.add(new Element(operator.getOpposite(), true));
            reversedElements.add(new Element(value.toString(), true));
        }
        return reversedElements;
    }
}