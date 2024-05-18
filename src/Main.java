import com.sun.source.tree.Tree;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
    private static final TreeMap<Character, Integer> romanValues = new TreeMap<>();
    private static final TreeMap<Integer, String> arabianValues = new TreeMap<>();

    static {
        romanValues.put('I', 1);
        romanValues.put('V', 5);
        romanValues.put('X', 10);
        romanValues.put('L', 50);
        romanValues.put('C', 100);
        romanValues.put('D', 500);
        romanValues.put('M', 1000);

        arabianValues.put(100, "C");
        arabianValues.put(90, "XC");
        arabianValues.put(50, "L");
        arabianValues.put(40, "XL");
        arabianValues.put(10, "X");
        arabianValues.put(9, "IX");
        arabianValues.put(5, "V");
        arabianValues.put(4, "IV");
        arabianValues.put(1, "I");
    }

    private static int romanToInt(String s) {
        int result = 0;
        int previousRomanValue = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            int currentRomanValue = romanValues.get(s.charAt(i));
            if (currentRomanValue < previousRomanValue){
                result -= currentRomanValue;
            }else{
                result += currentRomanValue;
            }
            previousRomanValue = currentRomanValue;
        }
        return result;
    }

    private static String intToRoman(int number) {
        String roman = "";
        int arabianKey;
        do {
            arabianKey = arabianValues.floorKey(number);
            roman += arabianValues.get(arabianKey);
            number -= arabianKey;
        }while (number != 0);
        return roman;
    }

    public static String calc(String input) {
        String[] parts = input.split(" ");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Введено в неправильном формате");
        }

        String num1 = parts[0];
        String operator = parts[1];
        String num2 = parts[2];

        if ((num1.matches("\\d+") &&!num2.matches("\\d+")) || (!num1.matches("\\d+") && num2.matches("\\d+"))) {
            throw new RuntimeException("Выражения с разными типами переменных или нецелые не допускаются");
        }

        int intNum1 = 0;
        int intNum2 = 0;

        if (isRoman(num1)){
            intNum1 = romanToInt(num1);
        }else{
            intNum1 = Integer.parseInt(num1);
        }
        if (isRoman(num2)){
            intNum2 = romanToInt(num2);
        }else{
            intNum2 = Integer.parseInt(num2);
        }


        if (intNum1 < 1 || intNum1 > 10 || intNum2 < 1 || intNum2 > 10) {
            throw new IllegalArgumentException("Числа должны быть в пределах от 1 до 10");
        }



        int result;
        switch (operator) {
            case "+":
                result = intNum1 + intNum2;
                break;
            case "-":
                result = intNum1 - intNum2;
                break;
            case "*":
                result = intNum1 * intNum2;
                break;
            case "/":
                result = intNum1 / intNum2;
                break;
            default:
                throw new IllegalArgumentException("Неизвестная операция");
        }

        if (isRoman(num1) && isRoman(num2)) {
            if (result > 0) {
                return intToRoman(result);
            }else{
                throw new IllegalArgumentException("Римские числа могут быть только положительные");
            }
        } else {
            return String.valueOf(result);
        }
    }

    private static boolean isRoman(String s) {
        for (char c : s.toCharArray()) {
            if (!romanValues.containsKey(c) && s.matches("\\d+")) { //дописать что G нет таких букв
                return false;
            } else if (!romanValues.containsKey(c) && !s.matches("\\d+")) {
                throw new IllegalArgumentException("Одно из чисел не римское.");
            }
        }
        return true;
    }


    public static void main(String[] args) {
        System.out.print("Введите выражение в формате 'число оператор число': ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String result = calc(String.valueOf(input));
        System.out.println(result);
    }
}