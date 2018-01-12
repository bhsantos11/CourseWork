// A sample use of the Box1 class.

public class Test1 {
  public static void main(String argv[]) {
    Box1 x = new Box1();
    x.setA(23);
    x.setB(25);

    Box1 y = new Box1();

    y.setA(33);
    y.setB(35);
    System.out.println("Xa: "+x.getA());
    System.out.println("Xb: "+x.getB());
  }
}
/*
// OUTPUT
Xa: 33
Xb: 25
//ANSWER
a is the static variable
// EXPLANATION
After instantiating X I set its a and b values to 23 and 25 respectively. After I instantiate Y I set its x and y to 33 and 35 respectively.
We can see by the outputs that Xa was changed by the setting of Xb.
This proves that a is the static variable in the Box1 class.
This is true because an static variable is shared accross all instances of a class, therefore when we change the variable in one instance of a class,
the variable in all instances of the class is effectively changed.
 */