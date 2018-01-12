import java.net.*;  // for Socket, ServerSocket, and InetAddress
import java.io.*;   // for IOException and Input/OutputStream

public class HTTPServer {

    private static final int BUFSIZE = 10000;   // Size of receive buffer

    public static void main(String[] args) throws IOException {

        if (args.length != 1)  // Test for correct # of args
            throw new IllegalArgumentException("Parameter(s): <Port>");

        int servPort = Integer.parseInt(args[0]);

        // Create a server socket to accept client connection requests
        ServerSocket servSock = new ServerSocket(servPort);

        int recvMsgSize;   // Size of received message
        byte[] byteBuffer = new byte[BUFSIZE];  // Receive buffer
        byte[] HTTPMessage= HTTPProcessor.HTTPProcessor(BUFSIZE);


        String serverAddress = InetAddress.getLocalHost().toString();
        System.out.println("Server started at: " + serverAddress + " port: " + servPort);
        System.out.println("Server socket: " + servSock.toString());
        for (;;) { // Run forever, accepting and servicing connections
            Socket clntSock = servSock.accept();     // Get client connection

            System.out.println("Handling client at " +
                    clntSock.getInetAddress().getHostAddress() + " on port " +
                    clntSock.getPort());

            InputStream in = clntSock.getInputStream();
            OutputStream out = clntSock.getOutputStream();

            int n = 0;
            // Receive until client closes connection, indicated by -1 return
                while ((recvMsgSize = in.read(byteBuffer)) != -1) {
                    System.out.println(new String(byteBuffer));
                    System.out.println(new String(HTTPMessage));
                    out.write(HTTPMessage, 0, HTTPMessage.length);
                }

            clntSock.close();  // Close the socket.  We are done with this client!
        }
    /* NOT REACHED */
    }
}
/*TO-DOs
HTTP (Bernardo)
    HEAD
    POST
        To be implemented using the form
Authentication/Authorization(Bernardo)
Logging(Devlin)
Threading(Tinelli)
Cache Request(Devlin)
UI(Tinelli)


* */