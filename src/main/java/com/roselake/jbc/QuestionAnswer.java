package com.roselake.jbc;

public class QuestionAnswer {
    private long id;

    private String question;

    private String answer;

    public QuestionAnswer() {
    }

    public QuestionAnswer(String question) {
        this.question = question;
    }

    public QuestionAnswer(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
