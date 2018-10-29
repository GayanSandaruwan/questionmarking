package com.kashmiera.questionmarking.featureextractor.examples;

import com.kashmiera.questionmarking.featureextractor.sentencepropertyfeatures.SentenceLengths;

public class SentenceLengthExample {

    public static void main(String[] args) {

        SentenceLengths sentenceLengths = new SentenceLengths("This sentence has more words", "This sentence has words");
        System.out.println("outputScore : "+ sentenceLengths.getLengthScore());

    }




}
