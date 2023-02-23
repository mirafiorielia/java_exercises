package test_2;

public class Utils {
    public static double[] findMinMax(double[] a) {
        if (a == null || a.length == 0)
            throw new IllegalArgumentException();

        double min = a[0];
        double max = a[0];

        for (int index = 0; index < a.length; index++) {
            if (a[index] < min) {
                min = a[index];
            } else if (a[index] > max) {
                max = a[index];
            }
        }

        System.out.println("MIN: " + min);
        System.out.println("MAX: " + max);

        return new double[] { min, max };
    }
}
