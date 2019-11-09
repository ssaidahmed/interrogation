package com.interrogation.interrogation.repository;

import com.interrogation.interrogation.model.Poll;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


import java.util.List;
@Repository
@Transactional(readOnly = true)
public class PollRepositoryImpl implements PollRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Poll save(Poll poll) {

        if(poll.isNew()){
            em.persist(poll);
            return poll;
        }else {
            return em.merge(poll);
        }

    }

    @Override
    @Transactional
    public boolean delete(int id) {
         return em.createQuery("delete from Poll p where p.id=:id").setParameter("id", id).executeUpdate() != 0;

    }

    @Override
    public Poll get(int id) {
//        return em.find(Poll.class, id);
        Poll poll = null;
        try {
            poll =  em.createQuery("select p from Poll p left join fetch p.questions q where p.id =:id order by q.display asc", Poll.class).setParameter("id", id).getSingleResult();
        }catch (NoResultException ignore){

        }
        if(poll == null){
            return null;
        }
        return poll;

    }

    @Override
    public List<Poll> getAll(String displayOrder) {

        String sql;
        if("date".equals(displayOrder)){
            sql = "SELECT p FROM Poll p ORDER BY p.startDate DESC";
        }else if("title".equals(displayOrder)) {
            sql = "SELECT p FROM Poll p  ORDER BY p.title DESC";
        }else if("action".equals(displayOrder)){
            sql = "SELECT p FROM Poll p  WHERE p.activity IS TRUE";
        }else {
            sql = "FROM Poll";
        }

        Query query = em.createQuery(sql, Poll.class);
        query.setFirstResult(0);
        query.setMaxResults(10);
        return query.getResultList();
    }
}
