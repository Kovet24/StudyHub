package online.itklabs.academy.task1.parser;

import online.itklabs.academy.task1.enums.Status;
import online.itklabs.academy.task1.model.ErrorLog;
import online.itklabs.academy.task1.model.TestResult;

import java.util.*;

public class LogParser {
    private final Map<String, TestResult> result;
    private final List<ErrorLog> errors;

    public LogParser() {
        result = new LinkedHashMap<>(16, 0.75f, true);
        errors = new ArrayList<>();

    }

    public List<TestResult> parse(List<String> lines) {
        result.clear();
        errors.clear();

        for (String line : lines) {
            String[] parts = line.split("\\|");

            if (parts.length != 3) {
                errors.add(new ErrorLog(line, "Разделительного символа '|' больше/меньше, чем 2"));
                continue;
            }

            String name = parts[0];
            String statusStr = parts[1];
            String durationStr = parts[2];

            Status status = parseStatus(statusStr);
            if (status == null) {
                errors.add(new ErrorLog(line, "Невалидный статус - %s".formatted(statusStr)));
                continue;
            }

            Long duration = parseDuration(durationStr);
            if (duration == null) {
                errors.add(new ErrorLog(line, "Не число в длительности"));
                continue;
            } else if (duration < 0) {
                errors.add(new ErrorLog(line, "Отрицательное число в длительности"));
                continue;
            }

            result.put(name, new TestResult(name, status, duration));
        }

        return new ArrayList<>(result.values());
    }

    private Status parseStatus(String status) {
        try {
            return Status.valueOf(status);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private Long parseDuration(String durationStr) {
        try {
            return Long.parseLong(durationStr);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Optional<TestResult> firstFailed(List<TestResult> results) {
        for (TestResult result : results) {
            if (result.status() == Status.FAILED) {
                return Optional.of(result);
            }
        }
        return Optional.empty();
    }

    public List<TestResult> getResults() {
        return new ArrayList<>(result.values());
    }

    public List<ErrorLog> getErrors() {
        return errors;
    }
}