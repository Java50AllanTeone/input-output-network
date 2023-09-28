package telran.view.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import telran.employees.dto.Employee;
import telran.view.InputOutput;
import telran.view.SystemInputOutput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class InputOutputTest {
    InputOutput io = new SystemInputOutput();

    @BeforeEach
    void setUp() {

    }

    @Test
    void testReadEmployeeString() {
        Employee empl = io.readObject(
                "Enter employee <id>#<name>#<iso birthdate>#<department>#<salary>",
                "Wrong Employee",
                str -> {
                    String[] tokens = str.split("#");
                    if (tokens.length != 5)
                        throw new RuntimeException("must be 5 tokens");

                    long id = Long.parseLong(tokens[0]);
                    String name = tokens[1];
                    LocalDate birthDate = LocalDate.parse(tokens[2]);
                    String department = tokens[3];
                    int salary = Integer.parseInt(tokens[4]);

                    return new Employee(id, name, department, salary, birthDate);
                });
        io.writeObjectLine(empl);
    }


    @Test
    void readStringPattern() {
        assertEquals(5, io.readString("Enter string",
                "string length must be 5",
                e -> e.length() == 5).length());
    }

    @Test
    void readStringOptions() {
        HashSet<String> set = new HashSet<>(Arrays.asList(new String[]{"123"}));
        assertEquals("123", io.readString("Enter string",
                "string must be contained in options",
                set));
    }

    @Test
    void readObject() {
        assertTrue(io.readObject("Enter string",
                "cannot parse to int",
                Integer::parseInt) instanceof Integer);
    }

    @Test
    void readInt() {
        assertTrue(io.readInt("Enter string",
                "cannot parse to int") instanceof Integer);
    }

    @Test
    void readIntRange() {
        var res = io.readInt("Enter string",
                "cannot parse to int", 1, 5);

        assertTrue(res instanceof Integer);
        assertTrue(res >= 1 && res <= 5);
    }

    @Test
    void readLong() {
        assertTrue(io.readLong("Enter string",
                "cannot parse to long") instanceof Long);
    }

    @Test
    void readLongRange() {
        var res = io.readLong("Enter string",
                "cannot parse to long", 1, 5);

        assertTrue(res instanceof Long);
        assertTrue(res >= 1 && res <= 5);
    }

    @Test
    void readDouble() {
        assertTrue(io.readDouble("Enter string",
                "cannot parse to double") instanceof Double);
    }

    @Test
    void readDoubleRange() {
        var res = io.readDouble("Enter string",
                "cannot parse to double", 1, 5);

        assertTrue(res instanceof Double);
        assertTrue(res >= 1 && res <= 5);
    }


    @Test
    void readIsoDate() {
        assertTrue(io.readIsoDate("Enter string",
                "cannot parse to date") instanceof LocalDate);
    }


    @Test
    void readIsoDateRange() {
        LocalDate from = LocalDate.parse("2005-09-23");
        LocalDate to = LocalDate.parse("2010-11-03");
        var res = io.readIsoDate("Enter string",
                "cannot parse to date", from, to);

        assertTrue(res instanceof LocalDate);
        assertTrue(!res.isBefore(from) && !res.isAfter(to));
    }
}