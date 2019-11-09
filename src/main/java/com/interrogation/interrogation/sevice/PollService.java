package com.interrogation.interrogation.sevice;

import com.interrogation.interrogation.model.Poll;

import java.util.List;

public interface PollService {
    Poll save(Poll poll);

    void update(Poll poll);

    void delete(int id);

    Poll get(int id);
    List<Poll> getAll(String display);
}
