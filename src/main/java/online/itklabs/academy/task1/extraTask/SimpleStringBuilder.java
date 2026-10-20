package online.itklabs.academy.task1.extraTask;

import java.util.LinkedList;

public class SimpleStringBuilder {
    private final StringBuilder current = new StringBuilder();
    private final LinkedList<Integer> historyLength = new LinkedList<>();

    public SimpleStringBuilder append(String s) {
        historyLength.push(current.length());
        current.append(s);
        return this;
    }

    public SimpleStringBuilder undo() {
        if (!historyLength.isEmpty()) {
            current.setLength(historyLength.pop());
        }
        return this;
    }

    @Override
    public String toString() {
        return current.toString();
    }

    public static void main(String[] args) {
        SimpleStringBuilder b = new SimpleStringBuilder();
        System.out.println(b.append("Hello"));     // Hello
        System.out.println(b.append(", "));        // Hello,
        System.out.println(b.append("world"));     // Hello, world
        System.out.println(b.undo());              // Hello,
        System.out.println(b.undo());              // Hello
        System.out.println(b.undo());              //
        System.out.println(b.undo());              //
        System.out.println(b.append("AQA!"));      // AQA!
    }
}