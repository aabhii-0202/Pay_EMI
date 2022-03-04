package com.mediustechnologies.payemi.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetHelpQuestionAnswerDTO {

    @SerializedName("question")
    @Expose
    String question;

    @SerializedName("answer")
    @Expose
    String answer;

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
