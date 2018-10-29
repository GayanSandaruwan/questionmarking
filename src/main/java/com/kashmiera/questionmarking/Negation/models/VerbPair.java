package com.kashmiera.questionmarking.Negation.models;

public class VerbPair {
    private String sourceVerb;
    private String targetVerb;
    private Boolean sourceVerbNegated=false;
    private Boolean targetVerbNegated=false;
    private Boolean moreFrequentTarget = false;
    private Boolean lessFrequentTarget = false;
    private Boolean amplifierTarget =false;
    private Boolean downtonerTarget =false;
    private Boolean positiveMannerTarget=false;
    private Boolean negativeMannerTarget=false;
    private Boolean moreFrequentSource = false;
    private Boolean lessFrequentSource  = false;
    private Boolean amplifierSource  =false;
    private Boolean downtonerSource  =false;
    private Boolean positiveMannerSource =false;
    private Boolean negativeMannerSource =false;

    public String getSourceVerb() {
        return sourceVerb;
    }

    public void setSourceVerb(String sourceVerb) {
        this.sourceVerb = sourceVerb;
    }

    public String getTargetVerb() {
        return targetVerb;
    }

    public void setTargetVerb(String targetVerb) {
        this.targetVerb = targetVerb;
    }

    public Boolean getSourceVerbNegated() {
        return sourceVerbNegated;
    }

    public void setSourceVerbNegated(Boolean sourceVerbNegated) {
        this.sourceVerbNegated = sourceVerbNegated;
    }

    public Boolean getTargetVerbNegated() {
        return targetVerbNegated;
    }

    public void setTargetVerbNegated(Boolean targetVerbNegated) {
        this.targetVerbNegated = targetVerbNegated;
    }

    public void setMoreFrequentTarget(Boolean moreFrequentTarget) {
        this.moreFrequentTarget = moreFrequentTarget;
    }

    public void setLessFrequentTarget(Boolean lessFrequentTarget) {
        this.lessFrequentTarget = lessFrequentTarget;
    }

    public void setAmplifierTarget(Boolean amplifierTarget) {
        this.amplifierTarget = amplifierTarget;
    }

    public void setDowntonerTarget(Boolean downtonerTarget) {
        this.downtonerTarget = downtonerTarget;
    }

    public void setPositiveMannerTarget(Boolean positiveMannerTarget) {
        this.positiveMannerTarget = positiveMannerTarget;
    }

    public void setNegativeMannerTarget(Boolean negativeMannerTarget) {
        this.negativeMannerTarget = negativeMannerTarget;
    }

    public void setMoreFrequentSource(Boolean moreFrequentSource) {
        this.moreFrequentSource = moreFrequentSource;
    }

    public void setLessFrequentSource(Boolean lessFrequentSource) {
        this.lessFrequentSource = lessFrequentSource;
    }

    public void setAmplifierSource(Boolean amplifierSource) {
        this.amplifierSource = amplifierSource;
    }

    public void setDowntonerSource(Boolean downtonerSource) {
        this.downtonerSource = downtonerSource;
    }

    public void setPositiveMannerSource(Boolean positiveMannerSource) {
        this.positiveMannerSource = positiveMannerSource;
    }

    public void setNegativeMannerSource(Boolean negativeMannerSource) {
        this.negativeMannerSource = negativeMannerSource;
    }

    public Boolean getMoreFrequentTarget() {
        return moreFrequentTarget;
    }

    public Boolean getLessFrequentTarget() {
        return lessFrequentTarget;
    }

    public Boolean getAmplifierTarget() {
        return amplifierTarget;
    }

    public Boolean getDowntonerTarget() {
        return downtonerTarget;
    }

    public Boolean getPositiveMannerTarget() {
        return positiveMannerTarget;
    }

    public Boolean getNegativeMannerTarget() {
        return negativeMannerTarget;
    }

    public Boolean getMoreFrequentSource() {
        return moreFrequentSource;
    }

    public Boolean getLessFrequentSource() {
        return lessFrequentSource;
    }

    public Boolean getAmplifierSource() {
        return amplifierSource;
    }

    public Boolean getDowntonerSource() {
        return downtonerSource;
    }

    public Boolean getPositiveMannerSource() {
        return positiveMannerSource;
    }

    public Boolean getNegativeMannerSource() {
        return negativeMannerSource;
    }
}
