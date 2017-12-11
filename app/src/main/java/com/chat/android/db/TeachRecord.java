package com.chat.android.db;

/**
 * Created by 26241 on 2017/7/17.
 */

public class TeachRecord {

    private String title;
    private String question;
    private String answer;

    public TeachRecord(String title,String question, String answer){
        this.question = question;
        this.answer = answer;
        this.title = title;
    }

    public String getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
