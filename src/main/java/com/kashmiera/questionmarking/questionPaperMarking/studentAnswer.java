package com.kashmiera.questionmarking.questionPaperMarking;

public class studentAnswer {

    private int queId;
    private int stdId;
    private int stdAnsId;
    private String stdAnswer;
    private String teachersAnswer;
    private float contentMark;
    private float score;

    public void setQuestionId(int qId){
        this.queId = qId;
    }
    public int getQuestionId(){
        return this.queId;
    }

    public void setStudentId(int sId){
        this.stdId = sId;
    }
    public int getStudentId(){
        return this.stdId;
    }

    public void setStudentAnswerId(int s){
        this.stdAnsId = s;
    }
    public int getStudentAnswerId(){
        return this.stdAnsId;
    }

    public void setStudentAnswer(String stdAns){
        this.stdAnswer = stdAns;
    }
    public String getStudentAnswer(){
        return this.stdAnswer;
    }

    public void setTeacherAnswer(String ta){
        this.teachersAnswer = ta;
    }
    public String getTeacherAnswer(){
        return this.teachersAnswer;
    }

    public void setScore(float cmk){
        this.score = cmk;
    }
    public float getScore(){
        return this.score;
    }

    public void setContentMark(float cmk){
        this.contentMark = cmk;
    }
    public float getContentMark(){
        return this.contentMark;
    }
}
