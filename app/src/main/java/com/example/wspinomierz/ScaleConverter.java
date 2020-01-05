package com.example.wspinomierz;

import java.util.HashMap;

public class ScaleConverter {

    private HashMap<String, Integer> uiaa2int = new HashMap<String, Integer>() {{
        put("I", 0);
        put("II", 1);
        put("II+", 2);
        put("III", 3);
        put("IV", 4);
        put("IV+", 6);
        put("V-", 7);
        put("V", 8);
        put("V+", 9);
        put("-VI", 10);
        put("VI", 11);
        put("VI+", 12);
        put("VII-", 13);
    }};
    HashMap<Integer, String> int2uiaa = new HashMap<Integer, String>() {{
        put(0, "I");
        put(1, "II");
        put(2, "II+");
        put(3, "III");
        put(4, "IV");
        put(5, "IV");
        put(6, "IV+");
        put(7, "V-");
        put(8, "V");
        put(9, "V+");
        put(10, "VI-");
        put(11, "VI");
        put(12, "VI+");
        put(13, "VII-");
    }};

    HashMap<Integer, String> int2kurtyki = new HashMap<Integer, String>() {{
        put(0, "I");
        put(1, "II");
        put(2, "II+");
        put(3, "III");
        put(4, "IV");
        put(5, "IV");
        put(6, "IV+");
        put(7, "V-");
        put(8, "V");
        put(9, "V+");
        put(10, "VI-");
        put(11, "VI");
        put(12, "VI+");
        put(13, "VI.1");
    }};

    private HashMap<String, Integer> kurtyki2int = new HashMap<String, Integer>() {{
        put("I", 0);
        put("II", 1);
        put("II+", 2);
        put("III", 3);
        put("IV", 4);
        put("IV+", 6);
        put("V-", 7);
        put("V", 8);
        put("V+", 9);
        put("VI-", 10);
        put("VI", 11);
        put("VI+", 12);
        put("VI.1", 13);
    }};

    private HashMap<String, Integer> francuska2int = new HashMap<String, Integer>() {{
        put("1", 0);
        put("2", 1);
        put("2+", 2);
        put("3", 3);
        put("4a", 4);
        put("4b", 5);
        put("4c", 6);
        put("5a", 7);
        put("5b", 8);
        put("5c", 9);
        put("6a", 11);
        put("6a+", 12);
        put("6b", 13);
    }};

    private HashMap<Integer, String> int2francuska = new HashMap<Integer, String>() {{
        put(0, "1");
        put(1, "2");
        put(2, "2+");
        put(3, "3");
        put(4, "4a");
        put(5, "4b");
        put(6, "4c");
        put(7, "5a");
        put(8, "5b");
        put(9, "5c");
        put(11, "6a");
        put(12, "6a+");
        put(13, "6b");
    }};

    HashMap<Integer, String> int2usa = new HashMap<Integer, String>() {{
        put(0, "5.1");
        put(1, "5.2");
        put(2, "5.2");
        put(3, "5.3");
        put(4, "5.4");
        put(5, "5.4");
        put(6, "5.5");
        put(7, "5.5");
        put(8, "5.6");
        put(9, "5.7");
        put(10, "5.8");
        put(11, "5.9");
        put(12, "5.10a");
        put(13, "5.10b");
    }};

    private HashMap<String, Integer> usa2int = new HashMap<String, Integer>() {{
        put("5.1", 0);
        put("5.2", 1);
        put("5.3", 3);
        put("5.4", 4);
        put("5.5", 6);
        put("5.6", 8);
        put("5.7", 9);
        put("5.8", 10);
        put("5.9", 11);
        put("5.10a", 12);
        put("5.10b", 13);
    }};

    public String String2String(String scale_from, String scale_to, String grade_from) {
        Integer scaleInInt = 0;
        String scaleInString = "I";
        switch (scale_from) {
            case "UIAA":
                scaleInInt = Integer.valueOf(uiaa2int.get(grade_from));
                break;
            case "Francuska":
                scaleInInt = Integer.valueOf(francuska2int.get(grade_from));
                break;
            case "Kurtyki":
                scaleInInt = Integer.valueOf(kurtyki2int.get(grade_from));
                break;
            case "USA":
                scaleInInt = Integer.valueOf(usa2int.get(grade_from));
                break;
        }
        switch (scale_to) {
            case "Francuska":
                scaleInString = int2francuska.get(scaleInInt);
                return scaleInString;
            case "Kurtyki":
                scaleInString = int2kurtyki.get(scaleInInt);
                return scaleInString;
            case "UIAA":
                scaleInString = int2uiaa.get(scaleInInt);
                return scaleInString;
            case "USA":
                scaleInString = int2usa.get(scaleInInt);
                return scaleInString;
        }
        return scaleInString;
    }

    public String Int2String( String scale_to, Integer grade_from) {
        String scaleInString = "I";
        switch (scale_to) {
            case "Francuska":
                scaleInString = int2francuska.get(grade_from);
                return scaleInString;
            case "Kurtyki":
                scaleInString = int2kurtyki.get(grade_from);
                return scaleInString;
            case "UIAA":
                scaleInString = int2uiaa.get(grade_from);
                return scaleInString;
            case "USA":
                scaleInString = int2usa.get(grade_from);
                return scaleInString;
        }
        return scaleInString;
    }
    public Integer String2Int( String scale_from, String grade_from) {
        Integer scaleInInt = 1;
        switch (scale_from) {
            case "UIAA":
                scaleInInt = Integer.valueOf(uiaa2int.get(grade_from));
                return scaleInInt;
            case "Francuska":
                scaleInInt = Integer.valueOf(francuska2int.get(grade_from));
                return scaleInInt;
            case "Kurtyki":
                scaleInInt = Integer.valueOf(kurtyki2int.get(grade_from));
                return scaleInInt;
            case "USA":
                scaleInInt = Integer.valueOf(usa2int.get(grade_from));
                return scaleInInt;
        }
        return scaleInInt;
    }
}
