package com.tiktak;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class StreamProcessor {

    /*
    1 - Filter out nulls and non-numeric strings
    2 - convert string to integer. And collect an Integer List
     */
    public static List<Integer> fixStream(Stream<Stream<String>> strStream) {
        // Flatten, filter, and convert Stream<Stream<String>> to List<Integer>
        List<Integer> integers = strStream
                .flatMap(Stream::sequential) // Flatten Stream<Stream<String>> to Stream<String>
                .filter(str -> str != null && str.matches("-?\\d+")) // Filter non-null and numeric strings
                .map(Integer::parseInt) // Convert to Integer
                .collect(Collectors.toList());

        // Process the list to group by 3s and sum > 90
        return processList(integers);
    }

    // group three element in each group
    // add to list if group elements sum >= 90
    private static List<Integer> processList(List<Integer> list) {
        return IntStream.range(0, list.size() / 3 * 3) // Process in groups of 3, discard incomplete group
                .filter(i -> i % 3 == 0) // Take every third element as the start of a new group
                .filter(i -> list.get(i) + list.get(i + 1) + list.get(i + 2) >= 90) // Sum of group >= 90
                .boxed() // Box the int values to Integer
                .flatMap(i -> IntStream.of(list.get(i), list.get(i + 1), list.get(i + 2)).boxed()) // Flatten groups
                .collect(Collectors.toList()); // Collect results into a list
    }

    public static void main(String[] args) {
        // Example usage
        Stream<Stream<String>> exampleStream = Stream.of(
                Stream.of("0", "s1", null, "35", "90", "60"),
                Stream.of("ttt", null, null, "15"),
                Stream.of("75", "95", "0", "0", null, "ssss", "0", "-15"),
                Stream.of("25", "fgdfg", "", "105", "dsfdsf", "-5")
                //    Stream.of("25", "fgdfg", "", "105", "dsfdsf", "-5","-5")
        );

        List<Integer> result = fixStream(exampleStream);
        System.out.println("************ result *************\n" + result);

        /*
        printed output:
        ************ result*************
        [0, 35, 90, 60, 15, 75, 95, 0, 0]
         */
    }
}
