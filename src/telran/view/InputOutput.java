package telran.view;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public interface InputOutput {
    String readString(String prompt);
    void writeString(String string);

    default String readString(String prompt, String errorPrompt, Predicate<String> pattern) {
        String str;
        boolean running = false;

        do {
            str = readString(prompt);
            if (running = !pattern.test(str)) {
                writeLine(errorPrompt);
            }
        } while (running);

        return str;
    }

    default String readString(String prompt, String errorPrompt, HashSet<String> options) {
        return null;
    }

    default <T> T readObject(String prompt, String errorPrompt, Function<String, T> mapper) {
        boolean running = false;
        T res = null;

        do {
            running = false;
            String string = readString(prompt);

            try {
                res = mapper.apply(string);
                return res;
            } catch (RuntimeException e) {
                writeLine(errorPrompt + ": " + e.getMessage());
                running = true;
            }
        } while (running);
        return res;
    }

    default  Integer readInt(String prompt, String errorPrompt) {
        return readObject(prompt, errorPrompt, Integer::parseInt);
    }

    default Integer readInt(String prompt, String errorPrompt, int min, int max) {
        return -1;
    }

    default Long readLong(String prompt, String errorPrompt) {
        return readObject(prompt, errorPrompt, Long::parseLong);
    }

    default Long readLong(String prompt, String errorPrompt, long min, long max) {
        return null;
    }

    default Double readDouble(String prompt, String errorPrompt) {
        return readObject(prompt, errorPrompt, Double::parseDouble);
    }

    default Double readDouble(String prompt, String errorPrompt, double min, double max) {
        return null;
    }

    default LocalDate readIsoDate(String prompt, String errorPrompt) {
        return readObject(prompt, errorPrompt, LocalDate::parse);
    }

    default LocalDate readIsoDate(String prompt, String errorPrompt, LocalDate min, LocalDate max) {
        return null;
    }


    default void writeLine(String string) {
        writeString(string + "\n");
    }

    default void writeObject(Object object) {
        writeString(object.toString());
    }

    default void writeObjectLine(Object object) {
        writeLine(object + "\n");
    }




}
