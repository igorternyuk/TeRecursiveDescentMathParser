package com.recursivedescentmathparser.tests;

import com.recursivedescentmathparser.Expression;
import com.recursivedescentmathparser.MathParser;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by igor on 10.03.18.
 */
public class MathParserTest {
    @Test
    public void arithmeticExpressionsTes(){
        MathParser parser = new MathParser();
        final double EPS = 0.000001;
        assertTrue(parser.parse("0").evaluate() == 0);
        assertTrue(Math.abs(parser.parse("2+2").evaluate() - 4.0) <= EPS);
        assertTrue(Math.abs(parser.parse("2+3*5").evaluate() - 17.0) == 0);
        assertTrue(Math.abs(parser.parse("2+3*5-38/2").evaluate() + 2.0) <= EPS);
        assertTrue(Math.abs(parser.parse("2*(5+7)").evaluate() - 24.0) <= EPS);
        assertTrue(Math.abs(parser.parse("2+5*3-38/2+28/2").evaluate() - 12.0) <= EPS);
        assertTrue(Math.abs(parser.parse("3.14*100+12^2*2").evaluate() - 602.0) <= EPS);
        assertTrue(Math.abs(parser.parse("3+2*(12-5)").evaluate() - 17.0) <= EPS);
    }

    @Test
    public void functionsTest(){
        MathParser parser = new MathParser();
        final double EPS = 0.000001;
        assertTrue(Math.abs(parser.parse("sqrt(49)+14/2").evaluate() - 14.0) <= EPS);
        assertTrue(Math.abs(parser.parse("cbrt(27)+14/2").evaluate() - 10.0) <= EPS);
        assertTrue(Math.abs(parser.parse("2^2").evaluate() - 4.0) <= EPS);
        assertTrue(Math.abs(parser.parse("2^5").evaluate() - 32.0) <= EPS);
    }

    @Test
    public void logicalOperatorsTest(){
        MathParser parser = new MathParser();
        final double EPS = 0.000001;
        assertTrue(Math.abs(parser.parse("(5!=7)*8+(9<14)*100").evaluate() - 108.0) <= EPS);
        assertTrue(Math.abs(parser.parse("10<=100").evaluate()-1.0) <= EPS);
    }

    @Test
    public void trigonometricExpressionsTest(){
        MathParser parser = new MathParser();
        final double EPS = 0.000001;
        assertTrue(Math.abs(parser.parse("sin(Pi/6) * 2").evaluate() - 1.0) <= EPS);
        assertTrue(Math.abs(parser.parse("cos(Pi/3) * 2").evaluate() - 1.0) <= EPS);
        assertTrue(Math.abs(parser.parse("tg(Pi/4)").evaluate() - 1.0) <= EPS);
        assertTrue(Math.abs(parser.parse("(sin(Pi/4))^2+(cos(Pi/4))^2").evaluate() -
                1.0) <= EPS);
        assertTrue(Math.abs(parser.parse("(tg(Pi/4))^2+1").evaluate() -
                2.0) <= EPS);
        assertTrue(Math.abs(parser.parse("180*arcsin(0.5)/Pi").evaluate() - 30.0) <= EPS);
    }

    @Test
    public void testParserWithVariables(){
        MathParser parser = new MathParser();
        final double EPS = 0.000001;
        assertTrue(Math.abs(parser.parse("Pi").evaluate() - Math.PI) <= EPS);
        assertTrue(Math.abs(parser.parse("E").evaluate() - Math.E) <= EPS);
        assertTrue(parser.parse("E^Pi > Pi^E").evaluate() == 1.0);

    }
}