package com.interrogation.interrogation.repository;

import com.interrogation.interrogation.model.Poll;
import com.interrogation.interrogation.model.Question;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class QuestionRepositoryImpl implements QuestionRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Question save(Question question, int poll_id) {
        if(!question.isNew() && get(question.getId(), poll_id) == null){
            return null;
        }
        question.setPoll(em.getReference(Poll.class, poll_id));
        if(question.isNew()){
            em.persist(question);
            return question;
        }else {
            return em.merge(question);
        }


    }

    @Override
    @Transactional
    public boolean delete(int id, int poll_id) {
        Query query = em.createQuery("delete from Question q where q.id=:id and q.poll.id=:poll_id").setParameter("id", id).setParameter("poll_id", poll_id);
        return query.executeUpdate() != 0;


    }

    @Override
    public Question get(int id, int poll_id) {
        Question question = em.find(Question.class, id);
        return question != null && question.getPoll().getId() == poll_id ? question : null;

    }

    @Override
    public List<Question> getAll(int poll_id) {

        return em.createQuery("SELECT q FROM Question q WHERE q.poll.id=:poll_id ORDER BY q.display asc", Question.class).setParameter("poll_id", poll_id).getResultList();

    }
}
