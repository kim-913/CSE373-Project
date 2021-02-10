package problems;

/**
 * Parts b.iii, b.iv, and b.v should go here.
 *
 * (Implement `toString`, `rotateRight`, and `reverse` as described by the spec.
 *  See the spec on the website for picture examples and more explanation!)
 *
 * REMEMBER THE FOLLOWING RESTRICTIONS:
 * - Do not add any additional imports
 * - Do not create new `int[]` objects for `toString` or `rotateRight`
 **/

public class ArrayProblems {

    public static String toString(int[] array) {
        if (array.length == 0) {
            return "[]";
        }
        String string = "";
        if (array.length > 0) {
            string += "[" + array[0];
            for (int i = 1; i < array.length; i++) {
                string += ", " + array[i];
            }
            string += "]";
        }
        return string;
    }


    public static void rotateRight(int[] numbers) {
        if (numbers.length > 0) {
            int last = numbers[numbers.length - 1];
            for (int i = numbers.length - 1; i > 0; i--) {
                numbers[i] = numbers[i - 1];
            }
            numbers[0] = last;
        }
    }


    public static int[] reverse(int[] array) {
        int[] list2 = new int[array.length];
        int count = 0;
        for (int i = array.length - 1; i >= 0; i--) {
            list2[count] = array[i];
            count++;
        }
        return list2;
    }
}
