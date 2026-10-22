package online.itklabs.academy.task3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class FlakyDetector {
    public static void main(String[] args) {
        Path path1;
        Path path2;

        if (args.length == 0) {
            path1 = Path.of("src/main/resources/task3/run1.log");
            path2 = Path.of("src/main/resources/task3/run2.log");
        } else if (args.length == 2) {
            path1 = Path.of(args[0]);
            path2 = Path.of(args[1]);
        } else {
            throw new IllegalArgumentException("В args нужно передать два параметра, пути до .log");
        }

        List<String> run1 = readFile(path1);
        List<String> run2 = readFile(path2);

        System.out.println("Flaky-кандидаты: " + flaky(run1, run2));
        System.out.print("Прогон 2, сводка: " + statusSummary(run2));
    }

    private static List<String> readFile(Path path) {
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось прочитать файл: " + path, e);
        }
    }

    public static List<String> flaky(List<String> run1, List<String> run2) {
        Set<String> result = new LinkedHashSet<>(failed(run1));
        result.retainAll(passed(run2));
        return new ArrayList<>(result);
    }

    public static Set<String> failed(List<String> run) {
        return nameWithStatus(run, "FAILED");
    }

    public static Set<String> passed(List<String> run) {
        return nameWithStatus(run, "PASSED");
    }

    private static Set<String> nameWithStatus(List<String> run, String status) {
        Set<String> result = new LinkedHashSet<>();

        for (String line : run) {
            String[] parts = line.split("\\|");

            if (parts[1].equals(status)) {
                result.add(parts[0]);
            }
        }
        return result;
    }

    public static Map<String, Long> statusSummary(List<String> run) {
        Map<String, Long> result = new LinkedHashMap<>();

        for (String line : run) {
            String status = line.split("\\|")[1];
            result.merge(status, 1L, Long::sum);
        }
        return result;
    }
}
