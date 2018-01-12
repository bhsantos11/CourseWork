// A sample use of the Box2 class.
public class Test2 {
  public static void main(String argv[]) {
    Box2 x = new Box2(97,101);
    Box2 y = Box2.copy1(x);
    Box2 z = Box2.copy2(x);
    System.out.println("Before change");
    System.out.println("x: " + x.pair[0] + "  " + x.pair[1]);
    System.out.println("y: " + y.pair[0] + "  " + y.pair[1]);
    System.out.println("z: " + z.pair[0] + "  " + z.pair[1]);
    x.pair[0] = 500;
    x.pair[1] = 600;
    System.out.println("After change");
    System.out.println("x: " + x.pair[0] + "  " + x.pair[1]);
    System.out.println("y: " + y.pair[0] + "  " + y.pair[1]);
    System.out.println("z: " + z.pair[0] + "  " + z.pair[1]);

  }
}
/*
//OUTPUT
Before change
x: 97  101
y: 97  101
z: 97  101
After change
x: 500  101
y: 97  600
z: 500  101
//ANSWER
copy1 is deep copy, with copy2 being shallow copy.
//EXPLANATION
Shallow copy is when we get the object that will be copied unto to point towards the memory of the object that will be copied.
Deep copy is when we change the values of the object to be copied unto, to the values of the object that is being copied.
Here first we have make 2 copies y with copy1 and z with copy2. We then change the values of x.
Since Z is also affected by this copy, it means that it is pointing to the same memory location as x, therefore being a shallow copy.
 */