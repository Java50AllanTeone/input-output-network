package telran.client;

import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;
import telran.view.SystemInputOutput;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

public class CalculatorClientApp {
    private static Map<String, String> arithOps = Map.of("Add", "+",
            "Substract", "-", "Multiply", "*", "Divide", "/");
    private static Map<String, String> dateOps = Map.of("Plus days", "plusDate",
            "Minus days", "minusDate", "Between", "between");

    private static final int PORT = 5000;
    private static final String HOST = "localhost";
    static BufferedReader reader;
    static PrintStream writer;

    public static void main(String[] args) throws IOException {
        InputOutput io = new SystemInputOutput();
        Socket socket = new Socket(HOST, PORT);

        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintStream(socket.getOutputStream());

        Menu menu = new Menu("Calculator",
                new Menu("CalcArith", getItems(arithOps, io)),
                new Menu("CalcDate", getItems(dateOps, io)),
                Item.of("Exit", e -> closeProtocol(socket), true));

        menu.perform(io);
    }


    private static Item[] getItems(Map<String, String> ops, InputOutput io) {
        ArrayList<Item> items = new ArrayList<>();
        ops.entrySet().forEach(e -> items.add(Item.of(e.getKey(), f -> runProtocol(e.getValue(), io))));
        items.add(Item.exit());

        return items.toArray(Item[]::new);
    }

    private static void runProtocol(String operation, InputOutput io) {
        String op1;
        String op2;

        if (operation.equals("plusDate") || operation.equals("minusDate") || operation.equals("between"))
            op1 = io.readIsoDate("Enter first date", "Wrong date").toString();
        else
            op1 = io.readDouble("Enter first number", "Wrong number") + "";

        op2 = operation.equals("between") ? io.readIsoDate("Enter second date", "Wrong date").toString() :
                io.readDouble("Enter second number", "Wrong number") + "";

        writer.printf("%s#%s#%s%n", operation, op1, op2);

        try {
            io.writeLine(reader.readLine());
        } catch (Exception e) {
        }
    }

    static void closeProtocol(Socket socket) {
        try {
            socket.close();
        } catch (IOException e) {
        }
    }
}
