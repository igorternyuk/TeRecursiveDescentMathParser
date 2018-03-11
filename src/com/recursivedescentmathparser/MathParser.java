package com.recursivedescentmathparser;

import java.util.Map;
import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by igor on 09.03.18.
 */
public class MathParser {
    private final Map<String, Function<Double,Double>> functions = createFunctionsMap();
    private final Map<String, BiFunction<Double,Double,Double>> binaryOperatorsMap = createBinaryOperatorsMap();
    private final Map<String, Double> variables = new HashMap<>();
    private String inputExpression;
    int currentPosition = -1, currentCharacter;

    public MathParser(){
        this.addConstantsToVariableMap();
        this.inputExpression = "0";
        this.currentPosition = -1;
    }

    public MathParser(final String inputExpression){
        this.addConstantsToVariableMap();
        this.inputExpression = inputExpression;
        this.currentPosition = -1;
        this.currentCharacter = -1;
    }

    public MathParser(final String inputExpression, final Map<String, Double> variables){
        this(inputExpression);
        //this.variables = variables;
        this.variables.putAll(variables);
    }

    public Map<String, Double> getVariables() {
        return variables;
    }

    private void addConstantsToVariableMap(){
        variables.put("E", Math.E);
        variables.put("Pi", Math.PI);
    }

    public void addVariable(final String varName, final double value){
        this.variables.put(varName, value);
    }

    public void addFunction(final String funcName, final Function<Double,Double> function){
        this.functions.put(funcName, function);
    }

    public void removeFunction(final String funcName){
        this.functions.remove(funcName);
    }

    public void removeVariable(final String varName){
        this.variables.remove(varName);
    }

    private static Map<String, Function<Double,Double>> createFunctionsMap(){
        Map<String, Function<Double,Double>> functionsMap = new HashMap<>();
        functionsMap.put("sin", arg -> Math.sin(arg));
        functionsMap.put("cos", arg -> Math.cos(arg));
        functionsMap.put("tg", arg -> Math.tan(arg));
        functionsMap.put("ctg", arg -> 1.0 / Math.tan(arg));
        functionsMap.put("sec", arg -> 1.0 / Math.sin(arg));
        functionsMap.put("csec", arg -> 1.0 / Math.cos(arg));
        functionsMap.put("arcsin", arg -> Math.asin(arg));
        functionsMap.put("arccos", arg -> Math.acos(arg));
        functionsMap.put("arctg", arg -> Math.atan(arg));
        functionsMap.put("square", arg -> arg * arg);
        functionsMap.put("cube", arg -> arg * arg * arg);
        functionsMap.put("sqrt", arg -> Math.sqrt(arg));
        functionsMap.put("cbrt", arg -> Math.cbrt(arg));
        functionsMap.put("signum", arg -> Math.signum(arg));
        functionsMap.put("abs", arg -> Math.abs(arg));
        functionsMap.put("ln", arg -> Math.log10(arg) / Math.log10(Math.E));
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
        binaryOperatorsMap.put(">", (a,b) -> a > b ? 1.0 : 0 );
        binaryOperatorsMap.put("<", (a,b) -> a < b ? 1.0 : 0);
        binaryOperatorsMap.put(">=", (a,b) -> a > b ? 1.0 : 0 );
        binaryOperatorsMap.put("<=", (a,b) -> a < b ? 1.0 : 0);
        binaryOperatorsMap.put("==", (a,b) -> a > b ? 1.0 : 0 );
        binaryOperatorsMap.put("!=", (a,b) -> a < b ? 1.0 : 0);
        binaryOperatorsMap.put("e", (a,b) -> a * Math.pow(10,b));
        //binaryOperatorsMap.put("log", (a,b) -> Math.log10(a) / Math.log10(b));
        return binaryOperatorsMap;
    }

    private boolean isDigit(final int ch){
        return (ch >= '0' && ch <= '9') || ch == '.';
    }

    private boolean isSymbol(final int ch){
        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z');
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

    private boolean consumeString(final String stringToConsume){
        while (this.currentCharacter == ' ') nextCharacter();
        if(this.currentPosition + stringToConsume.length() < this.inputExpression.length()) {
            if (this.inputExpression.substring(this.currentPosition,
                    this.currentPosition + stringToConsume.length()).equals(stringToConsume)) {
                this.currentPosition += stringToConsume.length() - 1;
                nextCharacter();
                return true;
            }
        }
        return false;
    }

    public Expression parse(){
        nextCharacter();
        Expression result = parseExpression();
        System.out.println("result = " + result.evaluate());
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
        Expression x = this.parseHighPriorityOperators();
        for(;;){
            final double evalX = x.evaluate();
            if(consume('+')){
                Expression b = this.parseHighPriorityOperators();
                x = (() -> evalX + b.evaluate());
            } else if(consume('-')){
                Expression b = this.parseHighPriorityOperators();
                x = (() -> evalX - b.evaluate());
            } else {
                return x;
            }
        }
    }

    private Expression parseHighPriorityOperators(){
        Expression x = this.parseToken();
        for (;;){
            final double evalX = x.evaluate();
            if(consume('*')){
                Expression b = this.parseToken();
                x = (() -> evalX * b.evaluate());
            } else if(consume('/')){
                Expression b = this.parseToken();
                x = (() -> evalX / b.evaluate());
            } else {
                return x;
            }
        }
    }

    private Expression parseToken(){
        if(consume('+')){
            return () -> parseToken().evaluate(); //Unary plus
        } else if(consume('-')){
            return () -> -parseToken().evaluate(); //Unary minus
        }
        Expression res;
        int startPosition = this.currentPosition;
        if(consume('(')){
            res = this.parseExpression();
            if(!consume(')')) throw new RuntimeException("Unbalanced parentheses");
        } else if(isDigit(this.currentCharacter)){
            while (isDigit(this.currentCharacter)) nextCharacter();
            System.out.println("startPosition = " + startPosition + " this.currentPosition = " + this.currentPosition);
            double x = Double.parseDouble(this.inputExpression.substring(startPosition, this.currentPosition));
            System.out.println("x = " + x);
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
                for (Map.Entry<String, Function<Double,Double>> entry: functions.entrySet()){
                    if(entry.getKey().equals(func)){
                        res = () -> entry.getValue().apply(x.evaluate());
                        wasFunctionFound = true;
                        break;
                    }
                }
                if(!wasFunctionFound){
                    throw new RuntimeException("Unknown function or variable " + func);
                }
            }
        } else {
            throw new RuntimeException("Unexpected: " + (char)this.currentCharacter);
        }

        for(Map.Entry<String, BiFunction<Double,Double,Double>> entry: this.binaryOperatorsMap.entrySet()){
            if(this.consumeString(entry.getKey())){
                final Expression a = res;
                final Expression b = this.parseToken();
                res = () -> entry.getValue().apply(a.evaluate(),b.evaluate());
                break;
            }
        }
        return res;
    }
}
