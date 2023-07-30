package com.epam.rd.autotasks.words;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Pattern;

public class StringUtil {
    static int count = 0;

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

        boolean isUnix = path.startsWith("~") || path.startsWith("/");
        boolean isWindows = path.startsWith("C:");

        if (! isCorrectPathUnix(path) && ! isCorrectPathWindows(path)) return null;

        if (toWin) {
            return convertUnixToWindows(path);
        } else {
            return convertWindowsToUnix(path);
        }

    }

    private static boolean isCorrectPathUnix(String path) {
        if (path.contains("~")) count++;
        char[] chars = path.toCharArray();
        for (char ch : chars) {
            if (ch == '\\'
                    || ch == 'C'
                    || count > 2) return false;
        }
        return true;
//        return path.ccontains("\\")
//                && path.startsWith("~") || path.startsWith("/")
//                || ! path.contains("C:/")
//                || count <= 1;
    }

    private static boolean isCorrectPathWindows(String path) {
        if (path.contains("C:/")) count++;
        char[] chars = path.toCharArray();
        for (char ch : chars) {
            if (ch == '~'
            || ch == '/'
            || count > 1) return false;
        }
        return true;

//        return path.contains("~\\")
//                || ! path.contains("~")
//                || ! path.contains("/");
    }

    private static String convertUnixToWindows(String path) {
        if (path.startsWith("~/")) {
            path = "C:\\User\\".concat(path.substring(2).replace('/', '\\'));
        } else if (path.equals("/")) {
            path = "C:\\";
        } else if (path.startsWith("/") && path.length() > 1) {
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
        } else return path.replace("/", "\\");

        return path;
    }

    private static String convertWindowsToUnix(String path) {
        if (path.startsWith("C:\\User\\")) {
            path = "~/".concat(path.substring(8)).replace("\\", "/");
        } else if (path.endsWith("User")) {
            path = "~";
        } else if (path.startsWith("C:")) {
            path = "/".concat(path.substring(3)).replace("\\", "/");
        } else if (path.equals(".")) {
            path = ".";
        } else if (path.equals("..")) {
            path = "..";
        } else if (path.equals("C:\\")) {
            path = "/";
        } else if (path.startsWith("..\\") && path.endsWith("\\")) {
            path = "../".concat(path.substring(3, path.length() - 1)).concat("/");
        } else if (path.startsWith("..\\")) {
            path = "../".concat(path.substring(3));
        } else return path.replace("\\", "/");

        return path;
    }

    public static String joinWords(String[] words) {
        if (words == null || words.length == 0) return null;
        List<String> list = new ArrayList<>();
        for (String word : words) {
            if (! word.isEmpty()) list.add(word);
        }
        if (list.size() > 0) {
            StringJoiner joiner = new StringJoiner(", ", "[", "]");
            String res = "";
            for (String word : words) {
                if (word.isEmpty()) continue;
                res = joiner.add(word).toString();
            }
            return res;
        }
        return null;
    }


}