package HW1;

import java.util.ArrayList;
import java.util.List;

public class TestObjects {
    public static List<Object[]> testArray = new ArrayList<>();

    static {
        Integer[] arrayInt = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        String[] arrayString = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        Double[] arrayDouble = new Double[]{1d, 2d, 3d, 4d, 5d, 6d, 7d, 8d, 9d};
        Long[] arrayLong = new Long[]{1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L};
        Float[] arrayFloat = new Float[]{1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F};
        Object[] arrayObj = new Object[]{new Object(), new Object(), new Object(), new Object(), new Object(), new Object(), new Object(), new Object(), new Object()};

        testArray.add(arrayInt);
        testArray.add(arrayString);
        testArray.add(arrayDouble);
        testArray.add(arrayLong);
        testArray.add(arrayFloat);
        testArray.add(arrayObj);
    }
}
