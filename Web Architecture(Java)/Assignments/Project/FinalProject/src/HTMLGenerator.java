import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HTMLGenerator {
    public static String HTMLGen(){
        String ans = "";
        try { // Reading file from HTML and adding to stringAnswer

            //I/O initialization
            String stringAnswer = "";
            String URL = "src\\site\\Login.html";

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


        return ans;
    }

    public static String HTMLGen(String username){ // For account manager
        String stringAnswer = "";
        try {
            //I/O initialization

            String URL = "src\\site\\AccountManager.html";
            FileReader fr = new FileReader(URL);
            boolean twitter = false;
            BufferedReader br = new BufferedReader(fr);

            String currentLine;


            String[] accounts = AccountManager.userAccounts(username, "src\\DB.txt");
//            String[] accounts = {"realDonaldTrump", "bbhauers","Twitter" }; dummy array

            // Include Welcome line with person's name
            while ((currentLine = br.readLine()) != null) {
                stringAnswer += currentLine + "\n";
                if (currentLine.contains("Accounts being watched will go here.")) {
                    for (String account : accounts) {
                        stringAnswer += "<br><button onclick=\"location.href = '" + account + "';\" >"
                                +account+"</button> \n";
                    }
                }


            }//end of while loop for file write
        }
        catch (IOException e){

        }

        return stringAnswer;
    }

    public static void main(String [] args) {
        String test = HTMLGen("ben");
        System.out.println(test);
    }
}
