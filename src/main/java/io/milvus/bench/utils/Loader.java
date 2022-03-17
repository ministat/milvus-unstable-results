package io.milvus.bench.utils;

import edu.emory.mathcs.backport.java.util.Arrays;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Loader {
    public static List<List<Long>> loadGroundTruth(InputStream is) {
        List<List<Long>> gnd = new ArrayList();
        Closeable resource = null;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            resource = br;
            String line = "";
            List<Long> oneGnd = new ArrayList();
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) {
                    gnd.add(oneGnd);
                    oneGnd = new ArrayList();
                } else {
                    oneGnd.add(Long.valueOf(line));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resource != null) {
                try {
                    resource.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return gnd;
    }

    public static List<List<Float>> loadFloatArrayFromCsv(InputStream is) {
        List<List<Float>> floatArray = new ArrayList();
        Closeable resource = null;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            resource = br;
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] items = line.split(",");
                List<String> itemList = Arrays.asList(items);
                List<Float> floats = itemList.stream().map(Float::valueOf).collect(Collectors.toList());
                floatArray.add(floats);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resource != null) {
                try {
                    resource.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return floatArray;
    }

    public static void saveFloatArrayToCsv(List<List<Float>> floatArray, String csvFile) {
        Closeable resource = null;
        try {
            FileWriter fileWriter = new FileWriter(csvFile);
            for (List<Float> row : floatArray) {
                List<String> strings = row.stream().map(String::valueOf).collect(Collectors.toList());
                fileWriter.append(String.join(",", strings));
                fileWriter.append(System.lineSeparator());
            }
            resource = fileWriter;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resource != null) {
                try {
                    resource.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
