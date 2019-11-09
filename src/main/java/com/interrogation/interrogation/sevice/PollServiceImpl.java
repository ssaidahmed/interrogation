package com.interrogation.interrogation.sevice;

import com.interrogation.interrogation.model.Poll;
import com.interrogation.interrogation.repository.PollRepositoryImpl;
import com.interrogation.interrogation.util.exceptions.NotFoundException;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import static com.interrogation.interrogation.util.ValidationUtil.checkNotFoundWithId;
import java.util.List;

@Service
@CacheConfig(cacheNames = "poll")
public class PollServiceImpl implements PollService {

    private PollRepositoryImpl pollRepository;

    public PollServiceImpl(PollRepositoryImpl pollRepository) {
        this.pollRepository = pollRepository;
    }

    @Override
    @CacheEvict(value = "poll", allEntries = true)
    public Poll save(Poll poll) {
        Assert.notNull(poll, "poll must not be null");
        return pollRepository.save(poll);
    }

    @Override
    @CacheEvict(value = "poll", allEntries = true)
    public void update(Poll poll) {
        Assert.notNull(poll, "poll must not be null");
        checkNotFoundWithId(pollRepository.save(poll), poll.getId());

    }

    @Override
    @CacheEvict(value = "users", allEntries = true)
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(pollRepository.delete(id), id);

    }

    @Override
    public Poll get(int id) {
        return checkNotFoundWithId(pollRepository.get(id), id);
    }

    @Override
    @Cacheable("poll")
    public List<Poll> getAll(String display) {
        return pollRepository.getAll(display);
    }
}
