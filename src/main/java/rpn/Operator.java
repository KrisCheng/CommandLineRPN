package rpn;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * @author: Peng Cheng
 * @description: operator handle class
 * @since: 2020/10/13 21:21
 */
public enum Operator {

    ADDITION("+", "-", 2) {
        @Override
        public Double calculate(Double firstOperand, Double secondOperand) {
            return secondOperand + firstOperand;
        }
    },

    SUBTRACTION("-", "+", 2) {
        @Override
        public Double calculate(Double firstOperand, Double secondOperand) {
            return secondOperand - firstOperand;
        }
    },

    MULTIPLICATION("*", "/", 2) {
        @Override
        public Double calculate(Double firstOperand, Double secondOperand) {
            return secondOperand * firstOperand;
        }
    },

    DIVISION("/", "*", 2) {
        @Override
        public Double calculate(Double firstOperand, Double secondOperand) throws CalculateException {
            if (firstOperand == 0) {
                throw new CalculateException("cannot divide by 0.");
            }
            return secondOperand / firstOperand;
        }
    },

    SQUAREROOT("sqrt", "pow", 1) {
        @Override
        public Double calculate(Double firstOperand, Double secondOperand) {
            return sqrt(firstOperand);
        }
    },

    POWER("pow", "sqrt", 1) {
        @Override
        public Double calculate(Double firstOperand, Double secondOperand) {
            return pow(firstOperand, 2);
        }
    },

    UNDO("undo", null, 0) {
        @Override
        public Double calculate(Double firstOperand, Double secondOperand) throws CalculateException{
            throw new CalculateException("Invalid operation");
        }
    },

    CLEAR("clear", null, 0) {
        @Override
        public Double calculate(Double firstOperand, Double secondOperand) throws CalculateException {
            throw new CalculateException("Invalid operation");
        }
    };

    // map for look up
    private static final Map<String, Operator> lookupMap = new HashMap<String, Operator>();

    static {
        for (Operator o : Operator.values()) {
            lookupMap.put(o.getSymbol(), o);
        }
    }

    private String symbol;
    private String opposite;
    private int operandsNumber;

    Operator (String symbol, String opposite, int operandsNumber) {
        this.symbol = symbol;
        this.opposite = opposite;
        this.operandsNumber = operandsNumber;
    }

    public abstract Double calculate(Double firstOperand, Double secondOperand) throws CalculateException;

    public String getSymbol() {
        return symbol;
    }

    public String getOpposite() {
        return opposite;
    }

    public int getOperandsNumber() {
        return operandsNumber;
    }

    public static Operator getEnum(String value) {
        return lookupMap.get(value);
    }

    @Override
    public String toString() {
        return symbol;
    }
}
