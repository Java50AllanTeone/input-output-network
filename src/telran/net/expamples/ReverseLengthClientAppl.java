package telran.net.expamples;

import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;
import telran.view.SystemInputOutput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ReverseLengthClientAppl {

    private static final  String HOST = "localhost";
    private static final int PORT = 5000;
    static BufferedReader reader;
    static PrintStream writer;

    public static void main(String[] args) throws Exception {
        InputOutput io = new SystemInputOutput();
        Socket socket = new Socket(HOST, PORT);

        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintStream(socket.getOutputStream());

        Menu menu = new Menu("Reverse-Length-Client",
                Item.of("Reverse", io1 -> runProtocol("reverse", io1)),
                Item.of("Length", io1 -> runProtocol("length", io1)),
                Item.of("Exit", io1 -> closeProtocol(socket), true));

        menu.perform(io);
    }

    static void runProtocol(String type, InputOutput io1) {
        String str = io1.readString("Enter any string");
        writer.printf("%s#%s\n", type, str);

        try {
            io1.writeLine(reader.readLine());
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



