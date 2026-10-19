package online.itklabs.academy.task1.model;


import online.itklabs.academy.task1.enums.Status;

public record TestResult(String name, Status status, long durationMs) {
}
