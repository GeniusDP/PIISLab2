package org.example.utils;

import java.io.IOException;

public class ConsoleOrganizer {

    public static void clearConsole() throws IOException {
        System.out.print("\033\143");
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
