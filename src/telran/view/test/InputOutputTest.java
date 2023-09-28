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
    void readString() throws IOException {


    }

    @Test
    void writeString() {
    }

    @Test
    void testReadString() {
    }

    @Test
    void testReadString1() {
    }

    @Test
    void readObject() {
    }

    @Test
    void readInt() {
    }

    @Test
    void testReadInt() {
    }

    @Test
    void readLong() {
    }

    @Test
    void testReadLong() {
    }

    @Test
    void readDouble() {
    }

    @Test
    void testReadDouble() {
    }

    @Test
    void readIsoDate() {
    }

    @Test
    void testReadIsoDate() {
    }

    @Test
    void writeLine() {
    }

    @Test
    void writeObject() {
    }

    @Test
    void writeObjectLine() {
    }
}