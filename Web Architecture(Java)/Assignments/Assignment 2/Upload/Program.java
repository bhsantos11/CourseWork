import java.util.Scanner;
import java.util.ArrayList;

public class Program {
    public static void main(String[] args)throws Exception{
        // Initializations
        Scanner scan = new Scanner(System.in);
        ArrayList<Student> studentDB = new ArrayList<>();
        // Menu

        System.out.println(" _  _  ____  __     ___  __   _  _  ____    ____  __     ____  ____  _  _  ____  ____  __ _  ____    _  _   __   __ _   __    ___  ____  ____ \n" +
                "/ )( \\(  __)(  )   / __)/  \\ ( \\/ )(  __)  (_  _)/  \\   / ___)(_  _)/ )( \\(    \\(  __)(  ( \\(_  _)  ( \\/ ) / _\\ (  ( \\ / _\\  / __)(  __)(  _ \\\n" +
                "\\ /\\ / ) _) / (_/\\( (__(  O )/ \\/ \\ ) _)     )( (  O )  \\___ \\  )(  ) \\/ ( ) D ( ) _) /    /  )(    / \\/ \\/    \\/    //    \\( (_ \\ ) _)  )   /\n" +
                "(_/\\_)(____)\\____/ \\___)\\__/ \\_)(_/(____)   (__) \\__/   (____/ (__) \\____/(____/(____)\\_)__) (__)   \\_)(_/\\_/\\_/\\_)__)\\_/\\_/ \\___/(____)(__\\_)\n");
        while(true) {
            System.out.println("Enter A to Add a new student, V to view students and their information, R to remove students, or Q to quit");
            String input = scan.nextLine();
            switch (input) {
                case "A":
                    System.out.println("Enter your name: ");
                    String userName = scan.nextLine();
                    System.out.println("Enter your graduation year");
                    int userGraduation = scan.nextInt();
                    System.out.println("Enter your GPA: ");
                    double userGPA = scan.nextDouble();
                    Student user = new Student(userName, userGraduation, userGPA);
                    studentDB.add(user);
                    String consume = scan.nextLine(); // Placed in order to start newline
                    break;
                case "V":
                    for(int x = 0; x < studentDB.size(); x++){
                        studentDB.get(x).displayStudent();
                    }
                    break;
                case "R":
                    System.out.println("Enter id of Student");
                    int selectedID = scan.nextInt();
                    studentDB.remove(selectedID);
                    String consume2 = scan.nextLine(); // Placed in order to start newline
                    break;
                case "Q":
                    System.out.println("Thank you for using Student Manager! ");
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("This is not a valid input.");
                    break;
            }
        }// end of while
    }// end of main method
}