package telran.view;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
         return readObject(prompt, errorPrompt, e -> {
             if (!pattern.test(e)) {
                 throw new IllegalArgumentException(errorPrompt);
             }
             return e.toString();
         });
    }

    default String readString(String prompt, String errorPrompt, HashSet<String> options) {
        return readString(prompt, errorPrompt, options::contains);
    }



    default <T> T readObject(String prompt, String errorPrompt, Function<String, T> mapper) {
//        boolean running = false;
//        T res = null;
//
//        do {
//            running = false;
//            String string = readString(prompt);
//
//            try {
//                res = mapper.apply(string);
//                return res;
//            } catch (RuntimeException e) {
//                writeLine(errorPrompt + ": " + e.getMessage());
//                running = true;
//            }
//        } while (running);
//        return res;

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
        //TODO
        return readObject(prompt, errorPrompt, e -> {
            int num = Integer.parseInt(e);

            if (num < min || num > max) {
                throw new IllegalArgumentException(errorPrompt);
            }
            return num;
        });
    }

    default Long readLong(String prompt, String errorPrompt) {
        return readObject(prompt, errorPrompt, Long::parseLong);
    }

    default Long readLong(String prompt, String errorPrompt, long min, long max) {
        //TODO
        return readObject(prompt, errorPrompt, e -> {
            long num = Long.parseLong(e);

            if (num < min || num > max) {
                throw new IllegalArgumentException(errorPrompt);
            }
            return num;
        });
    }

    default Double readDouble(String prompt, String errorPrompt) {
        return readObject(prompt, errorPrompt, Double::parseDouble);
    }

    default Double readDouble(String prompt, String errorPrompt, double min, double max) {
        //TODO
        return readObject(prompt, errorPrompt, e -> {
            double num = Double.parseDouble(e);

            if (Double.compare(num, min) < 0 || Double.compare(num, max) > 0) {
                throw new IllegalArgumentException(errorPrompt);
            }
            return num;
        });
    }

    default LocalDate readIsoDate(String prompt, String errorPrompt) {
        return readObject(prompt, errorPrompt, LocalDate::parse);
    }

    default LocalDate readIsoDate(String prompt, String errorPrompt, LocalDate min, LocalDate max) {
        //TODO
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
