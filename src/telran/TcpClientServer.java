package telran;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TcpClientServer implements Runnable {
    Socket socket;
    ObjectInputStream reader;
    ObjectOutputStream writer;
    ApplProtocol protocol;

    public TcpClientServer(Socket socket, ApplProtocol protocol) throws Exception {
        this.socket = socket;
        this.protocol = protocol;
        reader = new ObjectInputStream(socket.getInputStream());
        writer = new ObjectOutputStream(socket.getOutputStream());
    }



    @Override
    public void run() {
        try {
           while (true) {
               Request request = (Request) reader.readObject();
               Response response = protocol.getResponse(request);
               writer.writeObject(response);
               writer.reset();
           }
        } catch (EOFException e) {
            System.out.println("Client closed connection");
        } catch (Exception e) {
            System.out.println("Abnormal closing connection");
        }
    }
}
