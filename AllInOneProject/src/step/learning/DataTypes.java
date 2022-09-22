package step.learning;

public class DataTypes {
    public void run() {
        String message = ConsoleColors.BLUE;
        message += "Data types demo";
        message += ConsoleColors.RESET;
        System.out.println(message);

        byte bx = 10;
        short sx = 20;
        int ix = 30;
        long lx = 40;
        double dx = 5.0;
        float fx = 6.0f;

        System.out.println("bx = " + bx + " " + ((Object)bx).getClass().getSimpleName());
        System.out.println("sx = " + sx + " " + ((Object)sx).getClass().getSimpleName());
        System.out.println("ix = " + ix + " " + ((Object)ix).getClass().getSimpleName());
        System.out.println("lx = " + lx + " " + ((Object)lx).getClass().getSimpleName());
        System.out.println("dx = " + dx + " " + ((Object)dx).getClass().getSimpleName());
        System.out.println("fx = " + fx + " " + ((Object)fx).getClass().getSimpleName());

        System.out.println((char)65);
        System.out.println((int)'A');

        String s1 = "ABC";
        String s2 = "AB" + "C";
        String s3 = new String("ABC");
        String s4 = new String("ABC");

        System.out.println(s1 == s2);
        System.out.println(s1.equals(s2));
        System.out.println(s3 == s4);
        System.out.println(s3.equals(s4));
    }
}