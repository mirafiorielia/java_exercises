import java.util.Scanner;

public class Mirafiori2008772MaxRun {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        System.out.print("Input: ");
        String input = console.nextLine();
        int index = findIndexRun(input.toLowerCase());

        System.out.println("input: " + input);
        String output = "";
        for (int i = index; i < input.length(); i++) {
            if (input.charAt(i) != input.charAt(index)) {
                break;
            }
            output += input.charAt(index);
        }
        System.out.println("output: " + output);
    }

    public static int findIndexRun(String value) {
        int index = 0;
        int max = 0;

        for (int i = 0; i < value.length(); i++) {
            int count = 1;
            for (int j = i + 1; j < value.length(); j++) {
                if (value.charAt(i) != value.charAt(j)) {
                    break;
                }

                count++;
                if (count > max) {
                    max = count;
                    index = i;
                }
            }
        }
        return index;
    }
}
