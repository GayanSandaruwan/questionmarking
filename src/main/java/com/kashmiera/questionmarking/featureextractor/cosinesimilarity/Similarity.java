package com.kashmiera.questionmarking.featureextractor.cosinesimilarity;

import java.util.Hashtable;
import java.util.LinkedList;

import com.kashmiera.questionmarking.featureextractor.sentenceproperties.SentenceProps;
import com.kashmiera.questionmarking.featureextractor.wordproperties.Frequency;

public abstract class Similarity {

	protected SentenceProps sentenceProps;

	public double similarityScore() {
		double similarity_score = 0.0000000;

		sentenceProps.initializeFrequencies();

		double vectS1S2 = 0.0000000;
		double vectS1_Sq = 0.0000000;
		double vectS2_Sq = 0.0000000;

		LinkedList<String> distincts = sentenceProps.getDistincts();
		Hashtable<String, Frequency> freq_vector = sentenceProps.getFreq_vector();

		for (int i = 0; i < distincts.size(); i++) {
			Frequency frequencyValues = freq_vector.get(distincts.get(i));

			double freq1 = (double) frequencyValues.getSentence1();
			double freq2 = (double) frequencyValues.getSentence2();

//			System.out.println(distincts.get(i) + "#" + freq1 + "#" + freq2);

			vectS1S2 = vectS1S2 + (freq1 * freq2);

			vectS1_Sq = vectS1_Sq + freq1 * freq1;
			vectS2_Sq = vectS2_Sq + freq2 * freq2;

		}

//		System.out.println("VectS1S2 " + vectS1S2 + " VectS1_Sq " + vectS1_Sq + " VectS2_Sq " + vectS2_Sq);

		if(vectS1_Sq==0 || vectS2_Sq==0){
			similarity_score = 0;
		}else {
			similarity_score = ((vectS1S2) / (Math.sqrt(vectS1_Sq) * Math.sqrt(vectS2_Sq)));
		}

		return similarity_score;
	}

}
