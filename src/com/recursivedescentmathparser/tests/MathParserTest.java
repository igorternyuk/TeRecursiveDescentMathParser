package com.recursivedescentmathparser.tests;

import com.recursivedescentmathparser.parser.Expression;
import com.recursivedescentmathparser.parser.MathParser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by igor on 10.03.18.
 * Last edited 13.03.18
 */
public class MathParserTest {

    private static final double EPS = 1e-9;
    private MathParser parser;

    @Before
    public void setUp() {
        parser = new MathParser();
    }

    @Test
    public void zeroTest() {
        assertEquals(0, parser.parse("0").evaluate(), EPS);
    }

    @Test
    public void unaryOperatorsTest() {
        assertEquals(0, parser.parse("-0").evaluate(), EPS);
        assertEquals(0, parser.parse("+0").evaluate(), EPS);
        assertEquals(-5, parser.parse("-5").evaluate(), EPS);
        assertEquals(5, parser.parse("+5").evaluate(), EPS);
        assertEquals(4, parser.parse("+++4").evaluate(), EPS);
        assertEquals(4, parser.parse("--4").evaluate(), EPS);
    }

    @Test
    public void exponentialFormatTest() {
        assertEquals(5e7, parser.parse("5e7").evaluate(), EPS);
        assertEquals(5e7, parser.parse("+5e+7").evaluate(), EPS);
        assertEquals(5e-7, parser.parse("5e-7").evaluate(), EPS);
        assertEquals(5e+7, parser.parse("5e+7").evaluate(), EPS);
        assertEquals(-5e+7, parser.parse("-5e+7").evaluate(), EPS);
    }

    @Test
    public void additionTest() {
        assertEquals(4, parser.parse("2+2").evaluate(), EPS);
        assertEquals(17, parser.parse("10 + 7").evaluate(), EPS);
        assertEquals(100, parser.parse("-7 + 107").evaluate(), EPS);
        assertEquals(5e7 + 7e5, parser.parse("5e7 + 7e5").evaluate(), EPS);
    }

    @Test
    public void subtractionTest() {
        assertEquals(8, parser.parse("10-2").evaluate(), EPS);
        assertEquals(12, parser.parse("10--2").evaluate(), EPS);
        assertEquals(3001, parser.parse("3999 - 998").evaluate(), EPS);
        assertEquals(9, parser.parse("87 - 78").evaluate(), EPS);
        assertEquals(9e7 - 3e5, parser.parse("9e7 - 3e5").evaluate(), EPS);
    }

    @Test
    public void multiplicationTest() {
        assertEquals(20, parser.parse("10*2").evaluate(), EPS);
        assertEquals(91, parser.parse("13*7").evaluate(), EPS);
        assertEquals(51, parser.parse("3*17").evaluate(), EPS);
        assertEquals(9e7 * 3e5, parser.parse("9e7 * 3e5").evaluate(), EPS);
    }

    @Test
    public void divisionTest() {
        assertEquals(20, parser.parse("100 / 5").evaluate(), EPS);
        assertEquals(23, parser.parse("69 / 3").evaluate(), EPS);
        assertEquals(31, parser.parse("93 / 3").evaluate(), EPS);
        assertEquals(9e7 / 3e5, parser.parse("9e7 / 3e5").evaluate(), EPS);
    }

    @Test
    public void powerTest() {
        assertEquals(Math.pow(5, 7), parser.parse("5^7").evaluate(), EPS);
        assertEquals(196, parser.parse("14^2").evaluate(), EPS);
        assertEquals(15625, parser.parse("25^3").evaluate(), EPS);
        assertEquals(10000, parser.parse("1e2^2").evaluate(), EPS);
        assertEquals(-5e+7, parser.parse("-5e+7").evaluate(), EPS);
    }

