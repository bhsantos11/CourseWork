import java.io.*;
/*
* This is used to generate the HTTP message to be returned by the Browser*/

public class HTTPProcessor {

    public static byte[] HTTPProcessor(int BUFFSIZE) {
        byte[] answer = new byte[BUFFSIZE];
        String stringAnswer =  new String();
        //HTTP header Response being written
        stringAnswer += "HTTP/1.1 200 OK \n";
        stringAnswer += "Content-Type: text/html; charset=UTF-8 \n";
        stringAnswer += "Connection: Closed \n";

        stringAnswer += "\r\n ";//CRLF may not be implemented properly

        try { // Reading file from HTML and adding to stringAnswer
            String fileName = "src\\site\\Login.html";

            //I/O initialization
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String currentLine;

            //Reads file and adds to stringAnswer until eof
            while((currentLine = br.readLine()) != null){
                stringAnswer += currentLine + "\n"; // TO-DO Implement StringBuilder as its more efficient
            }

        }catch (IOException e){
            System.out.println(System.getProperty("user.dir")); // In case a FileNotFound exception occurs
            e.printStackTrace();
        }

        answer = stringAnswer.getBytes();
        return answer;
    }
}
