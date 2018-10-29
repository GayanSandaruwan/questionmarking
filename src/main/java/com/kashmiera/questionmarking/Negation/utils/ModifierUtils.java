package com.kashmiera.questionmarking.Negation.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModifierUtils {


    private static List<String> negativeWords = new ArrayList<String>(Arrays.asList("never", "not", "nothing","no"));

    public static List<String> getNegativeWords() {
        return negativeWords;
    }

}
