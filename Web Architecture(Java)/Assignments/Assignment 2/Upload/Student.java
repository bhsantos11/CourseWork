import java.util.Calendar;
import java.util.ArrayList;
import java.util.Scanner;
public class Student {
    // variables
    private static int numberOfStudents = 0;
    private String name;
    private double GPA;
    private String group;
    private int year;
    private int id;
    private ArrayList<Course> courseDB = new ArrayList();

    // Calendar

    Calendar now = Calendar.getInstance();
    int currentYear = now.get(1); // Looking at 'get' method we can see that if we pass in '1' it will return the year
    int currentMonth = now.get(2);
    Scanner scan = new Scanner(System.in);


    public Student(String name, int graduationYear, double g) throws Exception {
        setName(name);
        try {setYear(graduationYear);}
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        setGroup(year);
        setGPA(g);
        setCourses();
        id = numberOfStudents;
        numberOfStudents++;
    }

    // Set methods
    public void setName(String name) {
        this.name = name;

    }

    public void setYear(int graduation) throws Exception {
        if (graduation < currentYear) {
            throw new Exception("Student already graduated");
        }
        int x = graduation - currentYear;
        if (currentMonth > 7) { // If true student in fall semester
            x--;
        }
        year = 4 - x;
    }

    public void setGroup(int year) {
        String[] groups = {"Freshman", "Sophomore", "Junior", "Senior"};
        this.group = groups[year - 1];
    }

    public void setCourses() {
        System.out.println("You will now enter your courses");
        while(true) {
            System.out.println("Enter A to Add course, D to display courses, and Q to quit back to main menu.");
            String input = scan.nextLine();
            switch (input) {
                case "A":
                    System.out.println("Enter course name: ");
                    String courseName = scan.nextLine();
                    if(courseName.equals('Q')) break;// Having the if check courseName is probably not the best validation practice
                    System.out.println("Enter number of credits: ");
                    int courseCredits = scan.nextInt();
                    Course course = new Course(courseName, courseCredits);
                    courseDB.add(course);
                    String consume = scan.nextLine(); // Placed in order to start newline
                    break;
                case "D":
                    for(int x = 0; x < courseDB.size(); x++){
                        courseDB.get(x).displayCourse();
                    }
                    break;
                case "Q":
                    return;
                default:
                    System.out.println("This is not a valid input.");
                    break;
            }
        }
    }

    public void setGPA(double g) {
        GPA = g;

    }

    // Get methods
    public String getName(){
        return name;
    }

    public int getYear() {
        return year;
    }

    public String getGroup() {
        return group;
    }

    public double getGPA() {
        return GPA;
    }

    public int getID(){
        return id;
    }

    public void displayStudent(){
        System.out.println("Student name: " + getName());
        System.out.println("Student year: " + getGroup());
        System.out.println("Student GPA: " + getGPA());
        System.out.println("Student ID: " + getID() + '\n');
    }
}// end of Student class