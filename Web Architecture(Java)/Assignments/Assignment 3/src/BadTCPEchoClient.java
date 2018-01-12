import java.net.*;  // for Socket
import java.io.*;   // for IOException and Input/OutputStream

// Poor services start at line 17 and 50
// It is important to note that we can't use both poor services at once, therefore they must be tested separately
public class BadTCPEchoClient {

    public static void main(String[] args) throws IOException {
        // Im a bad client
        System.out.println("I'm a bad client");

        if ((args.length < 2) || (args.length > 3))  // Test for correct # of args
            throw new IllegalArgumentException("Parameter(s): <Server> <Word> [<Port>]");

        String server = args[0];       // Server name or IP address
        // Convert input String to bytes using the default character encoding

        // Poor service 1
        // This will make the buffer size too big and other clients will not be able to connect to the server
//        String temp = args[1];
//        for(int x = 0; x < 20; x++) temp += temp;
//        byte[] byteBuffer = temp.getBytes();

        byte[] byteBuffer = args[1].getBytes();

        int servPort = (args.length == 3) ? Integer.parseInt(args[2]) : 7;

        // Create socket that is connected to server on specified port
        Socket socket = new Socket(server, servPort);
        System.out.println("Connected to server...sending echo string");

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();


        out.write(byteBuffer);  // Send the encoded string to the server


        // Receive the same string back from the server
        int totalBytesRcvd = 0;  // Total bytes received so far
        int bytesRcvd;           // Bytes received in last read
        while (totalBytesRcvd < byteBuffer.length) {
            if ((bytesRcvd = in.read(byteBuffer, totalBytesRcvd,
                    byteBuffer.length - totalBytesRcvd)) == -1)
                throw new SocketException("Connection close prematurely");
            totalBytesRcvd += bytesRcvd;
        }

        System.out.println("Received: " + new String(byteBuffer));

        // Poor service 2
        // Since the server never closes the connection, if the client never closes the server will keep that connection open and so no new clients will be able to connect
//        socket.close();  // Close the socket and its streams
    }
}