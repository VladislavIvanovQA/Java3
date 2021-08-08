package HW1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MethodCastArrayList extends TestObjects {
    public static void main(String[] args) {
        testArray.forEach(arr -> {
            System.out.println("Before: " + arr.getClass().getName());
            System.out.println(Arrays.toString(arr));
            System.out.println("After: " + arr.getClass().getName());
            System.out.println(convertArrayToList(arr).getClass().getName());
        });
    }

    public static <T> List<T> convertArrayToList(T[] initArray) {
        return new ArrayList<>(Arrays.asList(initArray));
    }
}
