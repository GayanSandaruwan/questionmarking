package com.kashmiera.questionmarking.featureextractor.wordproperties;

public class Frequency {
	private int sentence1;
	private int sentence2;

	public Frequency(int f1, int f2)
	{
		this.sentence1 =f1;
		this.sentence2 =f2;
	}
	public void updateFrequency(int f1, int f2)
	{
		this.sentence1 =f1;
		this.sentence2 =f2;
	}

	public int getSentence1() {
		return sentence1;
	}

	public int getSentence2() {
		return sentence2;
	}
}
