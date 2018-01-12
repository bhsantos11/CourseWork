import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.Socket;

public class WrkrRunnable implements Runnable{
    protected Socket clntSock = null;
    protected String txtFrmSrvr   = null;
    protected int BUFSIZE = 10000;
    protected byte[] byteBuffer = new byte[BUFSIZE];

    public WrkrRunnable(Socket clntSocket, String txtFrmSrvr) {
        this.clntSock = clntSocket;
        this.txtFrmSrvr   = txtFrmSrvr;
    }
    public void run() {
        try {
            Session userSession = new Session();
            int recvMsgSize;

            System.out.println("Handling client at " +
                    clntSock.getInetAddress().getHostAddress() + " on port " +
                    clntSock.getPort());

            InputStream in = clntSock.getInputStream();
            OutputStream out = clntSock.getOutputStream();
            byte[] HTTPMessage = new byte[BUFSIZE];


            //Here print out their initial message and return the login page
            while((recvMsgSize = in.read(byteBuffer)) != -1){//stays here until further info is read
                System.out.println(new String(byteBuffer));
                HTTPMessage = HTTPProcessor.HTTPMessageGen(byteBuffer, BUFSIZE, userSession);
                out.write(HTTPMessage, 0, HTTPMessage.length);
            }

            clntSock.close();
            System.out.println("Socket Closed");
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            System.out.println("Non IOException");
            e.printStackTrace();
        }
    }
}