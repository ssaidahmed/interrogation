package com.interrogation.interrogation.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;



@Entity(name = "Question")
@Table(name = "question")
public class Question extends BaseEntity {




    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poll_id")
    @JsonBackReference
    private Poll poll;

    @NotNull
    private String questionText;

    @NotNull
    private String display;

    public Question() {
    }

    public Question(@NotNull String questionText, @NotNull String displayOrder) {
        this(null, questionText, displayOrder);
    }

    public Question(Integer id, @NotNull String questionText, @NotNull String display) {
        super(id);
        this.questionText = questionText;
        this.display = display;
    }

    public Poll getPoll() {
        return poll;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getDisplay() {
        return display;
    }


    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", questionText='" + questionText + '\'' +
                ", display='" + display + '\'' +
                '}';
    }
}
