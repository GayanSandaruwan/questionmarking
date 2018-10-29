package com.kashmiera.questionmarking.featureextractor.examples;

import edu.stanford.nlp.pipeline.Annotation;
import com.kashmiera.questionmarking.featureextractor.semanticsimilarity.SemanticSentenceSimilarity;
import com.kashmiera.questionmarking.utils.NLPUtils;

public class SemanticStcSimilarity {
    static NLPUtils nlpUtils = new NLPUtils("tokenize,ssplit,pos");
    static String sentence1 = "Database";
    static String sentence2 = "Database is a collection of data";

    public static void main(String[] args) {


        Annotation annotation1 =nlpUtils.annotate(sentence1);
        Annotation annotation2 = nlpUtils.annotate(sentence2);

        SemanticSentenceSimilarity semanticSentenceSimilarity = new SemanticSentenceSimilarity(annotation1,
                annotation2,nlpUtils);

        double finalScore = semanticSentenceSimilarity.getAverageScore();

        System.out.println("finalScore : "+finalScore);
    }
}
