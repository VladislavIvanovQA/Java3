import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class TestEntryArray {
    public static Stream<Arguments> dataForArray() {
        List<Arguments> arguments = new ArrayList<>();
        arguments.add(Arguments.arguments(new int[]{1, 2, 3, 4, 4, 2, 3, 4, 1, 7}, new int[]{1, 7}));
        arguments.add(Arguments.arguments(new int[]{1, 4, 1, 1, 1, 1}, new int[]{1, 1, 1, 1}));
        arguments.add(Arguments.arguments(new int[]{4, 4, 4, 4}, new int[]{}));
        arguments.add(Arguments.arguments(new int[]{1, 2, 3, 5, 6, 7, 8}, null));
        arguments.add(Arguments.arguments(new int[]{4, 2, 3, 5, 6, 7, 8}, new int[]{2, 3, 5, 6, 7, 8}));
        return arguments.stream();
    }

    public static int[] checkEnterArray(int[] array) {
        int count = (int) Arrays.stream(array)
                .filter(ar -> ar == 4)
                .count();
        if (count == 0) {
            throw new RuntimeException("Array not have 4 number.");
        }

        int pos = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 4) pos = i;
        }

        pos = pos + 1;
        int newLength = array.length - pos;
        int[] newArr = new int[newLength];
        for (int i = 0; i < newLength; i++) {
            newArr[i] = array[i + pos];
        }

        return newArr;
    }

    @ParameterizedTest
    @MethodSource("dataForArray")
    public void testArray(int[] arrays, int[] result) {
        Assertions.assertArrayEquals(checkEnterArray(arrays), result);
    }
}
