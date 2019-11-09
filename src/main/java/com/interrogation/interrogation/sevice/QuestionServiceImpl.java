package com.interrogation.interrogation.sevice;
import com.interrogation.interrogation.model.Question;
import com.interrogation.interrogation.repository.QuestionRepositoryImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.interrogation.interrogation.util.ValidationUtil.checkNotFoundWithId;

@Service
public class QuestionServiceImpl implements QuestionService {


    private QuestionRepositoryImpl questionRepository;

    public QuestionServiceImpl(QuestionRepositoryImpl questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Question save(Question question, int poll_id) {
        Assert.notNull(question, "poll must not be null");
        return questionRepository.save(question, poll_id);
    }

    @Override
    public void update(Question question, int poll_id) {
        checkNotFoundWithId(questionRepository.save(question, poll_id), question.getId());
    }

    @Override
    public void delete(int id, int poll_id) {
        checkNotFoundWithId(questionRepository.delete(id, poll_id), id);
        }

    @Override
    public Question get(int id, int poll_id) {

        return checkNotFoundWithId(questionRepository.get(id, poll_id), id);
    }

    @Override
    public List<Question> getAll(int poll_id) {
        return questionRepository.getAll(poll_id);
    }
}