    @Test
    public void parenthesesGroupingTest() {
        assertEquals(5, parser.parse("(5)").evaluate(), EPS);
        assertEquals(5, parser.parse("(+5)").evaluate(), EPS);
        assertEquals(-5, parser.parse("(-5)").evaluate(), EPS);
        assertEquals(-5, parser.parse("-(5)").evaluate(), EPS);
        assertEquals(5, parser.parse("+(5)").evaluate(), EPS);
        assertEquals(5, parser.parse("-(-5)").evaluate(), EPS);
        assertEquals(-5, parser.parse("+(-5)").evaluate(), EPS);
        assertEquals(57, parser.parse("2 * (3 - 17) + 5 * (2 + 15)").evaluate(), EPS);
        assertEquals(24.0, parser.parse("2*(5+7)").evaluate(), EPS);
    }

    @Test
    public void arithmeticExpressionsTest() {
        assertEquals(17.0, parser.parse("2+3*5").evaluate(), EPS);
        assertEquals(-2.0, parser.parse("2+3*5-38/2").evaluate(), EPS);
        assertEquals(12.0, parser.parse("2+5*3-38/2+28/2").evaluate(), EPS);
        assertEquals(602.0, parser.parse("3.14*100+12^2*2").evaluate(), EPS);
        assertEquals(17.0, parser.parse("3+2*(12-5)").evaluate(), EPS);
        assertEquals(3e+4 + 2e-7 * (12e-2 - 5e+7),parser.parse("3e+4+2e-7*(12e-2-5e+7)").evaluate(),  EPS);
    }

    @Test
    public void functionsTest() {
        assertEquals(-5, parser.parse("signum(-100.78)*5").evaluate(), EPS);
        assertEquals(14.0, parser.parse("signum(200.87)*14").evaluate(), EPS);
        assertEquals(14.0, parser.parse("sqrt(49)+14/2").evaluate(), EPS);
        assertEquals(10.0, parser.parse("cbrt(27)+14/2").evaluate(), EPS);
        assertEquals(2, parser.parse("sqrt(4)").evaluate(), EPS);
        assertEquals(16, parser.parse("sqr(4)").evaluate(), EPS);
        assertEquals(27, parser.parse("cube(3)").evaluate(), EPS);
        assertEquals(125, parser.parse("cube(5)").evaluate(), EPS);
        assertEquals(5, parser.parse("sqrt(sqr(3) + sqr(4))").evaluate(), EPS);
        assertEquals(10, parser.parse("sqrt(sqr(6) + sqr(8))").evaluate(), EPS);
    }

    @Test
    public void exponentTest() {
        assertEquals(Math.exp(4), parser.parse("exp(4)").evaluate(), EPS);
        assertEquals(Math.exp(-7), parser.parse("exp(-7)").evaluate(), EPS);
        assertEquals(Math.cosh(4), parser.parse("5e-1*(exp(4) + exp(-4))").evaluate(), EPS);
        assertEquals(Math.sinh(13), parser.parse("5e-1*(exp(13) - exp(-13))").evaluate(), EPS);
        assertEquals(Math.exp(-4e2), parser.parse("exp(-4e2)").evaluate(), EPS);
    }

    @Test
    public void logarithmicFunctionsTest() {
        assertEquals(1.0, parser.parse("ln(E)").evaluate(), EPS);
        assertEquals(2.0, parser.parse("ln(E^2)").evaluate(), EPS);
        assertEquals(9, parser.parse("log2(512)").evaluate(), EPS);
        assertEquals(4, parser.parse("log4(256)").evaluate(), EPS);
        assertEquals(3, parser.parse("log8(512)").evaluate(), EPS);
        assertEquals(2, parser.parse("log10(100)").evaluate(), EPS);
        assertEquals(2, parser.parse("log16(256)").evaluate(), EPS);
    }

