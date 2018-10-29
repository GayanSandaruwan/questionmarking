package com.kashmiera.questionmarking.featureextractor.grammaticalrelationships;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import edu.stanford.nlp.pipeline.Annotation;
import com.kashmiera.questionmarking.featureextractor.sentenceproperties.SentenceObjects;
import com.kashmiera.questionmarking.featureextractor.sentenceproperties.SentenceProps;
import com.kashmiera.questionmarking.featureextractor.sentenceproperties.SentenceSubjects;
import com.kashmiera.questionmarking.utils.NLPUtils;

public class GrammarOverlapRatio {

	private Annotation sourceAnnotation;
	private Annotation targetAnnotation;
	private NLPUtils nlpUtils;

	public GrammarOverlapRatio(Annotation sourceAnnotation,Annotation targetAnnotation, NLPUtils nlpUtils) {
		this.sourceAnnotation = sourceAnnotation;
		this.targetAnnotation = targetAnnotation;
		this.nlpUtils = nlpUtils;
	}

	public double getSubjectOverlap() {
		SentenceProps sentenceProps = new SentenceSubjects(sourceAnnotation, targetAnnotation, nlpUtils);

		sentenceProps.initializeDistinctsCommons();

		int distinctsSize = sentenceProps.getDistincts_sentence1().size();

		if(distinctsSize==0){
			return 0;
		}

		return ((double) sentenceProps.getCommons().size() /
				(double) distinctsSize);
	}

	public double getObjectOverlap() {
		SentenceProps sentenceProps = new SentenceObjects(sourceAnnotation, targetAnnotation, nlpUtils);

		sentenceProps.initializeDistinctsCommons();

		int distinctsSize = sentenceProps.getDistincts_sentence1().size();

		if(distinctsSize==0){
			return 0;
		}

		return ((double) sentenceProps.getCommons().size() /
				(double) distinctsSize);
	}

	public double getSubjectNounOverlap() {
		ArrayList<String> subjectsSent1 = nlpUtils.getSubjects(sourceAnnotation);
		ArrayList<String> nounsSent2 = nlpUtils.getNouns(targetAnnotation);

		// distinct subjects
		Set<String> set = new HashSet<String>(subjectsSent1);
		// number of distinct subjects
		int noSubjects = set.size();

		if(noSubjects==0){
			return 0;
		}

		// common elements from subjects and nouns
		set.retainAll(nounsSent2);
		// number of common elements
		int noCommon = set.size();

		return (double) noCommon / (double) noSubjects;
	}
}
