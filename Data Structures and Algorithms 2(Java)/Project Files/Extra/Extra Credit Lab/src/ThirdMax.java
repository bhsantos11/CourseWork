public class ThirdMax {

    public static int thirdMax(int[] nums){
        int tMax = nums[0];
        int b1=0 , b2 = 0, b3 = 0; // b1 is biggest, b2 is 2nd biggest, and b3 is third biggest

        for(int num : nums) {// loop to find b values
            // One if statement for each b value
            if (num > b1) {
                b3 = b2;
                b2 = b1;
                b1 = num;
            }
            if (num > b2 && num < b1) {
                b3 = b2;
                b2 = num;
            }
            if (num > b3 && num < b2) b3 = num;
        }
        tMax = b3;
        if(b3 == 0) tMax = b1;

        System.out.println("b1: " + b1);
        System.out.println("b2: " + b2);
        System.out.println("b3: " + b3);

        return tMax;
    }
    public static void main(String[] args){
        int[] array = {2, 2, 3, 1};
        String arrayString = "";
        for(int num: array) arrayString += (num + ", " );
        int x = thirdMax(array);

        System.out.println("Array is: " + arrayString);
        System.out.println("Result is: " + x);
        System.out.println(1%3);
    }
}