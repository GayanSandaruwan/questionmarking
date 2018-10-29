package com.kashmiera.questionmarking.featureextractor.sentenceproperties;

import edu.stanford.nlp.pipeline.Annotation;
import com.kashmiera.questionmarking.utils.NLPUtils;

public class SentenceSubjects extends SentenceProps{

	public SentenceSubjects(Annotation sourceAnnotation,Annotation targetAnnotation, NLPUtils nlpUtils) {
		seq_sentence1 = nlpUtils.getSubjects(sourceAnnotation);
		seq_sentence2 = nlpUtils.getSubjects(targetAnnotation);
	}

}
