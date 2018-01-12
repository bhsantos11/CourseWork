import java.io.*;
import java.util.Scanner;

public class AccountManager
{

    //adds a new twitter handle to a users list of accounts
    public static void addAccount(String username, String twitterHandle, String DB) throws IOException {
        try {
            File inputFile = new File(DB);

            Scanner inputScan = new Scanner(inputFile);


            String temp = "";
            String userdata;
            String[] userarray;
            while (inputScan.hasNext()) {
                userdata = inputScan.nextLine();
                temp += userdata;
                userarray = userdata.split("&");
                if (username.compareTo(userarray[0]) == 0) {
                    temp += "," + twitterHandle;
                }
                temp += "\r\n";
            }
            inputScan.close();
            PrintWriter writer = new PrintWriter(inputFile);
            writer.print(temp);
            writer.close();
        }
        catch (FileNotFoundException e){
            System.out.println(e.toString());
            System.out.println(System.getProperty("user.dir")); // In case a FileNotFound exception occurs
        }
    }

    //Returns the watched accounts of a given user
    public static String[] userAccounts(String username, String DB) throws IOException
    {
        File inputFile= new File(DB);
        Scanner inputScan= new Scanner(inputFile);
        String userdata;
        String[] userarray;
        String[] accountarray;
        while (inputScan.hasNext())
        {
            userdata=inputScan.nextLine();
            userarray=userdata.split("&");
            if (username.compareTo(userarray[0])==0)
            {
                accountarray=userarray[2].split(",");
                inputScan.close();
                return accountarray;
            }
        }
        String[] notfound={"user not found"};
        inputScan.close();
        return notfound;
    }

    // Tests
    public static void main(String[] args){

        //addAccount Cases
        try {
            addAccount("dev", "yoyo", "src\\DB.txt");
        }
        catch (IOException e){
            e.printStackTrace();
        }

        //userInfo cases
        try {
            String[] myAccounts = userAccounts("dev", "src\\DB.txt");
            for(String account : myAccounts){
                System.out.println(account);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
  