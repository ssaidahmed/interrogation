package com.interrogation.interrogation.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "Poll")
@Table(name = "poll")
public class Poll extends BaseEntity{



    @NotNull
    private String title;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate startDate;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate endDate;

    @NotNull
    private boolean activity;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "poll")
    @JsonManagedReference
   // @OrderBy("display ASC")
    private List<Question> questions;

    public Poll() { }

    public Poll(@NotNull String title, @NotNull LocalDate startDate, @NotNull LocalDate endDate, @NotNull boolean activity) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.activity = activity;
    }

    public Poll(Integer id, @NotNull String title, @NotNull LocalDate startDate, @NotNull LocalDate endDate, @NotNull boolean activity) {
        super(id);
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.activity = activity;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public boolean isActivity() {
        return activity;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setActivity(boolean activity) {
        this.activity = activity;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    @Override
    public String toString() {
        return "Poll{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", activity=" + activity +
                '}';
    }
}
