import java.io.*;
import java.util.Scanner;

public class Session {

    private String user;

    public Session(){
        user = null;
    }
    public Session(String username){
        user = username;
    }

    public void setUsername(String username){
        user = username;
    }

    public String getUsername(){
        return user;
    }

    public static boolean auth(String user, String pass, String DB)
    {
        try {
            File inputFile = new File(DB);
            Scanner inputScan = new Scanner(inputFile);
            String userdata;
            String[] userarray;
            while (inputScan.hasNext()) {
                userdata = inputScan.nextLine();
                userarray = userdata.split("&");
                if (user.compareTo(userarray[0]) == 0) {
                    if (pass.compareTo(userarray[1]) == 0) {
                        inputScan.close();
                        return true;
                    }
                }
            }
            inputScan.close();
        }
        catch (IOException e){}
        return false;
    }


}

// Here what might cause an issue is the static nature of the object
// I'm still unsure on whether one session = new Session(username) will overwrite all
// This might not be an issue due to the fact that sessions are located in separate threads and might
