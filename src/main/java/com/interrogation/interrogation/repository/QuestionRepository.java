package com.interrogation.interrogation.repository;

import com.interrogation.interrogation.model.Question;

import java.util.List;

public interface QuestionRepository {

    Question save(Question question, int poll_id);

    boolean delete(int id, int poll_id);

    Question get(int id, int poll_id);

    List<Question> getAll(int poll_id);
}
