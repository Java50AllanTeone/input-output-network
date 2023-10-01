package telran.view;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.function.Function;
import java.util.function.Predicate;

public interface InputOutput {
    String readString(String prompt);
    void writeString(String string);

    default String readString(String prompt, String errorPrompt, Predicate<String> pattern) {
         return readObject(prompt, errorPrompt, e -> {
             if (!pattern.test(e)) {
                 throw new IllegalArgumentException("doesn't match a pattern");
             }
             return e;
         });
    }

    default String readString(String prompt, String errorPrompt, HashSet<String> options) {
        return readString(prompt, errorPrompt, options::contains);
    }



    default <T> T readObject(String prompt, String errorPrompt, Function<String, T> mapper) {
        T res = null;

        while (true) {
            String string = readString(prompt);

            try {
                res = mapper.apply(string);
                return res;
            } catch (RuntimeException e) {
                writeLine(errorPrompt + ": " + e.getMessage());
            }
        }
    }

    default  Integer readInt(String prompt, String errorPrompt) {
        return readObject(prompt, errorPrompt, Integer::parseInt);
    }

    default Integer readInt(String prompt, String errorPrompt, int min, int max) {
        return readData(prompt, errorPrompt, Integer::parseInt, Integer::compare, min, max);
    }

    default Long readLong(String prompt, String errorPrompt) {
        return readObject(prompt, errorPrompt, Long::parseLong);
    }

    default Long readLong(String prompt, String errorPrompt, long min, long max) {
        return readData(prompt, errorPrompt, Long::parseLong, Long::compare, min, max);
    }

    default Double readDouble(String prompt, String errorPrompt) {
        return readObject(prompt, errorPrompt, Double::parseDouble);
    }

    default Double readDouble(String prompt, String errorPrompt, double min, double max) {
        return readData(prompt, errorPrompt, Double::parseDouble, Double::compare, min, max);
    }

    default LocalDate readIsoDate(String prompt, String errorPrompt) {
        return readObject(prompt, errorPrompt, LocalDate::parse);
    }

    default LocalDate readIsoDate(String prompt, String errorPrompt, LocalDate min, LocalDate max) {
        return readData(prompt, errorPrompt, LocalDate::parse, LocalDate::compareTo, min, max);
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


    private <T> T readData(String msg, String err, Function<String, T> parser, Comparator<T> comp,
                           T min, T max) {
        return readObject(msg, err, e -> {
            T obj = parser.apply(e);

            if (comp.compare(obj, min) < 0 || comp.compare(obj, max) > 0)
                throw new IllegalArgumentException("Must be in range: " + min + "-" + max);
            return obj;
        });
    }
}
