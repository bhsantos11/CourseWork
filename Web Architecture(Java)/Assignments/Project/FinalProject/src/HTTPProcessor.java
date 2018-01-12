/*
* This is used to generate the HTTP message to be returned by the Browser*
* Should be client
*/
public class HTTPProcessor {


    public static byte[] HTTPMessageGen(byte[] byteBuffer, int BUFFSIZE, Session session) throws Exception{  //Returns HTTP Response as byte[]

        //Here we need to create a session that be later be referenced


        String HTTPAnswer = new String(byteBuffer);
        byte[] answer = new byte[BUFFSIZE];
        String stringAnswer = new String();
        String nextPage = nextPage(HTTPAnswer, session);

        //HTTP header Response being written
        stringAnswer += "HTTP/1.1 200 OK \n";
        stringAnswer += "Content-Type: text/html; charset=UTF-8 \n";
        stringAnswer += "Server: AccountManager \n";
        stringAnswer += "Page: " + nextPage + " \n";

        stringAnswer += "\r\n ";//CRLF carrige return



        String URL;
        System.out.println("Next Page " + nextPage);

        //This will check what the nextPage is and compose the appropiate html page
        if(nextPage.equals("AccountManager.html")){
            String username = session.getUsername();
            stringAnswer += HTMLGenerator.HTMLGen(username);
        }
        else stringAnswer += HTMLGenerator.HTMLGen();


        answer = stringAnswer.getBytes();
        return answer;
    }

    private static String nextPage(String HTTPAnswer, Session session)throws Exception {
        String nextPage = "";
        String[] HTTPAnswerArray = HTTPAnswer.split("\n");
        String method = HTTPAnswerArray[0];


        if(method.startsWith("GET")){ // Get is only used for login page
            nextPage = "Login.html";
            System.out.println("GET METHOD DETECTED");
        }
        else if (method.startsWith("POST")){ // Logged in so last line should be logging in info
            System.out.println("POST METHOD DETECTED");
            String[] lastLine = HTTPAnswerArray[(HTTPAnswerArray.length) -1 ].split("&"); // Get last array

            //USER IS LOGGING IN
            if(lastLine[0].startsWith("username")) { //Here the user is logging in
                String username = lastLine[0].substring(9, lastLine[0].length());
                String password = lastLine[1].substring(9, lastLine[1].length() - 1);
                //Authentication will happen here
                if(session.auth(username, password, "DB.txt")){ // Successfully Authenticated
                    nextPage = "AccountManager.html";
                }
                else{
                    nextPage = "Login.html";
                }
            }

            //USER IS ADDING ACCOUNT
            else if(lastLine[0].startsWith("accountName")){ // User has added account, would look like accountName=bbhauers
                String account = lastLine[0].split("=")[1];// This splits the last line and fetches the second item of the array which will be the account handle
                String username = session.getUsername();
                AccountManager.addAccount(username, account, "DB.txt");
                nextPage = "AccountManager.html"; // This will be the page edited
            }
        }

        if (nextPage == "") nextPage = "Login.html";
        return nextPage;
    }
}