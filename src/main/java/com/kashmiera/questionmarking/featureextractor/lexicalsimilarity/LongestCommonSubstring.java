package com.kashmiera.questionmarking.featureextractor.lexicalsimilarity;

import java.util.ArrayList;

public class LongestCommonSubstring {

    private String sourceSentence;
    private String targetSentence;
    private int longestCSLength=0;

    public LongestCommonSubstring(String sourceSentence, String targetSentence){
        this.sourceSentence = sourceSentence;
        this.targetSentence = targetSentence;
    }


    public ArrayList<String> longestSubstring() {

        ArrayList<String> longestCommonSubstrings = new ArrayList<String>();
        StringBuilder longestSubString = new StringBuilder();


        if (sourceSentence == null ||  targetSentence == null ) {
            return null;
        }

        // not case sensitive
        sourceSentence = sourceSentence.toLowerCase();
        targetSentence = targetSentence.toLowerCase();

        // java initializes them already with 0

        //two dimensional array
        int[][] num = new int[sourceSentence.length()][targetSentence.length()];
        //current length of longest substring
        int currentMaxLength = 0;
        int lastSubsBegin = 0;

        for (int i = 0; i < sourceSentence.length(); i++) {
            for (int j = 0; j < targetSentence.length(); j++) {
                if (sourceSentence.charAt(i) == targetSentence.charAt(j)) {
                    if ((i == 0) || (j == 0)) {
                        num[i][j] = 1;
                    }
                    else {
                        //diagonal previous value+1
                        num[i][j] = 1 + num[i - 1][j - 1];
                    }

                    if (num[i][j] > currentMaxLength) {
                        currentMaxLength = num[i][j];
                        // generate substring from str1 => i
                        int thisSubsBegin = i - num[i][j] + 1;
                        if (lastSubsBegin == thisSubsBegin) {
                            //if the current LCS is the same as the last time this block ran
                            longestSubString.append(sourceSentence.charAt(i));
                        } else {
                            //this block resets the string builder if a different LCS is found
                            lastSubsBegin = thisSubsBegin;
                            longestSubString = new StringBuilder();
                            longestSubString.append(sourceSentence.substring(lastSubsBegin, i + 1));
                        }
                        longestCommonSubstrings=new ArrayList<String>();
                        longestCommonSubstrings.add(longestSubString.toString());
                    }
                }
            }}

        longestCSLength = longestCommonSubstrings.get(0).length();
        return longestCommonSubstrings;
    }

    public double lcsValueSentence1(){
        longestSubstring();
        double lcsValue = (double) longestCSLength/(double) sourceSentence.length();
        return lcsValue;
    }
}
