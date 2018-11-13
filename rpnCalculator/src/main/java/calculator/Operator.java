package calculator;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.sqrt;

public enum Operator {

    ADDITION("+", "-", 2) {
        @Override
        public Double calculate(Double firstOperand, Double secondOperand) {
            return firstOperand + secondOperand;
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
        public Double calculate(Double firstOperand, Double secondOperand) throws CalculatorException {
            if (firstOperand == 0) {
                throw new CalculatorException("Dividend can't be 0.");
            }
            return secondOperand / firstOperand;
        }
    },

    SQRT("sqrt", "pow", 1) {
        @Override
        public Double calculate(Double firstOperand, Double secondOperand) {
            return sqrt(firstOperand);
        }
    },

    UNDO("undo", null, 0) {
        @Override
        public Double calculate(Double firstOperand, Double secondOperand) throws CalculatorException {
            throw new CalculatorException("Invalid operation");
        }
    },

    CLEAR("clear", null, 0) {
        @Override
        public Double calculate(Double firstOperand, Double secondOperand) throws CalculatorException {
            throw new CalculatorException("Invalid operation");
        }
    };

    private String symbol; //  operation
    private String opposite; // opposite operation for performing undo
    private int operandsNumber; // required parameters number
    private static final Map<String, Operator> searachMap = new HashMap<String, Operator>();

    public abstract Double calculate(Double firstOperand, Double secondOperand) throws CalculatorException;

    static {
        for (Operator o : values()) {
            searachMap.put(o.getSymbol(), o);
        }
    }

    Operator(String symbol, String opposite, int operandsNumber) {
        this.symbol = symbol;
        this.opposite = opposite;
        this.operandsNumber = operandsNumber;
    }

    public static Operator getEnum(String value) throws CalculatorException {
        if (null == searachMap.get(value)) {
            throw new CalculatorException(String.format("Operator ( %s ) is not supported yet.", value));
        }
        ;
        return searachMap.get(value);
    }

    public String getSymbol() {
        return symbol;
    }

    public String getOpposite() {
        return opposite;
    }

    public int getOperandsNumber() {
        return operandsNumber;
    }

    @Override
    public String toString() {
        return symbol;
    }
}