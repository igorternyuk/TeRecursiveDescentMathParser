package com.recursivedescentmathparser.parser;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by igor on 09.03.18.
 */
public class MathParser {
    private final Map<String, Function<Double,Double>> functions = createFunctionsMap();
    private final Map<String, BiFunction<Double,Double,Double>> binaryOperatorsMap = createBinaryOperatorsMap();
    private final Map<String, BiFunction<Double, Double, Double>> biFunctionsMap = createBiFunctionsMap();

    private Map<String, BiFunction<Double, Double, Double>> createBiFunctionsMap() {
        Map<String, BiFunction<Double, Double, Double>> map = new HashMap<>();
        map.put("max", (a, b) -> Math.max(a, b));
        map.put("min", (a, b) -> Math.min(a, b));
        map.put("hypot", (a, b) -> Math.hypot(a, b));
        map.put("log", (a, b) -> Math.log(a) / Math.log(b));
        return map;
    }

    private final Map<String, Double> variables = new HashMap<>();
    private String inputExpression;
    int currentPosition = -1, currentCharacter;

    public MathParser(){
        this.addConstantsToVariableMap();
        this.inputExpression = "0";
        this.currentPosition = -1;
    }

    public Map<String, Double> getVariables() {
        return Collections.unmodifiableMap(this.variables);
    }

    public Map<String, Function<Double,Double>> getFunctions(){
        return Collections.unmodifiableMap(this.functions);
    }

    public Map<String, BiFunction<Double,Double,Double>> getBinaryOperators(){
        return Collections.unmodifiableMap(this.binaryOperatorsMap);
    }

    private void addConstantsToVariableMap(){
        this.variables.put("E", Math.E);
        this.variables.put("Pi", Math.PI);
    }

    public void addVariable(final String varName, final double value){
        this.variables.put(varName, value);
    }

    public void addVariables(final Map<String, Double> variables){
        this.variables.putAll(variables);
    }

    public void removeVariable(final String varName){
        this.variables.remove(varName);
    }

    public void addFunction(final String funcName, final Function<Double,Double> function){
        this.functions.put(funcName, function);
    }

    public void addFunctions(final Map<String,Function<Double,Double>> functions){
        this.functions.putAll(functions);
    }

    public void removeFunction(final String funcName){
        this.functions.remove(funcName);
    }

    public void addBinaryOperator(final String operator, BiFunction<Double,Double,Double> biFunction){
        this.binaryOperatorsMap.put(operator, biFunction);
    }

    public void addBinaryOperators(final Map<String,BiFunction<Double,Double,Double>> operators){
        this.binaryOperatorsMap.putAll(operators);
    }

    public void removeBinaryOperator(final String operatorName){
        this.binaryOperatorsMap.remove(operatorName);
    }

    public static double asinh(final double arg){
        return Math.log(arg + Math.sqrt(arg * arg + 1));
    }

    public static double acosh(final double arg){
        return Math.log(arg + Math.sqrt(arg * arg - 1));
    }

    public static double atanh(final double arg){
        return 0.5 * Math.log((1 + arg) / (1 - arg));
    }

