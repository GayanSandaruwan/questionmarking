package com.kashmiera.questionmarking.featureextractor.cosinesimilarity;

import edu.stanford.nlp.pipeline.Annotation;
import com.kashmiera.questionmarking.featureextractor.sentenceproperties.SentenceAdjectives;
import com.kashmiera.questionmarking.utils.NLPUtils;

public class AdjectiveSimilarity extends Similarity{

	public AdjectiveSimilarity(Annotation sourceAnnotation,Annotation targetAnnotation, NLPUtils nlpUtils){
		sentenceProps = new SentenceAdjectives(sourceAnnotation, targetAnnotation, nlpUtils);
	}

}
