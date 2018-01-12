public class Course {
    // Variables
    private String courseName;
    private int credits;

    public Course(String name, int credits){
        setCourseName(name);
        setCredits(credits);
    }

    // Setter methods
    public void setCourseName(String name){
        this.courseName = name;
    }

    public void setCredits(int credits){
        this.credits = credits;
    }

    // get methods
    public String getCourseName() {
        return courseName;
    }

    public int getCredits() {
        return credits;
    }

    public void displayCourse() {
        System.out.println("Course name: " + getCourseName());
        System.out.println("Course credits: " + getCredits() + '\n');
    }
}