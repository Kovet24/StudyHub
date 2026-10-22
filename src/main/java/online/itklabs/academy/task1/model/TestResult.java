package online.itklabs.academy.task1.model;


import online.itklabs.academy.task1.enums.Status;

// Нам не нужно переопределять equals и hashCode, так как компилятор делает это за нас, когда видит Record класс
public record TestResult(String name, Status status, long durationMs) {
}
