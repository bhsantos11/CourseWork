import java.util.Date;

public class Box3 {
    public int value;
    static boolean used = false;
    
    public Box3()          {
        if(used != true){
            System.out.println("Box3 executed " + new Date());
            used = true;
        }
        value = 0;
    }
    public void set(int v) { value = v;    }
    public int get()       { return value; }
    private static void timestamp() { 
      System.out.println( (new Date()) );
    }
}

