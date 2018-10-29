package com.kashmiera.questionmarking.featureextractor.semanticsimilarity;

import edu.cmu.lti.jawjaw.pobj.POS;
import edu.cmu.lti.lexical_db.data.Concept;
import edu.cmu.lti.ws4j.Relatedness;
import edu.cmu.lti.ws4j.RelatednessCalculator;
import edu.stanford.nlp.pipeline.Annotation;
import com.kashmiera.questionmarking.utils.NLPUtils;
import edu.cmu.lti.lexical_db.ILexicalDatabase;
import edu.cmu.lti.lexical_db.NictWordNet;
import edu.cmu.lti.ws4j.impl.WuPalmer;
import edu.cmu.lti.ws4j.util.WS4JConfiguration;


import java.util.ArrayList;
import java.util.List;

public class SemanticSentenceSimilarity {

    private String sentence1 ;
    private String sentence2;

    ArrayList<String> nouns1 = new ArrayList<>();
    ArrayList<String> nouns2 = new ArrayList<>();
    ArrayList<String> verbs1 = new ArrayList<>();
    ArrayList<String> verbs2 = new ArrayList<>();

    private int nounCount=0;
    private int verbCount=0;
    private double aggregateNounDistance = 0;
    private double aggregateVerbDistance = 0;
    private Annotation annotation1 ;
    private Annotation annotation2 ;
    private boolean hasValue=false;

    NLPUtils nlpUtils;

    private static ILexicalDatabase db = new NictWordNet();
    static RelatednessCalculator rc = new WuPalmer(db);

    public SemanticSentenceSimilarity(Annotation stc1, Annotation stc2,NLPUtils nlpUtls){
        annotation1 = stc1;
        annotation2 = stc2;
        nlpUtils = nlpUtls;
    }

    public SemanticSentenceSimilarity(){

    }

    private double compute(String word1, String word2) {
        WS4JConfiguration.getInstance().setMFS(true);
        double s = new WuPalmer(db).calcRelatednessOfWords(word1, word2);
        return s;
    }

    public double wordSimilarity(String word1, POS posWord1, String word2, POS posWord2) {
        double maxScore = 0D;
        try {
            WS4JConfiguration.getInstance().setMFS(true);
            List<Concept> synsets1 = (List<Concept>) db.getAllConcepts(word1, posWord1.name());
            List<Concept> synsets2 = (List<Concept>) db.getAllConcepts(word2, posWord2.name());
            for (Concept synset1 : synsets1) {
                for (Concept synset2 : synsets2) {
                    Relatedness relatedness = rc.calcRelatednessOfSynset(synset1, synset2);
                    double score = relatedness.getScore();
                    if (score > maxScore) {
                        maxScore = score;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(("Exception : "+ e));
        }
        if(maxScore>=1){
            return 1;
        }
        return maxScore;
    }

    public void getVerbsAndNouns(){

        nouns1.addAll(nlpUtils.getNouns(annotation1));
        nouns2.addAll(nlpUtils.getNouns(annotation2));
        verbs1.addAll(nlpUtils.getVerbs(annotation1));
        verbs2.addAll(nlpUtils.getVerbs(annotation2));

    }

    public void checkSimilarity() {

        getVerbsAndNouns();
        if(nouns1.size()>0 && nouns2.size()>0) {
            hasValue=true;
            for (int i = 0; i < nouns2.size(); i++) {
                double maxdistance=0;
                for (int j = 0; j < nouns1.size(); j++) {
                    double distance;
                    if (nouns2.get(i).equals(nouns1.get(j))) {
                        distance = 1;
                    } else {

                        distance = wordSimilarity(nouns2.get(i), POS.n, nouns1.get(j), POS.n);

                    }
                    if(maxdistance<distance){
                        maxdistance=distance;
                    }
                    //compute(nouns1.get(i), nouns1.get(j));

                }
                aggregateNounDistance += maxdistance;
//                    System.out.println(nouns1.get(i) + " -  " + nouns1.get(j) + " = " + distance);


                nounCount++;

                //System.out.println(nounCount);
            }
        }
        if (verbs1.size()>0 && verbs2.size()>0) {
            hasValue=true;
            for (int i = 0; i < verbs2.size(); i++) {
                double maxdistance=0;
                for (int j = 0; j < verbs1.size(); j++) {

                    double distance;
                    if (verbs2.get(i).equals(verbs1.get(j))) {
                        distance = 1;
                    } else {
                        distance = wordSimilarity(verbs2.get(i), POS.v, verbs1.get(j), POS.v);

                        //compute(verbs1.get(i), verbs1.get(j));
                    }
                    if(maxdistance<distance){
                        maxdistance=distance;
                    }

                }
                aggregateVerbDistance += maxdistance;
                //System.out.println(verbs1.get(i) + " -  " + verbs1.get(j) + " = " + distance);
                verbCount++;
                //System.out.println(verbCount);
            }

        }
    }

    public double getAverageScore(){
        checkSimilarity();
        if(nounCount==0 && verbCount==0) {

            return 0.0;
        }else if(!hasValue){

            return 0.0;
        }

        double score = (aggregateNounDistance + aggregateVerbDistance) / (double) (nounCount + verbCount);
        /*double finalScore = score * 2;
        if(finalScore >= 1){
           return 1;
        }*/

        return score;

    }



}