    @Test
    public void trigonometricFunctionsTest() {
        assertEquals(0.5, parser.parse("sin(Pi/6)").evaluate(), EPS);
        assertEquals(Math.sin(2 * Math.PI / 3), parser.parse("sin(2*Pi/3)").evaluate(), EPS);
        assertEquals(0.5, parser.parse("sin(5*Pi/6)").evaluate(), EPS);
        assertEquals(Math.sin(Math.PI / 4), parser.parse("sin(Pi/4)").evaluate(), EPS);
        assertEquals(0.5,parser.parse("cos(Pi/3)").evaluate(),  EPS);
        assertEquals(-0.5, parser.parse("cos(2*Pi/3)").evaluate(), EPS);
        assertEquals(Math.sin(-Math.PI / 4), parser.parse("sin(-Pi/4)").evaluate(), EPS);
        assertEquals(1.0, parser.parse("tg(Pi/4)").evaluate(), EPS);
        assertEquals(-1.0, parser.parse("ctg(3*Pi/4)").evaluate(), EPS);
        assertEquals(2.0, parser.parse("sec(Pi/6)").evaluate(), EPS);
        assertEquals(2.0, parser.parse("cosec(Pi/3)").evaluate(), EPS);
    }

    @Test
    public void trigonometricArcFunctionsTest() {
        assertEquals(0.0, parser.parse("arcsin(0)").evaluate(), EPS);
        assertEquals(0.0, parser.parse("arccos(1.0)").evaluate(), EPS);
        assertEquals(Math.PI / 4, parser.parse("arctg(1)").evaluate(), EPS);
        assertEquals(-Math.PI / 4, parser.parse("arcctg(-1)").evaluate(), EPS);
        assertEquals(Math.PI / 6, parser.parse("arcsec(2)").evaluate(), EPS);
        assertEquals(Math.PI / 4, parser.parse("arcsec(sqrt(2))").evaluate(), EPS);
        assertEquals(Math.PI / 3, parser.parse("arccosec(2)").evaluate(),  EPS);
        assertEquals(Math.PI / 4, parser.parse("arccosec(sqrt(2))").evaluate(), EPS);
    }

    @Test
    public void trigonometricExpressionsTest() {
        assertEquals(1.0, parser.parse("(sin(Pi/4))^2+(cos(Pi/4))^2").evaluate(), EPS);
        assertEquals(2.0, parser.parse("(tg(Pi/4))^2+1").evaluate(), EPS);
        assertEquals(30.0, parser.parse("180*arcsin(0.5)/Pi").evaluate(), EPS);
    }

    @Test
    public void hyperbolicFunctionsTest() {
        assertEquals(0, parser.parse("sh(0)").evaluate(), EPS);
        assertEquals(1.0, parser.parse("ch(0)").evaluate(), EPS);
        assertEquals(0, parser.parse("th(0)").evaluate(),  EPS);
        assertEquals(Double.POSITIVE_INFINITY, parser.parse("cth(0)").evaluate(), EPS);
        assertEquals(Double.POSITIVE_INFINITY, parser.parse("sech(0)").evaluate(), EPS);
        assertEquals(1, parser.parse("cosech(0)").evaluate(), EPS);
        for (double x = 1; x < 100; ++x) {
            assertEquals(Math.sinh(x), parser.parse("sh(" + x + ")").evaluate(), EPS);
            assertEquals(Math.cosh(x), parser.parse("ch(" + x + ")").evaluate(), EPS);
            assertEquals(Math.tanh(x), parser.parse("th(" + x + ")").evaluate(), EPS);
            assertEquals(1 / Math.tanh(x), parser.parse("cth(" + x + ")").evaluate(), EPS);
            assertEquals(1 / Math.sinh(x), parser.parse("sech(" + x + ")").evaluate(), EPS);
            assertEquals(1 / Math.cosh(x), parser.parse("cosech(" + x + ")").evaluate(), EPS);
        }
    }

