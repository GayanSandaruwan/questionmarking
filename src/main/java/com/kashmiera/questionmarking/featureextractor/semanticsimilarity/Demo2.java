package com.kashmiera.questionmarking.featureextractor.semanticsimilarity;

import edu.cmu.lti.lexical_db.ILexicalDatabase;
import edu.cmu.lti.lexical_db.NictWordNet;
import edu.cmu.lti.ws4j.Relatedness;
import edu.cmu.lti.ws4j.RelatednessCalculator;
import edu.cmu.lti.ws4j.impl.HirstStOnge;
import edu.cmu.lti.ws4j.impl.JiangConrath;
import edu.cmu.lti.ws4j.impl.LeacockChodorow;
import edu.cmu.lti.ws4j.impl.Lesk;
import edu.cmu.lti.ws4j.impl.Lin;
import edu.cmu.lti.ws4j.impl.Path;
import edu.cmu.lti.ws4j.impl.Resnik;
import edu.cmu.lti.ws4j.impl.WuPalmer;
import edu.cmu.lti.ws4j.util.WS4JConfiguration;
import edu.cmu.lti.jawjaw.pobj.POS;
import edu.cmu.lti.lexical_db.data.Concept;

import java.util.List;

public class Demo2 {
    static ILexicalDatabase db = new NictWordNet();
    static RelatednessCalculator rc = new WuPalmer(db);
    public static double wordSimilarity(String word1, POS posWord1, String word2, POS posWord2) {
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
            System.out.println("s1 : "+synsets1.toString());
            System.out.println("s2 : "+synsets2.toString());
            System.out.println("Similarity score of " + word1 + " & " + word2 + " : " + maxScore);
        } catch (Exception e) {
            System.out.println(("Exception : "+ e));
        }
        return maxScore;
    }

    public static void main(String[] args) {
        String txt1 = "dog";
        String txt2 = "table";
        wordSimilarity(txt1,POS.n,txt2,POS.n);

    }

}
