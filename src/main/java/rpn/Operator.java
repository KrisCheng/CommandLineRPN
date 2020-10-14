package rpn;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * @author: Peng Cheng
 * @description: operator handle class, which contains the operator, reverse for undo process, operandNumber for discrimination
 * @since: 2020/10/13 21:21
 */
public enum Operator {

    ADDITION("+", "-", 2) {
        @Override
        public Double calculate(Double firstValue, Double secondValue) {
            return secondValue + firstValue;
        }
    },

    SUBTRACTION("-", "+", 2) {
        @Override
        public Double calculate(Double firstValue, Double secondValue) {
            return secondValue - firstValue;
        }
    },

    MULTIPLICATION("*", "/", 2) {
        @Override
        public Double calculate(Double firstValue, Double secondValue) {
            return secondValue * firstValue;
        }
    },

    DIVISION("/", "*", 2) {
        @Override
        public Double calculate(Double firstValue, Double secondValue) throws CalculateException {
            if (firstValue == 0) {
                throw new CalculateException("0 is illegal as divisor.");
            }
            return secondValue / firstValue;
        }
    },

    SQUAREROOT("sqrt", "pow", 1) {
        @Override
        public Double calculate(Double firstValue, Double secondValue) {
            return sqrt(firstValue);
        }
    },

    POWER("pow", "sqrt", 1) {
        @Override
        public Double calculate(Double firstValue, Double secondValue) {
            return pow(firstValue, 2);
        }
    },

    UNDO("undo", null, 0) {
        @Override
        public Double calculate(Double firstValue, Double secondValue) throws CalculateException{
            throw new CalculateException("invalid operation");
        }
    },

    CLEAR("clear", null, 0) {
        @Override
        public Double calculate(Double firstValue, Double secondValue) throws CalculateException {
            throw new CalculateException("invalid operation");
        }
    };

    // save all operators as a map for look up
    private static final Map<String, Operator> lookupMap = new HashMap<String, Operator>();

    static {
        for (Operator o : Operator.values()) {
            lookupMap.put(o.getOperator(), o);
        }
    }

    private String operator;
    private String opposite;
    private int valueNumber;

    Operator (String operator, String opposite, int valueNumber) {
        this.operator = operator;
        this.opposite = opposite;
        this.valueNumber = valueNumber;
    }

    public abstract Double calculate(Double firstValue, Double secondValue) throws CalculateException;

    public String getOperator() {
        return operator;
    }

    public String getOpposite() {
        return opposite;
    }

    public int getValueNumber() {
        return valueNumber;
    }

    public static Operator getEnum(String value) {
        return lookupMap.get(value);
    }

    @Override
    public String toString() {
        return operator;
    }
}