package ru.otus.hw.commands;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.h2.tools.Console;

import java.sql.SQLException;

@ShellComponent
public class ConsoleDBCommands {
    @ShellMethod(value = "Opening h2db web console", key = "h2db")
    public void runH2dbConsole() throws SQLException {
        Console.main();
    }
}
