package com.tiktak;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.List;
import java.util.stream.Collectors;

public class StreamProcessorTest {

    @Test
    public void testFixStream() {
        Stream<Stream<String>> strStream = generateADummyTestStreamOfStream();

        final var integers = flatAndConvertToIntegerList(strStream);

        assertEquals(14, integers.size()); // Asserting the size of the list

    }

    @Test
    public void collectThreeElementsGroup() {
        Stream<Stream<String>> strStream = generateADummyTestStreamOfStream();

        final var list = flatAndConvertToIntegerList(strStream);

        final var outputGroups = IntStream.range(0, list.size() / 3 * 3) // Process in groups of 3, discard incomplete group
                .filter(i -> i % 3 == 0) // Take every third element as the start of a new group
                .filter(i -> list.get(i) + list.get(i + 1) + list.get(i + 2) >= 90) // Sum of group >= 90
                .boxed() // Box the int values to Integer
                .flatMap(i -> IntStream.of(list.get(i), list.get(i + 1), list.get(i + 2)).boxed()) // Flatten groups
                .collect(Collectors.toList()); // Collect results into a list

        assertEquals(9, outputGroups.size());
        assertEquals(0, outputGroups.get(0));
        assertEquals(0, outputGroups.get(8));
        assertEquals(15, outputGroups.get(4));

        //[0, 35, 90, 60, 15, 75, 95, 0, 0]

    }

    private Stream<Stream<String>> generateADummyTestStreamOfStream() {
        return Stream.of(
                Stream.of("0", "s1", null, "35", "90", "60"),
                Stream.of("ttt", null, null , "15"),
                Stream.of("75", "95", "0", "0", null, "ssss", "0", "-15"),
                Stream.of("25", "fgdfg", "", "105", "dsfdsf", "-5"));
    }

    private List<Integer> flatAndConvertToIntegerList(Stream<Stream<String>> strStream) {
        return strStream
                .flatMap(Stream::sequential)
                .filter(str -> str != null && str.matches("-?\\d+"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    // Additional methods, including fixStream and processList...
}
