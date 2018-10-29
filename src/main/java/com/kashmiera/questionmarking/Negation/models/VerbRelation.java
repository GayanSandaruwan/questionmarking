package com.kashmiera.questionmarking.Negation.models;

public class VerbRelation {
    private String relation;
    private String depWord;
    private String depLemma;
    private String depTag;
    private String govWord;
    private String govLemma;
    private String govTag;
    private Integer id;
    private boolean verbIsGov;
    private boolean verbIsDep;

    public boolean isVerbIsGov() {
        return verbIsGov;
    }

    public void setVerbIsGov(boolean verbIsGov) {
        this.verbIsGov = verbIsGov;
    }


    public boolean isVerbIsDep() {
        return verbIsDep;
    }

    public void setVerbIsDep(boolean verbIsDep) {
        this.verbIsDep = verbIsDep;
    }

    public String getRelation() {
        return relation;

    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }


    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getDepWord() {
        return depWord;
    }

    public void setDepWord(String depWord) {
        this.depWord = depWord;
    }

    public String getDepLemma() {
        return depLemma;
    }

    public void setDepLemma(String depLemma) {
        this.depLemma = depLemma;
    }

    public String getDepTag() {
        return depTag;
    }

    public void setDepTag(String depTag) {
        this.depTag = depTag;
    }

    public String getGovWord() {
        return govWord;
    }

    public void setGovWord(String govWord) {
        this.govWord = govWord;
    }

    public String getGovLemma() {
        return govLemma;
    }

    public void setGovLemma(String govLemma) {
        this.govLemma = govLemma;
    }

    public String getGovTag() {
        return govTag;
    }

    public void setGovTag(String govTag) {
        this.govTag = govTag;
    }
}
