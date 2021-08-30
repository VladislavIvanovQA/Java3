import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class TestArray {
    public static Stream<Arguments> dataForArray() {
        List<Arguments> arguments = new ArrayList<>();
        arguments.add(Arguments.arguments(new int[]{1, 1, 1, 4, 4, 1, 4, 4}, true));
        arguments.add(Arguments.arguments(new int[]{1, 1, 1, 1, 1, 1}, false));
        arguments.add(Arguments.arguments(new int[]{4, 4, 4, 4}, false));
        arguments.add(Arguments.arguments(new int[]{1, 4, 4, 1, 1, 4, 3}, false));
        return arguments.stream();
    }

    @ParameterizedTest
    @MethodSource("dataForArray")
    public void testArray(int[] arrays, boolean result) {
        Assertions.assertEquals(checkEnterArray(arrays), result);
    }

    public boolean checkEnterArray(int[] array) {
        int count4 = (int) Arrays.stream(array)
                .filter(ar -> ar == 4)
                .count();

        int count1 = (int) Arrays.stream(array)
                .filter(ar -> ar == 1)
                .count();

        return count1 != 0 && count4 != 0;
    }
}
