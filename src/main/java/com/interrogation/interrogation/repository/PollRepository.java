package com.interrogation.interrogation.repository;

import com.interrogation.interrogation.model.Poll;

import java.util.List;

public interface PollRepository {

    Poll save(Poll poll);

    boolean delete(int id);

    Poll get(int id);

    List<Poll> getAll(String displayOrder);
}
