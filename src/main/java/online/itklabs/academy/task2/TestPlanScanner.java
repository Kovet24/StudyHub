package online.itklabs.academy.task2;

import online.itklabs.academy.task2.annotation.TestId;
import online.itklabs.academy.task2.model.Checklist;
import online.itklabs.academy.task2.model.TestStep;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TestPlanScanner {
    public static void main(String[] args) {
        Class<?> clazz = resolveClass(args);
        printSteps(buildPlan(clazz), clazz);
    }

    private static Class<?> resolveClass(String[] args) {
        if (args.length == 0) {
            return Checklist.class;
        } else if (args.length == 1) {
            try {
                return Class.forName(args[0]);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Не найден class - %s. Введите полное название".formatted(args[0]), e);
            }
        } else {
            throw new IllegalArgumentException("В args можно передать только полное название класса");
        }
    }

    private static void printSteps(List<TestStep> steps, Class<?> clazz) {
        System.out.printf("План прогона для %s:\n", clazz.getSimpleName());

        if (steps.isEmpty()) {
            System.out.println("  Кейсов нет");
        } else {
            int num = 1;
            for (TestStep step : steps) {
                System.out.printf("  %d. %s → %s\n", num++, step.id(), step.methodName());
            }
        }
    }

    public static List<TestStep> buildPlan(Class<?> clazz) {
        List<TestStep> steps = new ArrayList<>();
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            TestId testId = method.getAnnotation(TestId.class);

            if (testId != null) {
                steps.add(new TestStep(testId.value(), method.getName(), testId.order()));
            }
        }

        steps.sort(Comparator.comparingInt(TestStep::order));
        return steps;
    }
}