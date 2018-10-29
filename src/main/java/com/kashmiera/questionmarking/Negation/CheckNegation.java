package com.kashmiera.questionmarking.Negation;

import edu.stanford.nlp.pipeline.Annotation;
import com.kashmiera.questionmarking.featureextractor.semanticsimilarity.SemanticSentenceSimilarity;

import com.kashmiera.questionmarking.Negation.models.VerbRelation;
import com.kashmiera.questionmarking.Negation.models.VerbPair;
import com.kashmiera.questionmarking.Negation.utils.ModifierUtils;
import com.kashmiera.questionmarking.utils.NLPUtils;

import java.util.ArrayList;
import java.util.Properties;

import edu.cmu.lti.jawjaw.pobj.POS;

public class CheckNegation {

	public static void main(String[] args) {

		String targetSentence = "Lee could not show that he was prejudiced by his attorney's erroneous advice.";
		String sourceSentence
				= "Lee has demonstrated that he was prejudiced by his counsel's erroneous advice.";

		// set up pipeline properties
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse");
		// use faster shift reduce parser
		props.setProperty("parse.model", "edu/stanford/nlp/models/srparser/englishSR.ser.gz");
		props.setProperty("parse.maxlen", "100");
		// set up Stanford CoreNLP pipeline
		// StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		NLPUtils nlpUtils = new NLPUtils(props);
		checkRelationsForOppositeness(nlpUtils, targetSentence, sourceSentence);
	}

	public static Integer checkRelationsForOppositeness(NLPUtils nlpUtils, String targetSentence, String sourceSentence) {

		ArrayList<VerbRelation> verbsSentence1;
		ArrayList<VerbRelation> verbsSentence2;

		Annotation targetAnnotation;
		Annotation sourceAnnotation;

		ArrayList<String> coreferencedSentences = new ArrayList<>();

		// build annotation for a review
		sourceAnnotation = nlpUtils.annotate(sourceSentence);
		targetAnnotation = nlpUtils.annotate(targetSentence);



       /* Coreferencer coreferencer = new Coreferencer();
        coreferencedSentences=Coreferencer.getCoreferencedSentences(sourceSentence,targetSentence,nlpUtils);
        System.out.println(coreferencedSentences.get(0));
        System.out.println(coreferencedSentences.get(1));*/
		ConstituentParser constituentParser = new ConstituentParser();
		//  constituentParser.runConstituentParser(sourceAnnotation,nlpUtils);

		System.out.println("break");

		verbsSentence1 = constituentParser.getVerbRelationships(sourceAnnotation, nlpUtils);

		System.out.println("targetSentence");
		System.out.println(" ");

		verbsSentence2 = constituentParser.getVerbRelationships(targetAnnotation, nlpUtils);
		String verbSource = "";
		String verbTarget = "";
		Integer verbSourceId = -1;
		Integer verbTargetId = -1;
		String sourceOther = "";
		String targetOther = "";
		VerbRelation verbObjectTarget = new VerbRelation();
		VerbRelation verbObjectSource = new VerbRelation();
		String currentSourceVerb = "";
		String currentTargetVerb = "";
		int currentSourceVerbID = -1;
		int currentTargetVerbID = -1;
		VerbRelation secondverb;
		VerbRelation verb;
		ArrayList<VerbPair> closeVerbs = new ArrayList<>();

		System.out.println("array sizes");
		System.out.println(verbsSentence2.size());
		System.out.println(verbsSentence1.size());

		for (int j = 0; j < verbsSentence2.size(); j++) {
			verb = verbsSentence2.get(j);

			if (verb.isVerbIsDep()) {
				verbTarget = verb.getDepLemma();
				verbTargetId = verb.getId();
				verbObjectTarget = verb;
			} else if (verb.isVerbIsGov()) {
				verbTarget = verb.getGovLemma();
				verbTargetId = verb.getId();
				verbObjectTarget = verb;
			}
			for (int i = 0; i < verbsSentence1.size(); i++) {
				secondverb = verbsSentence1.get(i);

				if (secondverb.isVerbIsDep()) {

					verbSource = secondverb.getDepLemma();
					verbSourceId = verb.getId();
					verbObjectSource = secondverb;
				} else if (secondverb.isVerbIsGov()) {
					verbSource = secondverb.getGovLemma();
					verbSourceId = verb.getId();
					verbObjectSource = secondverb;
				}


                /*System.out.println(" ");
                System.out.println("s  "  +verbSource);
                System.out.println("t  "+ verbTarget);*/
				SemanticSentenceSimilarity semanticSentenceSimilarity = new SemanticSentenceSimilarity();
				double score = semanticSentenceSimilarity.wordSimilarity(verbSource, POS.v, verbTarget, POS.v);
				if (score >= 0.8) {
					if (currentSourceVerbID != verbSourceId || currentTargetVerbID != verbTargetId) {
                        /*String[] verbPair = new String[2];
                        verbPair[0]=verbTarget;
                        verbPair[1]=verbSource;*/
						currentSourceVerbID = verbSourceId;
						currentTargetVerbID = verbTargetId;
						//System.out.println("rel :"+verbObjectTarget.getRelation());
						//System.out.println("rel :" + verbObjectSource.getRelation());

						if (verbObjectTarget.isVerbIsDep()) {
							//System.out.println("other :"+verbObjectTarget.getGovWord());
							targetOther = verbObjectTarget.getGovWord();
						} else if (verbObjectTarget.isVerbIsGov()) {
							//System.out.println("other :"+verbObjectTarget.getDepWord());
							targetOther = verbObjectTarget.getDepWord();
						}
						if (verbObjectSource.isVerbIsDep()) {
							//System.out.println("other :"+verbObjectSource.getGovWord());
							sourceOther = verbObjectSource.getGovWord();
						} else if (verbObjectSource.isVerbIsGov()) {
							//System.out.println("other :"+verbObjectSource.getDepWord());
							sourceOther = verbObjectSource.getDepWord();
						}
						VerbPair verbPair = new VerbPair();
						verbPair.setTargetVerb(verbTarget);
						verbPair.setSourceVerb(verbSource);
						if (verbObjectTarget.getRelation().equals("neg")) {
							verbPair.setTargetVerbNegated(true);
						}
						if (verbObjectSource.getRelation().equals("neg")) {
							verbPair.setSourceVerbNegated(true);
						}
						if (ModifierUtils.getNegativeWords().contains(targetOther)) {
							verbPair.setTargetVerbNegated(true);
						}
						if (ModifierUtils.getNegativeWords().contains(sourceOther)) {
							verbPair.setSourceVerbNegated(true);
						}


						closeVerbs.add(verbPair);

					}
				}

				//System.out.println("score: "+score);

			}

		}
		Integer value = detectNegation(closeVerbs);
		return value;

	}

	public static Integer detectNegation(ArrayList<VerbPair> closeVerbs) {
		System.out.println("close verbs");
		Integer count = 0;

		for (VerbPair pair : closeVerbs) {
			System.out.println("TV :" + pair.getTargetVerb());
			System.out.println("SV :" + pair.getSourceVerb());
			System.out.println("targetNegated: " + pair.getTargetVerbNegated());
			System.out.println("sourceNegated: " + pair.getSourceVerbNegated());
			if (pair.getTargetVerbNegated()) {
				if (!pair.getSourceVerbNegated()) {
					count++;
				}
			} else {
				if (pair.getSourceVerbNegated()) {
					count++;
				}
			}

			System.out.println("");
		}
		System.out.println(count);
		return count;
	}

}
