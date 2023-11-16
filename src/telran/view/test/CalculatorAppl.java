package telran.view.test;

import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;
import telran.view.SystemInputOutput;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CalculatorAppl {
    public static void main(String[] args) {
        Menu menu = new Menu("Calculator", new Menu("CalcArith", getItemsArith()), new Menu("CalcDate", getItemsDate()), Item.exit());
        menu.perform(new SystemInputOutput());
    }

    private static Item[] getItemsArith() {
        return new Item[] {
                Item.of("Add", CalculatorAppl::addItem),
                Item.of("Subtract", CalculatorAppl::subtractItem),
                Item.of("Multiply", CalculatorAppl::multiplyItem),
                Item.of("Divide", CalculatorAppl::divideItem),
                Item.exit()
        };
    }

    private static void addItem(InputOutput io) {
        double[] operands = getOperands(io);
        io.writeObjectLine(operands[0] + operands[1]);
    }

    private static void subtractItem(InputOutput io) {
        double[] operands = getOperands(io);
        io.writeObjectLine(operands[0] - operands[1]);
    }

    private static void multiplyItem(InputOutput io) {
        double[] operands = getOperands(io);
        io.writeObjectLine(operands[0] * operands[1]);
    }

    private static void divideItem(InputOutput io) {
        double[] operands = getOperands(io);
        io.writeObjectLine(operands[0] / operands[1]);
    }

    private static double[] getOperands(InputOutput io) {
        return new double[] {
                io.readDouble("Enter first number", "Wrong number"),
                io.readDouble("Enter second number", "Wrong number")
        };
    }


    private static Item[] getItemsDate() {
        return new Item[] {
                Item.of("Plus days", CalculatorAppl::addDate),
                Item.of("Minus days", CalculatorAppl::subtractDate),
                Item.of("Between", CalculatorAppl::betweenDate),
                Item.exit()
        };
    }

    private static void betweenDate(InputOutput io) {
        LocalDate date1 = io.readIsoDate("Enter Iso Date", "Wrong date");
        LocalDate date2 = io.readIsoDate("Enter Iso Date", "Wrong date");
        io.writeObjectLine(ChronoUnit.DAYS.between(date1, date2));
    }

    private static void subtractDate(InputOutput io) {
        LocalDate date = io.readIsoDate("Enter Iso Date", "Wrong date");
        int days = io.readInt("Enter quantity of days", "Wrong number");
        io.writeObjectLine(date.minusDays(days));
    }

    private static void addDate(InputOutput io) {
        LocalDate date = io.readIsoDate("Enter Iso Date", "Wrong date");
        int days = io.readInt("Enter quantity of days", "Wrong number");
        io.writeObjectLine(date.plusDays(days));
    }
}
