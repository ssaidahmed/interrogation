package com.interrogation.interrogation.sevice;

import com.interrogation.interrogation.model.Question;

import java.util.List;

public interface QuestionService {

    Question save(Question question, int poll_id);

    void update(Question question, int poll_id);

    void delete(int id, int poll_id);

    Question get(int id, int poll_id);

    List<Question> getAll(int poll_id);

}
