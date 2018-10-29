package com.kashmiera.questionmarking.featureextractor.examples;

import com.kashmiera.questionmarking.featureextractor.lexicalsimilarity.LongestCommonSubstring;

import java.util.ArrayList;

public class LCS{

    public static void main(String[] args) {
        String sentence1 = "The crash by the Piper tourist plane into the 26th floor occurred at 5:50 p.m. (1450 GMT) on Thursday, said journalist Desideria Cavina.";
        String sentence2 = "The crash by the Piper tourist plane into the 26th floor occurred at 5:50 p.m. (1450 GMT) on Thursday, said journalist Desideria Cavina.";
        ArrayList<String> output = new ArrayList<String>();

        LongestCommonSubstring longestCommonSubstring=new LongestCommonSubstring(sentence1.toLowerCase(),sentence2.toLowerCase());
        output = longestCommonSubstring.longestSubstring();
        double value = longestCommonSubstring.lcsValueSentence1();

        System.out.println("output LCS : " + output.toString());
        System.out.println("value LCS: "+ value);

    }
}
