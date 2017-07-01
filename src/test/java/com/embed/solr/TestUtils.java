package com.embed.solr;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * Created by sozhang on 6/28/17.
 */
public class TestUtils {

    private TestUtils() {
    }

    public static String fromCp(String path) {
        return TestUtils.class.getClassLoader().getResource(path).getFile();
    }

    public static List<Map<String, Object>> csvToListOfMap(File csvFile) {
        List<Map<String, Object>> records = Collections.emptyList();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile)));
            String[] headers = br.readLine().split(",");
            records = br.lines().map(s -> s.split(","))
                    .map(t -> IntStream.range(0, t.length)
                            .boxed()
                            .collect(toMap(i -> headers[i], i -> getObject(t[i]))))
                    .collect(toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }

    private static Object getObject(String s) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e2) {
                try {
                    return (Long.parseLong(s));
                } catch (NumberFormatException e3) {
                    return s;
                }
            }
        }
    }

}