    private static Map<String, Function<Double,Double>> createFunctionsMap(){
        Map<String, Function<Double,Double>> functionsMap = new HashMap<>();
        functionsMap.put("sin", arg -> Math.sin(arg));
        functionsMap.put("cos", arg -> Math.cos(arg));
        functionsMap.put("tg", arg -> Math.tan(arg));
        functionsMap.put("ctg", arg -> 1.0 / Math.tan(arg));
        functionsMap.put("sec", arg -> 1.0 / Math.sin(arg));
        functionsMap.put("cosec", arg -> 1.0 / Math.cos(arg));
        functionsMap.put("arcsin", arg -> Math.asin(arg));
        functionsMap.put("arccos", arg -> Math.acos(arg));
        functionsMap.put("arctg", arg -> Math.atan(arg));
        functionsMap.put("arcsec", arg -> Math.asin(1 / arg));
        functionsMap.put("arccosec", arg -> Math.acos(1 / arg));
        functionsMap.put("arcctg", arg -> Math.atan(1 / arg));
        functionsMap.put("sh", arg -> Math.sinh(arg));
        functionsMap.put("ch", arg -> Math.cosh(arg));
        functionsMap.put("th", arg -> Math.tanh(arg));
        functionsMap.put("cth", arg -> 1.0 / Math.tanh(arg));
        functionsMap.put("sech", arg -> 1.0 / Math.sinh(arg));
        functionsMap.put("cosech", arg -> 1.0 / Math.cosh(arg));
        functionsMap.put("arcsh", arg -> asinh(arg));
        functionsMap.put("arcch", arg -> acosh(arg));
        functionsMap.put("arcth", arg -> atanh(arg));
        functionsMap.put("arcsech", arg -> asinh(1 / arg));
        functionsMap.put("arccosech", arg -> acosh(1 / arg));
        functionsMap.put("arccth", arg -> atanh(1 / arg));
        functionsMap.put("sqr", arg -> arg * arg);
        functionsMap.put("cube", arg -> arg * arg * arg);
        functionsMap.put("sqrt", arg -> Math.sqrt(arg));
        functionsMap.put("cbrt", arg -> Math.cbrt(arg));
        functionsMap.put("signum", arg -> Math.signum(arg));
        functionsMap.put("abs", arg -> Math.abs(arg));
        functionsMap.put("sin", arg -> Math.sin(arg));
        functionsMap.put("exp", arg -> Math.exp(arg));
        functionsMap.put("ln", arg -> Math.log(arg));
        functionsMap.put("log2", arg -> Math.log10(arg) / Math.log10(2));
        functionsMap.put("log4", arg -> Math.log10(arg) / Math.log10(4));
        functionsMap.put("log8", arg -> Math.log10(arg) / Math.log10(8));
        functionsMap.put("log10", arg -> Math.log10(arg));
        functionsMap.put("log16", arg -> Math.log10(arg) / Math.log10(16));
        return functionsMap;
    }

    private static Map<String,BiFunction<Double,Double,Double>> createBinaryOperatorsMap() {
        Map<String,BiFunction<Double,Double,Double>> binaryOperatorsMap = new HashMap<>();
        binaryOperatorsMap.put("^", (a,b) -> Math.pow(a,b));
        binaryOperatorsMap.put(">=", (a,b) -> (a >= b) ? 1.0 : 0 );
        binaryOperatorsMap.put("<=", (a,b) -> (a <= b) ? 1.0 : 0);
        binaryOperatorsMap.put(">", (a,b) -> (a > b) ? 1.0 : 0 );
        binaryOperatorsMap.put("<", (a,b) -> (a < b) ? 1.0 : 0);
        binaryOperatorsMap.put("==", (a,b) -> Double.compare(a,b) == 0 ? 1.0 : 0 );
        binaryOperatorsMap.put("!=", (a,b) -> Double.compare(a,b) != 0 ? 1.0 : 0);
        binaryOperatorsMap.put("e", (a,b) -> a * Math.pow(10,b));
        return binaryOperatorsMap;
    }

    private boolean isDigit(final int ch){
        return (ch >= '0' && ch <= '9') || ch == '.';
    }

    private boolean isSymbol(final int ch){
        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || isDigit(ch);
    }

    private boolean isSpecialOperator(final int ch){
        for(Map.Entry<String,BiFunction<Double,Double,Double>> entry: this.binaryOperatorsMap.entrySet()){
            //System.out.println("(char)ch = " + (char)ch);
            if(entry.getKey().contains(String.valueOf((char)ch))){
                return true;
            }
        }
        return false;
    }

    private void nextCharacter(){
        this.currentCharacter = (++this.currentPosition < this.inputExpression.length()
                ? this.inputExpression.charAt(this.currentPosition)
                : -1);
    }

    private boolean consume(final int charToConsume){
        while (this.currentCharacter == ' ') nextCharacter();
        if(this.currentCharacter == charToConsume){
            nextCharacter();
            return true;
        }
        return false;
    }

    private Expression parse(){
        nextCharacter();
        Expression result = parseExpression();
        //System.out.println("result = " + result.evaluate());
        if(currentPosition < inputExpression.length()) throw new RuntimeException("Unexpected character: " +
                (char)currentCharacter);
        return result;
    }

    public Expression parse(final String expression){
        this.inputExpression = expression;
        this.currentPosition = -1;
        this.currentCharacter = -1;
        return this.parse();
    }
    public Expression parse(final String expression, final Map<String, Double> variables){
        this.inputExpression = expression;
        this.variables.putAll(variables);
        this.currentPosition = -1;
        this.currentCharacter = -1;
        return this.parse();
    }

