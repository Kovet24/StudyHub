package online.itklabs.academy.task1;

import online.itklabs.academy.task1.model.ErrorLog;
import online.itklabs.academy.task1.model.TestResult;
import online.itklabs.academy.task1.parser.LogParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TestLogParser {
    public static void main(String[] args) {
        Path path;

        if (args.length == 0) {
            path = Path.of("src/main/resources/task1/run.log");
        } else if (args.length == 1) {
            path = Path.of(args[0]);
        } else {
            String message = "В args можно передать путь к .log файлу или запустить приложение без параметров.";
            throw new IllegalArgumentException(message);
        }

        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            while (reader.ready()) {
                lines.add(reader.readLine());
            }
        } catch (IOException e) {
            throw new RuntimeException("Не удалось прочитать файл: " + path, e);
        }

        LogParser parser = new LogParser();
        List<TestResult> results = parser.parse(lines);
        List<ErrorLog> errors = parser.getErrors();

        printResult(results);
        printErrors(errors);
        printFirstFailed(parser);
    }

    private static void printResult(List<TestResult> results) {
        System.out.println("Результаты:");

        for (TestResult result : results) {
            System.out.printf("\t%s - %s (%d ms)\n", result.name(), result.status(), result.durationMs());
        }
    }

    private static void printErrors(List<ErrorLog> errors) {
        System.out.println("Битые строки:");

        for (ErrorLog error : errors) {
            System.out.printf("\t%s - %s\n", error.line(), error.message());
        }
    }

    private static void printFirstFailed(LogParser parser) {
        System.out.print("Первый упавший: ");
        parser.firstFailed(parser.getResults()).ifPresentOrElse(
                failed -> System.out.printf("%s - %s (%d ms)\n",
                        failed.name(), failed.status(), failed.durationMs()),
                () -> System.out.println("Падений нет")
        );
    }
}