package test_1;

public class Hello {
    public static void main(String[] args) {
        System.out.println(args.length);
        for (String arg : args) {
            System.out.println(arg);
        }

        System.out.println("Hell(o) World!");

        TesObj obj = new TesObj();
        obj.setName("Elia");

        System.out.println(obj.getName());

    }
}