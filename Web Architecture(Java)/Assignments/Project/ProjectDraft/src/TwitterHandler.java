import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;

public class TwitterHandler {
    public static String AccountHandler(String AccountName){
        AccountName = AccountName.substring(2);

        String finalURL = "https://foller.me/" + AccountName;
        System.out.println("Final URL: "+finalURL);

        return finalURL;
    }

    public static byte[] pageGenerator(byte[] byteBuff){
        byte[] ans;
        String HTTPAnswer = new String(byteBuff);
        String[] HTTPAnswerArray = HTTPAnswer.split("\n");
        String[] lastLine = HTTPAnswerArray[(HTTPAnswerArray.length) -1 ].split("&");
        String account = lastLine[0].substring(12);

        String finalURL = "https://foller.me/" + account;

        String stringAnswer = new String();
        stringAnswer += "HTTP/1.1 200 OK \n";
        stringAnswer += "Content-Type: text/html; charset=UTF-8 \n";
        stringAnswer += "Server: BernieBot \n";
        stringAnswer += "\r\n ";//CRLF may not be implemented properly

        try {

            System.out.println("Tweet Tweet");
            String URL = "src\\site\\AccountPage.html";

            FileReader fr = new FileReader(URL);
            boolean twitter = false;
            BufferedReader br = new BufferedReader(fr);

            String currentLine;

            //Reads file and adds to stringAnswer until eof
            while ((currentLine = br.readLine()) != null) {

                stringAnswer += currentLine + "\n";
                if(currentLine.equals("Account Stats:")){
                    System.out.println(finalURL);
                    stringAnswer += "<button onclick=\"location.href = '"+finalURL+"';\" >Account Stats</button>";
                }
            }
            }//end of try block
            catch (IOException e){

            }



        ans = stringAnswer.getBytes();
        return ans;
    }
}
