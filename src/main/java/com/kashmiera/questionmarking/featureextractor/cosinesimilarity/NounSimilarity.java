package com.kashmiera.questionmarking.featureextractor.cosinesimilarity;

import edu.stanford.nlp.pipeline.Annotation;
import com.kashmiera.questionmarking.featureextractor.sentenceproperties.SentenceNouns;
import com.kashmiera.questionmarking.utils.NLPUtils;

public class NounSimilarity extends Similarity{

	public NounSimilarity(Annotation sourceAnnotation,Annotation targetAnnotation, NLPUtils nlpUtils){
		sentenceProps = new SentenceNouns(sourceAnnotation, targetAnnotation, nlpUtils);
	}

}
