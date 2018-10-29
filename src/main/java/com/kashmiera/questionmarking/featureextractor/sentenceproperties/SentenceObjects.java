package com.kashmiera.questionmarking.featureextractor.sentenceproperties;

import edu.stanford.nlp.pipeline.Annotation;
import com.kashmiera.questionmarking.utils.NLPUtils;

public class SentenceObjects extends SentenceProps{

	public SentenceObjects(Annotation sourceAnnotation,Annotation targetAnnotation, NLPUtils nlpUtils) {
		seq_sentence1 = nlpUtils.getObjects(sourceAnnotation);
		seq_sentence2 = nlpUtils.getObjects(targetAnnotation);
	}

}
