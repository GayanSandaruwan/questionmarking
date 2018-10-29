package com.kashmiera.questionmarking.featureextractor.cosinesimilarity;

import edu.stanford.nlp.pipeline.Annotation;
import com.kashmiera.questionmarking.featureextractor.sentenceproperties.SentenceVerbs;
import com.kashmiera.questionmarking.utils.NLPUtils;

public class VerbSimilarity extends Similarity{

	public VerbSimilarity(Annotation sourceAnnotation,Annotation targetAnnotation, NLPUtils nlpUtils){
		sentenceProps = new SentenceVerbs(sourceAnnotation, targetAnnotation, nlpUtils);
	}

}
