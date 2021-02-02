package com.github.raininforest.gerberparserlib;

import com.github.raininforest.gerberparserlib.enums.ArithmeticOperation;
import com.github.raininforest.gerberparserlib.syntaxparser.macrotemplates.ConstantNumber;
import com.github.raininforest.gerberparserlib.syntaxparser.macrotemplates.MacroExpression;
import com.github.raininforest.gerberparserlib.syntaxparser.macrotemplates.Operator;
import com.github.raininforest.gerberparserlib.syntaxparser.macrotemplates.Variable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ExpressionTest {
    private MacroExpression expression;

    @Before
    public void init(){
        expression = new MacroExpression();
        Variable var1 = new Variable(1);
        var1.setValue(0.245);
        Variable var2 = new Variable(2);
        var2.setValue(10);
        expression.addExpressionItem(new Operator(ArithmeticOperation.UNARY_MINUS));
        expression.addExpressionItem(var1);
        expression.addExpressionItem(new Operator(ArithmeticOperation.BINARY_MINUS));
        expression.addExpressionItem(new Operator(ArithmeticOperation.OPENING_PARENTHESIS));
        expression.addExpressionItem(new Operator(ArithmeticOperation.UNARY_MINUS));
        expression.addExpressionItem(new ConstantNumber(1.0));
        expression.addExpressionItem(new Operator(ArithmeticOperation.BINARY_PLUS));
        expression.addExpressionItem(new ConstantNumber(2.0));
        expression.addExpressionItem(new Operator(ArithmeticOperation.MULTIPLY));
        expression.addExpressionItem(var2);
        expression.addExpressionItem(new Operator(ArithmeticOperation.CLOSING_PARENTHESIS));
        System.out.println(expression);
    }

    @Test
    public void calculateTest(){
        System.out.println("Calculated expression value=" + expression.calculate());
        Assert.assertEquals(expression.calculate(),-19.245, 0.00001);
    }
}
