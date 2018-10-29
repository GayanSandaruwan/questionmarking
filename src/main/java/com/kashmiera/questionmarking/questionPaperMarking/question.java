package com.kashmiera.questionmarking.questionPaperMarking;

public class question {

    private int queId;
    private int paperId;
    private String teachersAnswer;
    private float contentMark;
    private float markPerKeyword;
    private String keywords;

    public void setQuestionId(int qId){
        this.queId = qId;
    }
    public int getQuestionId(){
        return this.queId;
    }

    public void setPaperId(int pId){
        this.paperId = pId;
    }
    public int getPaperId(){
        return this.paperId;
    }

    public void setTeacherAnswer(String ta){
        this.teachersAnswer = ta;
    }
    public String getTeacherAnswer(){
        return this.teachersAnswer;
    }

    public void setContentMark(float cmk){
        this.contentMark = cmk;
    }
    public float getContentMark(){
        return this.contentMark;
    }

    public void setMarkPerKeyword(float mpk){
        this.markPerKeyword = mpk;
    }
    public float getMarkPerKeyword(){
        return this.markPerKeyword;
    }

    public void setKeywords(String k){
        this.keywords = k;
    }
    public String getKeywords(){
        return this.keywords;
    }

}
