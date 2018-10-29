package com.kashmiera.questionmarking.featureextractor.sentenceproperties;

import com.kashmiera.questionmarking.utils.WordUtils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SentenceWords extends SentenceProps{

	public SentenceWords(String sentence1, String sentence2){
		// Extracting words from string
		Pattern p = Pattern.compile("[a-zA-Z]+");
		Matcher m1 = p.matcher(sentence1);
		Matcher m2 = p.matcher(sentence2);

		seq_sentence1= new ArrayList<String>();
		seq_sentence2= new ArrayList<String>();

		while (m1.find())
		{
			String word = m1.group();
			if(!WordUtils.getStopWords().contains(word.toLowerCase())) {
				seq_sentence1.add(word.toLowerCase());
			}
		}

		while (m2.find())
		{
			String word = m2.group();
			if(!WordUtils.getStopWords().contains(word.toLowerCase())) {
				seq_sentence2.add(word.toLowerCase());
			}
		}

//		System.out.println("st1 :"+seq_sentence1);
//		System.out.println("st2 :"+seq_sentence2);
	}
}