    @Test
    public void hyperbolicArcFunctionsTest() {
        assertEquals(0.14, MathParser.asinh(Math.sinh(0.14)), EPS);
        assertEquals(200, MathParser.acosh(Math.cosh(200)), EPS);
        assertEquals(0.5, MathParser.atanh(Math.tanh(0.5)), EPS);
        assertEquals(5, parser.parse("arcsh(" + (Math.sinh(5)) + ")").evaluate(), EPS);
        assertEquals(7, parser.parse("arcch(" + (Math.cosh(7)) + ")").evaluate(), EPS);
        assertEquals(0.75, parser.parse("arcth(" + (Math.tanh(0.75)) + ")").evaluate(), EPS);
        assertEquals(5, parser.parse("arcsech(" + (1 / Math.sinh(5)) + ")").evaluate(), EPS);
        assertEquals(7, parser.parse("arccosech(" + (1 / Math.cosh(7)) + ")").evaluate(), EPS);
        assertEquals(0.75, parser.parse("arccth(" + (1 / Math.tanh(0.75)) + ")").evaluate(), EPS);
    }

    @Test
    public void logicalOperatorsTest(){
        assertEquals(0, parser.parse("5>7").evaluate(), EPS);
        assertEquals(1, parser.parse("50>7").evaluate(), EPS);
        assertEquals(0, parser.parse("10050<200").evaluate(), EPS);
        assertEquals(1, parser.parse("100<200").evaluate(), EPS);
        assertEquals(0, parser.parse("100>=200").evaluate(), EPS);
        assertEquals(1, parser.parse("15>=15").evaluate(), EPS);
        assertEquals(1, parser.parse("225>=15").evaluate(), EPS);
        assertEquals(1.0, parser.parse("10<=100").evaluate(), EPS);
        assertEquals(1.0, parser.parse("10<=10").evaluate(), EPS);
        assertEquals(0.0, parser.parse("1020<=10").evaluate(), EPS);
        assertEquals(0.0, parser.parse("1020==10").evaluate(), EPS);
        assertEquals(1.0, parser.parse("10==10").evaluate(), EPS);
        assertEquals(1.0, parser.parse("1020!=10").evaluate(), EPS);
        assertEquals(0.0,parser.parse("1020!=1020").evaluate(), EPS);
    }

    @Test
    public void biFunctionsTest() {
        assertEquals(5.0, parser.parse("max(5,2)").evaluate(), EPS);
        assertEquals(-57.0, parser.parse("min(-57,100)").evaluate(), EPS);
        assertEquals(5.0, parser.parse("hypot(3,4)").evaluate(), EPS);
        assertEquals(2.0, parser.parse("log(49,7)").evaluate(), EPS);
        assertEquals(5.0, parser.parse("max((2+3),(2+2))").evaluate(), EPS);
        assertEquals(Math.sin(Math.PI / 3), parser.parse("max(sin(Pi/6),sin(Pi/3))").evaluate(), EPS);
    }

    @Test
    public void logicalExpressionsTest() {
        assertEquals(108.0, parser.parse("(5!=7)*8+(9<14)*100").evaluate(), EPS);
        assertEquals(209.0, parser.parse("(100>=70)*9+(500<=3999)*200").evaluate(), EPS);
    }

    @Test
    public void testParserWithVariables() {
        assertEquals(Math.PI, parser.parse("Pi").evaluate(), EPS);
        assertEquals(Math.E, parser.parse("E").evaluate(), EPS);
        assertEquals(Math.pow(Math.E, Math.PI) + Math.pow(Math.PI, Math.E),
                 parser.parse("E^Pi + Pi^E").evaluate(), EPS);
        parser.addVariable("exis", 3.0);
        Expression parabola = parser.parse("exis^2");
        for (double x = -20.0; x <= 20.0; ++x) {
            parser.addVariable("exis", x);
            assertEquals(x *x, parabola.evaluate(), EPS);
        }

        parser.addFunction("degToRad", Math::toRadians);
        parser.addVariable("angle", -2 * Math.PI);
        Expression sine = parser.parse("sin(degToRad(angle))");
        Expression cosine = parser.parse("cos(degToRad(angle))");
        for (double angle = -180.0; angle <= 180; angle += 5) {
            parser.addVariable("angle", angle);
            assertEquals(Math.sin(Math.toRadians(angle)), sine.evaluate(), EPS);
            assertEquals(Math.cos(Math.toRadians(angle)), cosine.evaluate(), EPS);
        }
    }
}