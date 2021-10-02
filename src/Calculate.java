package com.company;

import java.math.BigDecimal;
import java.util.LinkedList;

class InputException extends Exception {
        InputException() {
                super();
        }
}

public class Calculate {
        public String evaluationExpression(String expression) throws InputException {
                /* operator in the stack */
                LinkedList<Character> stackOptr = new LinkedList<>();
                /* operand in the stack */
                LinkedList<Double> stackOpnd = new LinkedList<>();
                /* add '@' to the bottom of the stack*/
                stackOptr.addFirst('@');

                int i = 0;
                while (i < expression.length()) {
                        if ('0' <= expression.charAt(i) && '9' >= expression.charAt(i)) {
                                double number;
                                double integerPart = 0, decimalPart = 0;
                                boolean beforeDecimalPoint = true;
                                int decimalRank = 10;

                                while (('0' <= expression.charAt(i) && '9' >= expression.charAt(i)) || '.' == expression.charAt(i)) {

                                        if ('.' == expression.charAt(i)) {
                                                if (beforeDecimalPoint) {
                                                        beforeDecimalPoint = false;
                                                        i++;
                                                } else {
                                                        throw new InputException();
                                                }
                                        }

                                        if (beforeDecimalPoint) {
                                                integerPart = integerPart * 10 + (expression.charAt(i) - '0');
                                        } else {
                                                decimalPart = decimalPart + (double) (expression.charAt(i) - '0') / decimalRank;
                                                decimalRank *= 10;
                                        }

                                        i++;

                                        if ('c' == expression.charAt(i) || 's' == expression.charAt(i) || '(' == expression.charAt(i)) {
                                                throw new InputException();
                                        }
                                }
                                number = integerPart + decimalPart;
                                stackOpnd.addFirst(number);
                        } else {
                                switch (precede(stackOptr.getFirst(), expression.charAt(i))) {
                                case '<':
                                        stackOptr.addFirst(expression.charAt(i));
                                        break;
                                case '=':
                                        stackOptr.removeFirst();
                                        break;
                                case '>':
                                        if ('c' == stackOptr.getFirst()) {
                                                double a = stackOpnd.removeFirst();
                                                stackOpnd.addFirst(Math.cos(a));
                                        } else if ('s' == stackOptr.getFirst()) {
                                                double a = stackOpnd.removeFirst();
                                                stackOpnd.addFirst(Math.sin(a));
                                        } else {
                                                double a = stackOpnd.removeFirst();
                                                double b = stackOpnd.removeFirst();
                                                stackOpnd.addFirst(operate(a, stackOptr.getFirst(), b));
                                        }
                                        i--;
                                        stackOptr.removeFirst();
                                        break;
                                }
                                if ('s' == expression.charAt(i) || 'c' == expression.charAt(i)) {
                                        i += 3;
                                } else {
                                        i++;
                                }
                        }
                }

                return String.valueOf(stackOpnd.getFirst());
        }

        public char precede(char ch1, char ch2) {
                int a = 0, b = 0;

                switch (ch1) {
                case '+':
                        a = 5;
                        break;
                case '-':
                        a = 5;
                        break;
                case '*':
                        a = 15;
                        break;
                case '/':
                        a = 15;
                        break;
                case '(':
                        a = 2;
                        break;
                case ')':
                        a = 25;
                        break;
                case '^':
                        a = 17;
                        break;
                case '#':
                        a = 0;
                        break;
                case 's':
                        a = 20;
                        break;
                case 'c':
                        a = 20;
                        break;
                case '@':
                        a = 0;
                        break;
                }

                switch (ch2) {
                case '+':
                        b = 3;
                        break;
                case '-':
                        b = 3;
                        break;
                case '*':
                        b = 10;
                        break;
                case '/':
                        b = 10;
                        break;
                case '(':
                        b = 22;
                        break;
                case ')':
                        b = 2;
                        break;
                case '^':
                        b = 16;
                        break;
                case 's':
                        b = 19;
                        break;
                case 'c':
                        b = 19;
                        break;
                case '#':
                        b = 0;
                        break;
                case '@':
                        b = 0;
                        break;
                }

                if (a > b)
                        return '>';
                else if (a == b)
                        return '=';
                else
                        return '<';

        }

        double operate(double a, char sign, double b) {
                BigDecimal aa = BigDecimal.valueOf(a);
                BigDecimal bb = BigDecimal.valueOf(b);

                BigDecimal answer = null;

                switch (sign) {
                case '+':
//                        answer = b + a;
                        answer = bb.add(aa);
                        break;
                case '-':
//                        answer = b - a;
                        answer = bb.subtract(aa);
                        break;
                case '*':
//                        answer = b * a;
                        answer = bb.multiply(aa);
                        break;
                case '/':
//                        answer = b / a;
                        answer = bb.divide(aa);
                        break;
                case '^':
//                        answer = Math.pow(b, a);
                        answer = bb.pow(aa.intValue());
                        break;
                default:

                        break;
                }

                System.out.println(answer);

                return answer.doubleValue();
        }

}
