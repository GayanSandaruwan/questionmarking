package com.kashmiera.questionmarking.featureextractor.sentenceproperties;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

import com.kashmiera.questionmarking.featureextractor.wordproperties.Frequency;
import com.kashmiera.questionmarking.utils.WordUtils;

public abstract class SentenceProps {

	// holds : words,verbs,nouns,adjectives,subjects,objects
	protected ArrayList<String> seq_sentence1;
	protected ArrayList<String> seq_sentence2;

	// for all Overlap Ratios; both lexical and grammatical
	protected ArrayList<String> distincts_sentence1;
	protected ArrayList<String> distincts_sentence2;
	protected ArrayList<String> commons;

	// for Cosine Similarities
	private Hashtable<String, Frequency> freq_vector = new Hashtable<String,Frequency>();
	private LinkedList<String> distincts = new LinkedList<String>();


	/**
	 * Used for Cosine Similarities
	 */
	public void initializeFrequencies() {
		for (int i = 0; i < seq_sentence1.size(); i++) {
			String tmp_wd = seq_sentence1.get(i).trim();

				if (tmp_wd.length() > 0) {
					if (freq_vector.containsKey(tmp_wd)) {
						Frequency vals1 = freq_vector.get(tmp_wd);
						int freq1 = vals1.getSentence1() + 1;
						int freq2 = vals1.getSentence2();
						vals1.updateFrequency(freq1, freq2);
						freq_vector.put(tmp_wd, vals1);
					} else {
						Frequency vals1 = new Frequency(1, 0);
						freq_vector.put(tmp_wd, vals1);
						distincts.add(tmp_wd);
					}
				}

		}

		//prepare word frequency vector by using Text2
		for (int i = 0; i < seq_sentence2.size(); i++) {
			String tmp_wd = seq_sentence2.get(i).trim();
			if (tmp_wd.length() > 0) {
				if (freq_vector.containsKey(tmp_wd)) {
					Frequency vals1 = freq_vector.get(tmp_wd);
					int freq1 = vals1.getSentence1();
					int freq2 = vals1.getSentence2() + 1;
					vals1.updateFrequency(freq1, freq2);
					freq_vector.put(tmp_wd, vals1);
				} else {
					Frequency vals1 = new Frequency(0, 1);
					freq_vector.put(tmp_wd, vals1);
					distincts.add(tmp_wd);
				}
			}
		}
	}

	/**
	 * Used for Overlap Ratios
	 */
	public void initializeDistinctsCommons(){
		distincts_sentence1 =new ArrayList<String>();
		distincts_sentence2 =new ArrayList<String>();
		commons =new ArrayList<String>();

		for(String element:seq_sentence1){
			if(!distincts_sentence1.contains(element)){
				distincts_sentence1.add(element);
			}
		}

		for(String element:seq_sentence2){
			if(!distincts_sentence2.contains(element)){
				distincts_sentence2.add(element);
				if(distincts_sentence1.contains(element)){
					commons.add(element);
				}
			}
		}

//		System.out.println("dt1 :"+ distincts_sentence1);
//		System.out.println("dt2 :"+ distincts_sentence2);
//		System.out.println("cw :"+ commons);
	}

	public ArrayList<String> getSeq_sentence1() {
		return seq_sentence1;
	}

	public void setSeq_sentence1(ArrayList<String> seq_sentence1) {
		this.seq_sentence1 = seq_sentence1;
	}

	public ArrayList<String> getSeq_sentence2() {
		return seq_sentence2;
	}

	public void setSeq_sentence2(ArrayList<String> seq_sentence2) {
		this.seq_sentence2 = seq_sentence2;
	}

	public Hashtable<String, Frequency> getFreq_vector() {
		return freq_vector;
	}

	public void setFreq_vector(Hashtable<String, Frequency> freq_vector) {
		this.freq_vector = freq_vector;
	}

	public LinkedList<String> getDistincts() {
		return distincts;
	}

	public void setDistincts(LinkedList<String> distincts) {
		this.distincts = distincts;
	}

	public ArrayList<String> getDistincts_sentence1() {
		return distincts_sentence1;
	}

	public ArrayList<String> getDistincts_sentence2() {
		return distincts_sentence2;
	}

	public ArrayList<String>
	getCommons() {
		return commons;
	}
}
