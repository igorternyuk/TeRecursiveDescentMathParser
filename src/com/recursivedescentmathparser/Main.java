package com.recursivedescentmathparser;

import com.recursivedescentmathparser.parser.Expression;
import com.recursivedescentmathparser.parser.MathParser;

public class Main {

    public static void main(String[] args) {
        MathParser parser = new MathParser();
        parser.addVariable("exis", 3.0);
        Expression parabola = parser.parse("exis^2");
        for(double x = -20.0; x <= 20.0; ++x){
            parser.addVariable("exis",x);
            System.out.println("----vars------");
            parser.getVariables().forEach((k,v) -> {
                System.out.println(k + " => " + v);
            });
            System.out.println("----vars------");
            System.out.println("x = " + x + " --> " + parabola.evaluate());
        }

        parser.addFunction("degToRad", angle -> Math.toRadians(angle));
        parser.addVariable("angle", -2 * Math.PI);
        Expression sinus = parser.parse("sin(degToRad(angle))");
        for (double angle = -180.0; angle <= 180; angle += 5){
            parser.addVariable("angle", angle);
            System.out.println("sin of " + angle + " is " + sinus.evaluate());
        }
    }
}
