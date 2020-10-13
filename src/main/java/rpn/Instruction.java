package rpn;

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

    public String getReverseInstruction() throws CalculateException {
        if (operator.getOperandsNumber() < 1) {
            throw new CalculateException(String.format("invalid operation for operator %s", operator.getSymbol()));
        }
        return (operator.getOperandsNumber() == 2) ? String.format("%f %s %f", value, operator.getOpposite(), value) : String.format("%s", operator.getOpposite());
    }
}
