package telran.server;

import telran.CalculatorProtocol;
import telran.TcpServer;

public class CalculatorServerApp {
    static final int PORT = 5000;

    public static void main(String[] args) throws Exception {
        TcpServer server = new TcpServer(PORT, new CalculatorProtocol());
        server.run();
    }
}
