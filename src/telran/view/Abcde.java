package telran.view;

import telran.employees.dto.Employee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.time.LocalDate;

public class Abcde {
    public static void main(String[] args) throws IOException {
        InputOutput io = new SystemInputOutput();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//        PrintStream writer = new PrintStream(System.out);
//
////        writer.println(reader.readLine());
//
//        InputOutput io = new SystemInputOutput();

//        io.writeString(io.readString("aaa"));
//        io.readString("aaa");


        Employee empl = io.readObject(
                "Enter employee <id>#<name>#<iso birthdate>#<department>#<salary>",
                "Wrong Employee",
                str -> {
                    String[] tokens = str.split("#");
                    if (tokens.length != 5)
                        throw new RuntimeException("must be 5 tokens");

                    long id = Long.parseLong(tokens[0]);
                    String name = tokens[1];
                    String department = tokens[3];
                    int salary = Integer.parseInt(tokens[4]);
                    LocalDate birthDate = LocalDate.parse(tokens[2]);

                    return new Employee(id, name, department, salary, birthDate);
                });
        io.writeObjectLine(empl);
    }
}
