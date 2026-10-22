package online.itklabs.academy.task2.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Если RetentionPolicy будет CLASS или SOURCE, то мы не сможем через рефлексию понять, какие методы обрабатывать,
// из-за того, что эта аннотация не дойдет до выполнения кода, но сама программа будет работать
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestId {
    String value();

    int order();
}