package com.kashmiera.questionmarking.questionPaperMarking;
import com.kashmiera.questionmarking.controllers.SimilarityScore;
import com.kashmiera.questionmarking.datareader.DBConnect;
import edu.stanford.nlp.pipeline.Annotation;
import com.kashmiera.questionmarking.featureextractor.cosinesimilarity.*;
import com.kashmiera.questionmarking.featureextractor.lexicalsimilarity.OverlapWordRatio;
import com.kashmiera.questionmarking.featureextractor.semanticsimilarity.SemanticSentenceSimilarity;
import com.kashmiera.questionmarking.featureextractor.sentencepropertyfeatures.SentenceLengths;
import com.kashmiera.questionmarking.utils.NLPUtils;
import sun.plugin.javascript.JSObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class questionMarking {

    Connection con = null;

    String sourceSentence = null;
    String targetSentence = null;

    public questionMarking() {

        con = DBConnect.connect();
    }

    public  SimilarityScore paperMarking(int paperId){
        SimilarityScore similarityScore = new SimilarityScore();

        try
        {
            ArrayList<question> queArray = new ArrayList<question>();
            ArrayList<studentAnswer> stdAnsArray = new ArrayList<>();

            String sql = "SELECT questionId FROM question WHERE questionPaperId = '"+ paperId +"' AND type = 'structured'";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
                question que = new question();
                int qid = rs.getInt("questionId");


                String sql1 = "SELECT structuredqId,answer,contentMark,markPerKeyword FROM structured_question WHERE structuredqId = '"+ qid +"'";
                PreparedStatement pst1 = con.prepareStatement(sql1);
                ResultSet rs1 = pst1.executeQuery();
                while(rs1.next())
                {

                    String ans = rs1.getString("answer");

                    System.out.println(ans);

                    float cMark = rs1.getFloat("contentMark");
                    int q_id = rs1.getInt("structuredqId");
                    que.setTeacherAnswer(ans);
                    que.setContentMark(cMark);
                    que.setPaperId(paperId);
                    que.setQuestionId(q_id);
                    queArray.add(que);



                    String sql2 = "SELECT studentId,studAnswerId FROM student_answer WHERE  questionId = '"+ q_id +"' AND markedStatus = '0'";
                    PreparedStatement pst2 = con.prepareStatement(sql2);
                    ResultSet rs2 = pst2.executeQuery();

                    while(rs2.next())
                    {
                        studentAnswer stdA = new studentAnswer();
                        int sId = rs2.getInt("studentId");
                        int sAnsId = rs2.getInt("studAnswerId");
                        stdA.setTeacherAnswer(ans);
                        stdA.setStudentId(sId);
                        stdA.setQuestionId(qid);
                        stdA.setStudentAnswerId(sAnsId);
                        stdA.setContentMark(cMark);

                        System.out.println(sId);

                        String sql3 = "SELECT stdanswer FROM structured_stud_answer WHERE structuredStudAnsId = '"+ sAnsId +"' ";
                        PreparedStatement pst3 = con.prepareStatement(sql3);
                        ResultSet rs3 = pst3.executeQuery();
                        while(rs3.next())
                        {
                           String stdans = rs3.getString("stdanswer");
                           stdA.setStudentAnswer(stdans);
                           stdAnsArray.add(stdA);
                           System.out.println(stdans);

                        }


                    }

                }

            }

            /* While Loop for iterating ArrayList*/


            for (studentAnswer s : stdAnsArray) {

                NLPUtils nlpUtils = new NLPUtils("tokenize,ssplit,pos");

                String sourceSentence = s.getStudentAnswer();
                String targetSentence = s.getTeacherAnswer();

                float score ;

                Annotation sourceAnnotation =nlpUtils.annotate(sourceSentence);
                Annotation targetAnnotation = nlpUtils.annotate(targetSentence);

                SemanticSentenceSimilarity semanticSentenceSimilarity = new SemanticSentenceSimilarity(sourceAnnotation,targetAnnotation,nlpUtils);
                double semanticSimilarityScore = semanticSentenceSimilarity.getAverageScore();

                OverlapWordRatio overlapWordRatio = new OverlapWordRatio();
                ArrayList<Double> overlapScores = overlapWordRatio.getOverlapScore(sourceSentence, targetSentence);


                SentenceLengths sentenceLengths = new SentenceLengths(sourceSentence, targetSentence);


                Similarity wordSimilarity = new WordSimilarity(sourceSentence,targetSentence) ;
                double scoreWordsSimilarity = wordSimilarity.similarityScore();


                Similarity nounSimilarity = new NounSimilarity(sourceAnnotation, targetAnnotation, nlpUtils) ;
                double scoreNounsSimilarity = nounSimilarity.similarityScore();


                Similarity verbSimilarity = new VerbSimilarity(sourceAnnotation, targetAnnotation, nlpUtils) ;
                double scoreVerbsSimilarity = verbSimilarity.similarityScore();


                Similarity adjectiveSimilarity = new AdjectiveSimilarity(sourceAnnotation, targetAnnotation, nlpUtils) ;
                double scoreAdjectivesSimilarity = adjectiveSimilarity.similarityScore();


                System.out.println(s.getQuestionId());
                System.out.println(s.getStudentId());
                System.out.println(s.getTeacherAnswer());
                System.out.println(s.getStudentAnswer());
                System.out.println("\nSemantic Similarity Score   : "+semanticSimilarityScore);
                System.out.println("Word overlap Scores         : " + overlapScores.toString());
                System.out.println("Sentence Length Score       : "+ sentenceLengths.getLengthScore());
                System.out.println("Word Similarity Score       : "+ scoreWordsSimilarity);
                System.out.println("Noun Similarity Score       : "+ scoreNounsSimilarity);
                System.out.println("Verb Similarity Score       : "+ scoreVerbsSimilarity);
                System.out.println("Adjective Similarity Score  : "+ scoreAdjectivesSimilarity);


                similarityScore.semanticSimilarity = ""+semanticSimilarityScore;
                similarityScore.wordOverlap = ""+overlapScores;
                similarityScore.sentenceLength = ""+sentenceLengths.getLengthScore();
                similarityScore.wordSimilarity = ""+scoreWordsSimilarity;
                similarityScore.nounSimilarity = ""+scoreNounsSimilarity;
                similarityScore.verbSimilarity = ""+scoreVerbsSimilarity;
                similarityScore.adjeectiveSimilarity = ""+scoreAdjectivesSimilarity;
            }


        }
        catch (Exception e)
        {
            System.out.println(e);

        }
        return similarityScore;
    }

    public static void main(String[] args) throws Exception {

        questionMarking am = new questionMarking();
        am.paperMarking(1);


    }

}


