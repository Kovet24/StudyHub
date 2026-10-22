package online.itklabs.academy.task3.extraTask;

import java.util.LinkedHashMap;
import java.util.Map;

public class FrequencyAnalysis {
    public static void main(String[] args) {
        String[] words = {"up", "down", "up", "up", "left"};
        Integer[] numbers = {3, 1, 3, 3, 7, 1};

        System.out.println(frequency(words));
        System.out.println(frequency(numbers));
    }

    public static <T> Map<T, Long> frequency(T[] items) {
        Map<T, Long> result = new LinkedHashMap<>();

        for (T item : items) {
            result.merge(item, 1L, Long::sum);
        }
        return result;
    }
}
