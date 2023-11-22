package telran.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

public class CalculatorServerApp {
    static final int PORT = 5000;
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(PORT);

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("connected");

            runProtocol(socket);
        }
    }

    private static void runProtocol(Socket socket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintStream writer = new PrintStream(socket.getOutputStream())) {

            while (true) {
                String request = reader.readLine();

                if (request == null) {
                    System.out.println("connection closed");
                    socket.close();
                    return;
                } else {
                    String response = getResponse(request);
                    writer.println(response);
                }
            }
        } catch (Exception e) {
        }
    }

    private static String getResponse(String request) {
        String response = null;
        String[] tokens = request.split("#");

        if (tokens.length != 3) {
            response = "response must be in format <operation>#<operand>#<operand>";
        } else {
            response = getResponse(tokens);
        }

        return response;
    }

    private static String getResponse(String[] tokens) {
        return switch (tokens[0]) {
            case "+" -> Double.parseDouble(tokens[1]) + Double.parseDouble(tokens[2]) + "";
            case "-" -> Double.parseDouble(tokens[1]) - Double.parseDouble(tokens[2]) + "";
            case "*" -> Double.parseDouble(tokens[1]) * Double.parseDouble(tokens[2]) + "";
            case "/" -> Double.parseDouble(tokens[1]) / Double.parseDouble(tokens[2]) + "";
            case "between" -> ChronoUnit.DAYS.between(LocalDate.parse(tokens[2]), LocalDate.parse(tokens[1])) + "";
            case "plusDate" -> LocalDate.parse(tokens[1]).plusDays(Integer.parseInt(tokens[2])) + "";
            case "minusDate" -> LocalDate.parse(tokens[1]).minusDays(Integer.parseInt(tokens[2])) + "";
            default -> "wrong type";
        };
    }
}
