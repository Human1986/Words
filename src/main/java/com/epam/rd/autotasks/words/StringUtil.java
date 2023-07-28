package com.epam.rd.autotasks.words;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Pattern;

public class StringUtil {
    public static int countEqualIgnoreCaseAndSpaces(String[] words, String sample) {
        int count = 0;
        if (words == null || sample == null) return 0;
        for (String word : words) {
            String strip = word.strip();
            if (strip.equalsIgnoreCase(sample.strip())) count++;
        }
        return count;
    }

    public static String[] splitWords(String text) {
        if (text == null || text.isEmpty()) return null;
        Pattern pattern = Pattern.compile("\\W+");
        String[] words = pattern.split(text);
        List<String> s = new ArrayList<>();

        for (String word : words) {
            if (! word.isEmpty()) {
                s.add(word);
            }
        }

        String[] array = new String[s.size()];
        s.toArray(array);

        if (words.length == 0) return null;
        return array;
    }

    public static String convertPath(String path, boolean toWin) {
        if (path == null || path.isEmpty()) return null;
        if (! isCorrectPathUnix(path) && ! isCorrectPathWindows(path)) {
            return null;
        } else {
            if (toWin) {
                return convertUnixToWindows(path);
            } else return convertWindowsToUnix(path);
        }
    }

    private static String convertUnixToWindows(String path) {
//        if (path.contains("/")) path = path.replace("/", "\\");

        if (path.startsWith("~/")) {
            path = "C:\\User\\".concat(path.substring(2).replace('/', '\\'));
        } else if (path.startsWith("/")) {
            path = "C:" + path.replace('/', '\\');
        } else if (path.equals(".")) {
            path = ".";
        } else if (path.equals("..")) {
            path = "..";
        } else if (path.startsWith("~")) {
            path = "C:\\User";

        } else if (path.startsWith("../") && path.endsWith("/")) {
            path = "..\\".concat(path.substring(3, path.length() - 1)).concat("\\");
        } else if (path.startsWith("../")) {
            path = "..\\".concat(path.substring(3));
        } else return path;

        return path;
    }

    private static String convertWindowsToUnix(String path) {
        if (path.startsWith("~/")) {
            path = "C:".concat(path.substring(1).replace('/', '\\'));
        } else if (path.equals("~")) {
            path = "~";
        } else if (path.equals(".")) {
            path = ".";
        } else if (path.equals("..")) {
            path = "..";
        } else if (path.startsWith("~")) {
            path = "C:\\User";
        } else if (path.equals("/")) {
            path = "C:\\";
        } else if (path.startsWith("../") && path.endsWith("/")) {
            path = "..\\".concat(path.substring(3, path.length() - 1)).concat("\\");
        } else if (path.startsWith("../")) {
            path = "..\\".concat(path.substring(3));
        } else return path.replace("/", "\\");

        return path;
    }

    public static String joinWords(String[] words) {
        if (words == null || words.length == 0) return null;
        StringJoiner joiner = new StringJoiner(", ", "[", "]");
        String res = "";
        for (String word : words) {
            if (word.equals("")) return null;
            res = joiner.add(word).toString();
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println("Test 1: countEqualIgnoreCaseAndSpaces");
        String[] words = new String[]{" WordS    \t", "words", "w0rds", "WOR  DS",};
        String sample = "words   ";
        int countResult = countEqualIgnoreCaseAndSpaces(words, sample);
        System.out.println("Result: " + countResult);
        int expectedCount = 2;
        System.out.println("Must be: " + expectedCount);

        System.out.println("Test 2: splitWords");
        String text = "   ,, first, second!!!! third";
        String[] splitResult = splitWords(text);
        System.out.println("Result : " + Arrays.toString(splitResult));
        String[] expectedSplit = new String[]{"first", "second", "third"};
        System.out.println("Must be: " + Arrays.toString(expectedSplit));

        System.out.println("Test 3: convertPath");
        String unixPath = "/some/unix/path";
        String convertResult = convertPath(unixPath, true);
        System.out.println("Result: " + convertResult);
        String expectedWinPath = "C:\\some\\unix\\path";
        System.out.println("Must be: " + expectedWinPath);

        System.out.println("Test 4: joinWords");
        String[] toJoin = new String[]{"go", "with", "the", "", "FLOW"};
        String joinResult = joinWords(toJoin);
        System.out.println("Result: " + joinResult);
        String expectedJoin = "[go, with, the, FLOW]";
        System.out.println("Must be: " + expectedJoin);
    }

    private static boolean isCorrectPathUnix(String path) {
        String[] split = path.split("");
        int count = 0;

        for (String s : split) {
            if (s.contains("~") || s.contains("/")) count++;
            if (s.startsWith("~") || s.startsWith("/")
                    || ! s.endsWith("/") || s.contains("\\")
                    || ! s.contains("C:")
                    || count < 1)
                return true;
        }
        return false;
    }

    private static boolean isCorrectPathWindows(String path) {
        String[] split = path.split("");
        int count = 0;
        for (String s : split) {
            if (s.equals("C:")) count++;
            if (s.startsWith("C:")
                    && s.contains("\\")
                    && ! s.contains("~")
                    && count < 1)
                return true;
        }
        return false;
    }
}