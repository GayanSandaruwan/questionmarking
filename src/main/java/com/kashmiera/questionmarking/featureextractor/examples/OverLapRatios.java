package com.kashmiera.questionmarking.featureextractor.examples;

import edu.stanford.nlp.pipeline.Annotation;
import com.kashmiera.questionmarking.featureextractor.grammaticalrelationships.GrammarOverlapRatio;
import com.kashmiera.questionmarking.featureextractor.lexicalsimilarity.OverlapWordRatio;
import com.kashmiera.questionmarking.utils.NLPUtils;

import java.util.ArrayList;

public class OverLapRatios {

	public static void main(String[] args) {
		String sourceSentence = "The Julie loves me more than Linda loves me";
		String targetSentence = "A Jane likes me more than Julie loves me";

		OverlapWordRatio overlapWordRatio = new OverlapWordRatio();
		ArrayList<Double> overlapScores = overlapWordRatio.getOverlapScore(sourceSentence, targetSentence);
		System.out.println("Word overlapScores :" + overlapScores.toString());

		NLPUtils nlpUtils = new NLPUtils("tokenize,ssplit,pos,depparse");
		Annotation sourceAnnotation = nlpUtils.annotate(sourceSentence);
		Annotation targetAnnotation = nlpUtils.annotate(targetSentence);

		GrammarOverlapRatio grammarOverlapRatio = new GrammarOverlapRatio(sourceAnnotation, targetAnnotation, nlpUtils);
		System.out.println("Subject Overlap Ratio (S1) : " + grammarOverlapRatio.getSubjectOverlap());
		System.out.println("Object Overlap Ratio (S1) : " + grammarOverlapRatio.getObjectOverlap());
		System.out.println("Subject Noun Overlap Ratio (S1) : " + grammarOverlapRatio.getSubjectNounOverlap());

	}
}
