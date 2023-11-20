package telran.net.expamples;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.*;

public class ReverseLengthServerAppl {
    static final int PORT = 5000;

    public static void main(String[] args) throws Exception {
        System.out.println("Server is listening on port " + PORT);
        ServerSocket serverSocket = new ServerSocket(PORT);

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Client connected from port " + socket.getPort());

            runProtocol(socket);
        }

    }

    private static void runProtocol(Socket socket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintStream writer = new PrintStream(socket.getOutputStream())) {
            boolean running = true;

            while (running) {
                String request = reader.readLine();

                if (request == null) {
                    System.out.println("Client closed connection");
                    running = false;
                } else {
                    String response = getResponse(request);
                    writer.println(response);
                }
            }
        } catch (Exception e) {
            System.out.println("Abnormal socket closing");
        }
    }

    private static String getResponse(String request) {
        String response = null;
        String[] tokens = request.split("#");

        if (tokens.length != 2) {
            response = "request must be in format <type>#<string>";
        } else {
            response = switch (tokens[0]) {
                case "reverse" -> new StringBuilder(tokens[1]).reverse().toString();
                case "length" -> tokens[1].length() + "";
                default -> "Wrong type";
            };
        }
        return response;
    }
}
