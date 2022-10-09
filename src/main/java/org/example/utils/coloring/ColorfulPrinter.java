package org.example.utils.coloring;

public class ColorfulPrinter {

    public static void printColorfullyAndReset(Color color, String text){
        System.out.print(color.getValue() + text + Color.ANSI_RESET.getValue());
    }

    public static void printlnColorfullyAndReset(Color color, String text){
        System.out.println(color.getValue() + text + Color.ANSI_RESET.getValue());
    }

}