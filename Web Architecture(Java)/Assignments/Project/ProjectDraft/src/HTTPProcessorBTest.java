import java.io.*;
import java.net.*;
/*
* This is used to generate the HTTP message to be returned by the Browser*
* Should be client
*/

public class HTTPProcessorBTest {
//    public static boolean clientDone = false;

    public static byte[] HTTPProcessor(byte[] byteBuffer, int BUFFSIZE) {

        String HTTPAnswer = new String(byteBuffer);
        byte[] answer = new byte[BUFFSIZE];
        String stringAnswer = new String();
        //HTTP header Response being written
        stringAnswer += "HTTP/1.1 200 OK \n";
        stringAnswer += "Content-Type: text/html; charset=UTF-8 \n";
        stringAnswer += "Server: BernieBot \n";

        stringAnswer += "\r\n ";//CRLF may not be implemented properly
        String nextPage = nextPage(HTTPAnswer);


        String URL;
        System.out.println("Next Page '" + nextPage+"'");
//The approach below is done instead of dynamically generating account manager and is not very effective
//        if (nextPage.startsWith("https://foller.me")) {
//            try {
//                System.out.println("Tweet Tweet");
//                URL = nextPage;
//                URL accountPage = new URL(nextPage);
//                InputStreamReader sr = new InputStreamReader(accountPage.openStream());
//                BufferedReader br = new BufferedReader(sr);
//                br = new BufferedReader(sr);
//                String currentLine;
//
//                while ((currentLine = br.readLine()) != null) {
//                    stringAnswer += currentLine + "\n";
//                }
//            }
//             catch (IOException e){
//
//                }
//
//        }// end of it
//
//
//
//
//        else {

        if (nextPage.startsWith("https://foller.me")) {
            System.out.println("Generating and returning page");
            System.out.println();
            return TwitterHandler.pageGenerator(byteBuffer);
        }
        else{
            try { // Reading file from HTML and adding to stringAnswer

                //I/O initialization

                String folderSrc = "src\\site\\";
                URL = folderSrc + nextPage;
                FileReader fr = new FileReader(URL);
                boolean twitter = false;
                BufferedReader br = new BufferedReader(fr);

                String currentLine;

                //Reads file and adds to stringAnswer until eof
                while ((currentLine = br.readLine()) != null) {
                    stringAnswer += currentLine + "\n";
                }

            } catch (IOException e) {
                System.out.println(System.getProperty("user.dir")); // In case a FileNotFound exception occurs
                e.printStackTrace();
            }



        }
        answer = stringAnswer.getBytes();
        return answer;
    }

    public static void consoleLog(byte[] byteBuffer) {


    }

    public static String nextPage(String HTTPAnswer) {
        String nextPage = "";
        String[] HTTPAnswerArray = HTTPAnswer.split("\n");
        String method = HTTPAnswerArray[0];
//        if(method.length() > 30){
//            String URL = method.split(" ")[1];
//            System.out.println(URL);
//        } To be used if AccountManager form uses get method(using post as of now)
        if(method.startsWith("GET")){
            nextPage = "Login.html";
            System.out.println("GET METHOD DETECTED");
        }
        else if (method.startsWith("POST")){ // Logged in so last line should be logging in info
            System.out.println("POST METHOD DETECTED");
            String[] lastLine = HTTPAnswerArray[(HTTPAnswerArray.length) -1 ].split("&");
            if(lastLine[0].startsWith("username")) { //Here the user is logging in

                //Creating username and password strings
                String username = lastLine[0].substring(9, lastLine[0].length());
                String password = lastLine[1].substring(9, lastLine[1].length() - 1);

                //Authentication will happen here
                System.out.println("USERNAME: " + username);
                System.out.println("PASSWORD: " + password);
                nextPage = "AccountManager.html";
            }
            else if(lastLine[0].startsWith("accountName")){ // User has entered account
                String account = lastLine[0].substring(10);
                nextPage = TwitterHandler.AccountHandler(account);
            }
        }



        if (nextPage == "") nextPage = "Login.html";
        return nextPage;
    }
}