package com.kashmiera.questionmarking.featureextractor.cosinesimilarity;

import com.kashmiera.questionmarking.featureextractor.sentenceproperties.SentenceWords;

public class WordSimilarity extends Similarity{

	public WordSimilarity(String sentence1, String sentence2){
		sentenceProps = new SentenceWords(sentence1,sentence2);
	}
}
