package HW1;

import java.util.Arrays;

public class MethodChangeElement extends TestObjects {
    public static void main(String[] args) {
        testArray.forEach(arr -> {
            System.out.println("Before: " + arr.getClass().getName());
            System.out.println(Arrays.toString(arr));
            System.out.println("After: " + arr.getClass().getName());
            System.out.println(Arrays.toString(changePosition(arr, 0, 1)));
            System.out.println(Arrays.toString(changePosition(arr, 4, 2)));
        });
    }

    public static <T> T[] changePosition(T[] arrayNumber, int targetIndex, int fromIndex) {
        T target = arrayNumber[targetIndex];
        T from = arrayNumber[fromIndex];
        arrayNumber[targetIndex] = from;
        arrayNumber[fromIndex] = target;
        return arrayNumber;
    }
}
