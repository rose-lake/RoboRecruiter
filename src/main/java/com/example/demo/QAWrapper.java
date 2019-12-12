package com.example.demo;

import java.util.List;

public class QAWrapper {
    private List<QuestionAnswer> list;

    public QAWrapper() {
    }

    public QAWrapper(List<QuestionAnswer> list) {
        this.list = list;
    }

    public void addQA(QuestionAnswer questionAnswer) {
        this.list.add(questionAnswer);
    }

    public List<QuestionAnswer> getList() {
        return list;
    }

    public void setList(List<QuestionAnswer> list) {
        this.list = list;
    }
}