    private Expression parseExpression(){
        Expression currExpr = this.parseHighPriorityOperators();
        for(;;){
            final double evalCurrExpr = currExpr.evaluate();
            if(consume('+')){
                Expression b = this.parseHighPriorityOperators();
                currExpr = (() -> evalCurrExpr + b.evaluate());
            } else if(consume('-')){
                Expression b = this.parseHighPriorityOperators();
                currExpr = (() -> evalCurrExpr - b.evaluate());
            } else {
                return currExpr;
            }
        }
    }

    private Expression parseHighPriorityOperators(){
        Expression currExpr = this.parseToken();
        for (;;){
            final double evalCurrExpr = currExpr.evaluate();
            if(consume('*')){
                Expression b = this.parseToken();
                currExpr = (() -> evalCurrExpr * b.evaluate());
            } else if(consume('/')){
                Expression b = this.parseToken();
                currExpr = (() -> evalCurrExpr / b.evaluate());
            } else {
                return currExpr;
            }
        }
    }

    private Expression parseToken(){
        if(consume('+')){
            final Expression a = parseToken();
            return () -> a.evaluate(); //Unary plus
        } else if(consume('-')){
            final Expression a = parseToken();
            return () -> -1 * a.evaluate(); //Unary minus
        }
        Expression res;
        int startPosition = this.currentPosition;
        if (consume('(') || consume(',')) {
            res = this.parseExpression();
            if (consume(',')) return res;
            if (!consume(')')) {
                throw new RuntimeException("Unbalanced parentheses");
            }
        } else if(isDigit(this.currentCharacter)){
            while (isDigit(this.currentCharacter)) nextCharacter();
            //System.out.println("startPosition = " + startPosition + " this.currentPosition = " + this.currentPosition);
            double x = Double.parseDouble(this.inputExpression.substring(startPosition, this.currentPosition));
            //System.out.println("x = " + x);
            res = () -> x;
        } else if(isSymbol(this.currentCharacter)){
            while (isSymbol(this.currentCharacter)) nextCharacter();
            final String func = inputExpression.substring(startPosition, this.currentPosition);
            boolean wasVariableFound = false;
            String foundedKey ="";
            for(Map.Entry<String,Double> entry: this.variables.entrySet()){
                if(entry.getKey().equals(func)){
                    foundedKey = entry.getKey();
                    wasVariableFound = true;
                    break;
                }
            }
            if(wasVariableFound){
                final String key = foundedKey;
                res = () -> this.variables.get(key);
            } else {
                res = this.parseToken();
                final Expression x = res;
                boolean wasFunctionFound = false;
                String foundedFuncKey = "";
                for (Map.Entry<String, Function<Double,Double>> entry: functions.entrySet()){
                    if(entry.getKey().equals(func)){
                        foundedFuncKey = entry.getKey();
                        wasFunctionFound = true;
                        break;
                    }
                }
                if (wasFunctionFound) {
                    String key = foundedFuncKey;
                    res = () -> this.functions.get(key).apply(x.evaluate());
                } else {
                    boolean isBiFunctionFound = false;
                    for (Map.Entry<String, BiFunction<Double, Double, Double>> entry : this.biFunctionsMap.entrySet()) {
                        if (entry.getKey().equals(func)) {
                            isBiFunctionFound = true;
                            Expression a = res;
                            Expression b = this.parseExpression();
                            if (!consume(')'))
                                throw new RuntimeException("Missing ) after biFunction second argument");
                            res = () -> entry.getValue().apply(a.evaluate(), b.evaluate());
                            break;
                        }
                    }
                    if (!isBiFunctionFound) {
                        throw new RuntimeException("Unknown function or variable " + func);
                    }
                }
            }
        } else {
            throw new RuntimeException("Unexpected: " + (char)this.currentCharacter);
        }

        startPosition = this.currentPosition;
        while (isSpecialOperator(this.currentCharacter)) nextCharacter();
        final String operator = inputExpression.substring(startPosition, this.currentPosition);
        if(!operator.isEmpty()) {
            boolean isOperatorValid = false;
            for (Map.Entry<String, BiFunction<Double, Double, Double>> entry : this.binaryOperatorsMap.entrySet()) {
                if (entry.getKey().equals(operator)) {
                    final Expression a = res;
                    final Expression b = this.parseToken();
                    res = () -> entry.getValue().apply(a.evaluate(), b.evaluate());
                    isOperatorValid = true;
                    break;
                }
            }
            if(!isOperatorValid){
                throw new RuntimeException("Unknown operator " + operator);
            }
        }
        return res;
    }
}
