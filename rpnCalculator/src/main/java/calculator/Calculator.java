package calculator;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import org.apache.commons.lang.StringUtils;
public class Calculator {

    private Stack<Double> valuesStack = new Stack<Double>();
    private Stack<Instruction> instructionsStack = new Stack<Instruction>();
    private int position = 0;
    private Queue<String> unsettledOperand = new LinkedList<String>();

    private void processToken(String token, boolean isUndoOperation) throws CalculatorException {
        unsettledOperand.poll();
        Double value = tryParseDouble(token);
        if (value == null) {
            processOperator(token, isUndoOperation);
        } else {
            valuesStack.push(Double.parseDouble(token));
            instructionsStack.push(null);
        }
    }

    private void processOperator(String operatorString, boolean isUndoOperation) throws CalculatorException {
        Operator operator = Operator.getEnum(operatorString);
        if (operator.getOperandsNumber() > valuesStack.size()) {
            throwSufficientParamsNotFound(operatorString);
        }
        if (operator.equals(Operator.CLEAR)) {
            clearAll();
            return;
        }
        if (operator.equals(Operator.UNDO)) {
            undoLastInstruction();
            return;
        }
        Double firstOperand = valuesStack.pop();
        Double secondOperand = (valuesStack.size() > 0) ? valuesStack.pop() : null;
        Double result = operator.calculate(firstOperand, secondOperand);
        if (result != null) {
            valuesStack.push(result);
            if (!isUndoOperation) {
                //save for performing undo .
                instructionsStack.push(new Instruction(Operator.getEnum(operatorString), firstOperand));
            }
        }
    }


    private void undoLastInstruction() throws CalculatorException {
        Instruction lastInstruction = instructionsStack.pop();
        if (lastInstruction == null) {
            //if value, pop from value stack directly
            valuesStack.pop();
        } else {
            //if operation, do reverse ..
            eval(lastInstruction.getReverseInstruction(), true);
        }
    }

    public void preform(String input) throws CalculatorException {
        if (input == null || input.trim().length() == 0) {
            throw new CalculatorException("Input is null. Please input operands.");
        }
        eval(input, false);
    }


    private void eval(String input, boolean isUndoOperation) throws CalculatorException {
        String[] result = input.trim().split("\\s+");
        Collections.addAll(unsettledOperand,result);
        for (String aResult : result) {
            StringBuffer sb= new StringBuffer(input);
            position = input.indexOf(aResult);
            input = sb.replace(0,++position,StringUtils.repeat(" ",position)).toString();
            processToken(aResult, isUndoOperation);
         }
    }

    public Stack<Double> getValuesStack() {
        return valuesStack;
    }
    public Queue<String> getUnsettledStack() {return unsettledOperand;
    }

    private Double tryParseDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void throwSufficientParamsNotFound(String operator) throws CalculatorException {
        //todo
        throw new CalculatorException(
                String.format("operator %s (position: %d): insufficient parameters", operator, position));
    }

    private void clearAll() {
        valuesStack.clear();
        instructionsStack.clear();
        unsettledOperand.clear();
    }

    public String parseResult() {
        Stack<Double> stack = this.getValuesStack();
        StringBuilder result = new StringBuilder("Stack: ");
        for (double value : stack) {
            if (value % 1.0 == 0) {
                result.append(String.valueOf((long) value)).append(" ");
            } else {
                BigDecimal bd = new BigDecimal(Double.toString(value));
                result.append(bd.setScale(10, RoundingMode.FLOOR).stripTrailingZeros()).append(" ");
            }
        }
      return  unsettledOperand.size()>0 ? result.append(System.getProperty("line.separator")).
              append("(the "+ unsettledOperand+ " were not pushed on to the stack due to the previous error)").toString():
              result.toString();
    }
}