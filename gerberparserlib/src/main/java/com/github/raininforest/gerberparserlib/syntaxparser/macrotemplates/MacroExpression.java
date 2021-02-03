package com.github.raininforest.gerberparserlib.syntaxparser.macrotemplates;

import com.github.raininforest.gerberparserlib.enums.ArithmeticOperation;
import com.github.raininforest.gerberparserlib.enums.ExpressionItemType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Arithmetic expression. Used in macro template definition
 *
 * @author Sergey Velesko
 */
public class MacroExpression {
    protected static final Logger log = LogManager.getLogger();
    private final List<ExpressionItem> expressionItems;

    public MacroExpression() {
        expressionItems = new ArrayList<>();
    }

    public void addExpressionItem(ExpressionItem item) {
        expressionItems.add(item);
    }

    public double calculate() {
        return calculatePostfixExpression(infixToPostfixExpression(expressionItems));
    }

    /**
     * Convert expression from infix to postfix notation
     * @param infixExpression List of ExpressionItem in infix notation
     * @return List of ExpressionItem in postfix notation
     * @throws NoSuchElementException
     */
    private List<ExpressionItem> infixToPostfixExpression(List<ExpressionItem> infixExpression) throws NoSuchElementException {
        Deque<Operator> operationStack = new ArrayDeque<>();
        List<ExpressionItem> postfixExpression = new ArrayList<>();
        for (ExpressionItem expressionItem : infixExpression) {
            if (expressionItem.getExpressionItemType() == ExpressionItemType.OPERAND) {
                postfixExpression.add(expressionItem);
            } else {
                if (((Operator) expressionItem).getOperation() == ArithmeticOperation.OPENING_PARENTHESIS) {
                    operationStack.push((Operator) expressionItem);
                } else if (((Operator) expressionItem).getOperation() == ArithmeticOperation.CLOSING_PARENTHESIS) {
                    while (!(operationStack.getFirst().getOperation() == ArithmeticOperation.OPENING_PARENTHESIS)) {
                        postfixExpression.add(operationStack.pop());
                    }
                    if (operationStack.getFirst().getOperation() == ArithmeticOperation.OPENING_PARENTHESIS) {
                        operationStack.pop();
                    }
                } else {
                    Operator currentOperator = (Operator) expressionItem;
                    if (!operationStack.isEmpty()) {
                        if (operationStack.getFirst().getOperation().getPriority() >= currentOperator.getOperation().getPriority()) {
                            postfixExpression.add(operationStack.pop());
                        }
                    }
                    operationStack.push(currentOperator);
                }
            }
        }
        if (!operationStack.isEmpty()) {
            for (ExpressionItem item : operationStack) {
                postfixExpression.add(operationStack.pop());
            }
        }
        log.trace("Postfix Expression: " + postfixExpression);
        return postfixExpression;
    }

    /**
     * Calculating expression in postfix notation
     * @param postfixExpression expression
     * @return double result
     * @throws ArithmeticException
     * @throws NoSuchElementException
     */
    private double calculatePostfixExpression(List<ExpressionItem> postfixExpression) throws ArithmeticException, NoSuchElementException {
        Deque<Double> operandStack = new ArrayDeque<>();
        for (ExpressionItem expressionItem : postfixExpression) {
            if (expressionItem.getExpressionItemType() == ExpressionItemType.OPERAND) {
                operandStack.push(((Operand) expressionItem).getValue());
            } else if (expressionItem.getExpressionItemType() == ExpressionItemType.OPERATOR) {
                Double result = 0.0;
                switch (((Operator) expressionItem).getOperation()) {
                    case UNARY_PLUS: {
                        result = operandStack.pop();
                    }
                    break;
                    case UNARY_MINUS: {
                        Double value = operandStack.pop();
                        result = value * (-1);
                    }
                    break;
                    case BINARY_MINUS: {
                        Double value2 = operandStack.pop();
                        Double value1 = operandStack.pop();
                        result = value1 - value2;
                    }
                    break;
                    case BINARY_PLUS: {
                        Double value2 = operandStack.pop();
                        Double value1 = operandStack.pop();
                        result = value1 + value2;
                    }
                    break;
                    case MULTIPLY: {
                        Double value2 = operandStack.pop();
                        Double value1 = operandStack.pop();
                        result = value1 * value2;
                    }
                    break;
                    case DIVIDE: {
                        Double value2 = operandStack.pop();
                        Double value1 = operandStack.pop();
                        try {
                            result = value1 / value2;
                        } catch (ArithmeticException e) {
                            log.error("Dividing by ZERO in expression");
                            throw new ArithmeticException("Dividing by zero in expression");
                        }

                    }
                    break;
                    default:
                        log.error("Invalid expression!");
                } //end of switch
                operandStack.push(result);
            }
        }
        return operandStack.pop();
    }

    public List<ExpressionItem> getExpressionItems() {
        return expressionItems;
    }

    @Override
    public String toString() {
        StringBuilder resultString = new StringBuilder("Expression: ");
        for (ExpressionItem item : expressionItems) {
            resultString.append('[' + item.toString() + ']');
        }
        return resultString.toString();
    }
}
