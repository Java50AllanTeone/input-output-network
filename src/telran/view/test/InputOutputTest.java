package telran.view.test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import telran.employees.dto.Employee;
import telran.view.InputOutput;
import telran.view.SystemInputOutput;

import java.io.*;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class InputOutputTest {
    InputStream sysIn = System.in;
    PipedOutputStream out;
    InputOutput io;

    @BeforeEach
    void setUp() {
        try {
            out = new PipedOutputStream();
            System.setIn(new PipedInputStream(out));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    void initPipe(String... lines) {
        try {
            for (String s : lines)
                out.write((s + "\r\n").getBytes());

            out.close();
            io = new SystemInputOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testReadEmployeeString() throws IOException {
        String expected = "Employee[id=123, name=name, department=department, salary=10000, birthDate=2000-01-01]";
        String input = "123#name#2000-01-01#department#10000";
        initPipe(input);

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
        assertEquals(expected, empl.toString());
        io.writeObjectLine(empl);
    }


    @Test
    void readStringPattern() {
        int expected = 5;
        String input = "12345";
        initPipe(input);

        assertEquals(expected, io.readString("Enter string",
                "string length must be 5",
                e -> e.length() == 5).length());
    }

    @Test
    void readStringOptions() {
        String input1 = "345";
        String input2 = "123";
        initPipe(input1, input2);

        HashSet<String> set = new HashSet<>(Arrays.asList(new String[]{"123"}));
        assertEquals("123", io.readString("Enter string",
                "string must be contained in options",
                set));
    }

    @Test
    void readObject() {
        String input1 = "5.0";
        String input2 = "5";
        int expected = 5;
        initPipe(input1, input2);
        int res = io.readObject("Enter string", "cannot parse to int",
                Integer::parseInt);

        assertEquals(expected, res);
    }

    @Test
    void readInt() {
        String input1 = "5.0";
        String input2 = Long.MAX_VALUE + "";
        String input3 = "5";
        int expected = 5;

        initPipe(input1, input2, input3);
        int res = io.readInt("Enter string", "cannot parse to int");

        assertEquals(expected, res);
    }

    @Test
    void readIntRange() {
        String input1 = "15.0";
        String input2 = Long.MAX_VALUE + "";
        String input3 = "15";
        String input4 = "5";
        int expected = 5;
        initPipe(input1, input2, input3, input4);
        int res = io.readInt("Enter string", "cannot parse to int", 1, 5);

        assertEquals(expected, res);
    }

    @Test
    void readLong() {
        String input1 = "5.0";
        String input2 = new BigInteger(Long.MAX_VALUE + "")
                .add(new BigInteger(Long.MAX_VALUE + "")).toString();
        String input3 = "5";
        long expected = 5;
        initPipe(input1, input2,input3);
        long res = io.readLong("Enter string", "cannot parse to long");

        assertEquals(expected, res);
    }

    @Test
    void readLongRange() {
        String input1 = "5.0";
        String input2 = new BigInteger(Long.MAX_VALUE + "")
                .add(new BigInteger(Long.MAX_VALUE + "")).toString();
        String input3 = "15";
        String input4 = "5";
        long expected = 5;
        initPipe(input1, input2,input3, input4);
        long res = io.readLong("Enter string", "cannot parse to long", 1, 5);

        assertEquals(expected, res);
    }

    @Test
    void readDouble() {
        String input = "5.0";
        double expected = 5.0;
        initPipe(input);
        double res = io.readDouble("Enter string", "cannot parse to double");

        assertEquals(expected, res);

    }

    @Test
    void readDoubleRange() {
        String input1 = "15.0";
        String input2 = "5.0";
        double expected = 5.0;
        initPipe(input1, input2);
        double res = io.readDouble("Enter string", "cannot parse to double", 1, 5);

        assertEquals(expected, res);
    }


    @Test
    void readIsoDate() {
        String input1 = "01-01-2000";
        String input2 = "2000-30-01";
        String input3 = "01/01/2000";
        String input4 = "2000-01-01";
        LocalDate expected = LocalDate.parse("2000-01-01");
        initPipe(input1, input2, input3, input4);
        LocalDate res = io.readIsoDate("Enter string", "cannot parse to date");

        assertEquals(expected, res);
    }


    @Test
    void readIsoDateRange() {
        String input1 = "01-01-2000";
        String input2 = "2000-30-01";
        String input3 = "01/01/2000";
        String input4 = "2015-01-01";
        String input5 = "2000-01-01";
        LocalDate expected = LocalDate.parse("2000-01-01");
        initPipe(input1, input2, input3, input4, input5);
        LocalDate from = LocalDate.parse("1999-01-01");
        LocalDate to = LocalDate.parse("2005-01-01");
        LocalDate res = io.readIsoDate("Enter string", "cannot parse to date", from, to);

        assertEquals(expected, res);
    }
}