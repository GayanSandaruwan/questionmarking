package com.kashmiera.questionmarking.featureextractor.examples;

import edu.stanford.nlp.pipeline.Annotation;
import com.kashmiera.questionmarking.featureextractor.cosinesimilarity.AdjectiveSimilarity;
import com.kashmiera.questionmarking.featureextractor.cosinesimilarity.NounSimilarity;
import com.kashmiera.questionmarking.featureextractor.cosinesimilarity.Similarity;
import com.kashmiera.questionmarking.featureextractor.cosinesimilarity.VerbSimilarity;
import com.kashmiera.questionmarking.featureextractor.cosinesimilarity.WordSimilarity;
import com.kashmiera.questionmarking.utils.NLPUtils;

public class CosineSimilarity {

    public static void main(String[] args) {

    	String sourceSentence = "Julie loves me more than Linda loves me";
    	String targetSentence = "Jane likes me more than Julie loves me";

	    Similarity wordSimilarity = new WordSimilarity(sourceSentence,targetSentence) ;
        double scoreWordsSimilarity = wordSimilarity.similarityScore();
        System.out.println( "wordSimilarityScore: "+ scoreWordsSimilarity);

        NLPUtils nlpUtils = new NLPUtils("tokenize,ssplit,pos");
	    Annotation sourceAnnotation = nlpUtils.annotate(sourceSentence);
	    Annotation targetAnnotation = nlpUtils.annotate(targetSentence);

        Similarity nounSimilarity = new NounSimilarity(sourceAnnotation, targetAnnotation, nlpUtils) ;
        double scoreNounsSimilarity = nounSimilarity.similarityScore();
        System.out.println( "nounSimilarityScore: "+ scoreNounsSimilarity);

	    Similarity verbSimilarity = new VerbSimilarity(sourceAnnotation, targetAnnotation, nlpUtils) ;
	    double scoreVerbsSimilarity = verbSimilarity.similarityScore();
	    System.out.println( "verbSimilarityScore: "+ scoreVerbsSimilarity);

	    Similarity adjectiveSimilarity = new AdjectiveSimilarity(sourceAnnotation, targetAnnotation, nlpUtils) ;
	    double scoreAdjectivesSimilarity = adjectiveSimilarity.similarityScore();
	    System.out.println( "adjectiveSimilarityScore: "+ scoreAdjectivesSimilarity);

    }
}
