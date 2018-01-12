import java.net.*;  // for Socket, ServerSocket, and InetAddress
import java.io.*;   // for IOException and Input/OutputStream

public class HTTPServerBTest {

    private static final int BUFSIZE = 10000;   // Size of receive buffer

    public static void main(String[] args) throws IOException {

        if (args.length != 1)  // Test for correct # of args
            throw new IllegalArgumentException("Parameter(s): <Port>");

        int servPort = Integer.parseInt(args[0]);

        // Create a server socket to accept client connection requests
        ServerSocket servSock = new ServerSocket(servPort);

        int recvMsgSize;   // Size of received message
        byte[] byteBuffer = new byte[BUFSIZE];  // Receive buffer



        //Server Startup messages
        String serverAddress = InetAddress.getLocalHost().toString();
        System.out.println("Server started at: " + serverAddress + " port: " + servPort);
        System.out.println("Server socket: " + servSock.toString());



        for (;;) { // Run forever, accepting and servicing connections
            System.out.println("In loop before accept");
            Socket clntSock = servSock.accept();     // Get client connection
            System.out.println("Client accepted");

            System.out.println("Handling client at " +
                    clntSock.getInetAddress().getHostAddress() + " on port " +
                    clntSock.getPort());

            InputStream in = clntSock.getInputStream();
            OutputStream out = clntSock.getOutputStream();
            byte[] HTTPMessage = new byte[BUFSIZE];


            //Here print out their initial message and return the login page
            while((recvMsgSize = in.read(byteBuffer)) != -1){//stays here until further info is read
                System.out.println(new String(byteBuffer));
                HTTPMessage = HTTPProcessorBTest.HTTPProcessor(byteBuffer, BUFSIZE);
                out.write(HTTPMessage, 0, HTTPMessage.length);
            }






            clntSock.close();  // Close the socket.  We are done with this client!
            System.out.println("Socket Closed");
        }
    /* NOT REACHED */
    }
    public class SocketThread implements Runnable  {
        Socket servSock;
        int recvMsgSize;

        public SocketThread(Socket servSock, )

        public void run() {
            try {
                for (; ; ) {
                    System.out.println("In loop before accept");
                    Socket clntSock = servSock.accept();     // Get client connection
                    System.out.println("Client accepted");

                    System.out.println("Handling client at " +
                            clntSock.getInetAddress().getHostAddress() + " on port " +
                            clntSock.getPort());

                    InputStream in = clntSock.getInputStream();
                    OutputStream out = clntSock.getOutputStream();
                    byte[] HTTPMessage = new byte[BUFSIZE];


                    //Here print out their initial message and return the login page
                    while ((recvMsgSize = in.read(byteBuffer)) != -1) {//stays here until further info is read
                        System.out.println(new String(byteBuffer));
                        HTTPMessage = HTTPProcessor.HTTPProcessor(byteBuffer, BUFSIZE);
                        out.write(HTTPMessage, 0, HTTPMessage.length);
                    }


                    clntSock.close();  // Close the socket.  We are done with this client!
                    System.out.println("Socket Closed");
                }// end of for loop


            } catch (IOException e) {

            }
        }// end of run method
    }//end of SocketThread
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