import java.net.*;  // for Socket, ServerSocket, and InetAddress
import java.io.*;   // for IOException and Input/OutputStream

public class TCPEchoServer {

    private static final int BUFSIZE = 32;   // Size of receive buffer

    public static void main(String[] args) throws IOException {

        if (args.length != 1)  // Test for correct # of args
            throw new IllegalArgumentException("Parameter(s): <Port>");

        int servPort = Integer.parseInt(args[0]);

        // Create a server socket to accept client connection requests
        ServerSocket servSock = new ServerSocket(servPort);

        int recvMsgSize;   // Size of received message
        byte[] byteBuffer = new byte[BUFSIZE];  // Receive buffer

        for (;;) { // Run forever, accepting and servicing connections
            Socket clntSock = servSock.accept();     // Get client connection

            System.out.println("Handling client at " +
                    clntSock.getInetAddress().getHostAddress() + " on port " +
                    clntSock.getPort());

            InputStream in = clntSock.getInputStream();
            OutputStream out = clntSock.getOutputStream();

            // Bullet proofing
            // Receive until client closes connection, indicated by -1 return
            try { // Bullet proofing 2, this will prevent the connection reset error from "crashing" our server
                while ((recvMsgSize = in.read(byteBuffer)) != -1) {
                    if (recvMsgSize == 32) { // Bullet proofing 2, if the buffer is equal to 32 that means the message exceeded the limit and would cause a problem
                        clntSock.close();  //       this will cause an error in the bad client but that's its own fault for being a bad client
                        break;
                    }
                    System.out.println(new String(byteBuffer));
                    out.write(byteBuffer, 0, recvMsgSize);

                }
            }

            catch (java.net.SocketException e){
                clntSock.close();
            }
            clntSock.close();  // Close the socket.  We are done with this client!
        }
    /* NOT REACHED */
    }
}