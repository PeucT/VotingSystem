package com.anel.vote.service;

import com.anel.vote.model.Vote;
import com.anel.vote.repository.RestaurantRepo;
import com.anel.vote.repository.UserRepo;
import com.anel.vote.repository.VoteRepo;
import com.anel.vote.to.ResultTo;
import com.anel.vote.to.VoteTo;
import com.anel.vote.util.ValidationUtil;
import com.anel.vote.util.VoteUtil;
import com.anel.vote.util.exception.AnotherPersonsVoteException;
import com.anel.vote.util.exception.DoublePostVoting;
import com.anel.vote.util.exception.NotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static com.anel.vote.util.ValidationUtil.checkNotFound;

/**
 * Created by PeucT on 16.01.2018.
 */
@Service
public class VoteServiceImpl implements VoteService {
    @Autowired
    private VoteRepo voteRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Override
    public VoteTo getMyVote(LocalDate date, int userId) throws NotFoundException {
        Vote vote = voteRepo.getMyVote(date, userId);
        return VoteUtil.asTo(checkNotFound(vote, "{vote for user: " + userId + "}"));
    }

    @Override
    public void delete(int userId, LocalDateTime dateTime) throws NotFoundException {

        ValidationUtil.checkTimeForOperations(dateTime.toLocalTime());
        voteRepo.deleteByUserId(userId, dateTime.toLocalDate());
    }


    @Override
    @Transactional
    public VoteTo saveOrUpdate(int userId, LocalDateTime dateTime, VoteTo voteTo) throws NotFoundException{

        ValidationUtil.checkTimeForOperations(dateTime.toLocalTime());

        Vote vote = voteRepo.getMyVote(dateTime.toLocalDate(), userId);

        if (voteTo.isNew() && vote != null) {
            throw new DoublePostVoting("You have already voted today");
        }
        if (voteTo.isNew() && vote == null) {
            vote = new Vote();
        }
        if (!voteTo.isNew() && vote == null) {
            ValidationUtil.checkVoteUserBelongTo(userId, vote);
        }

        vote.setRestaurant(restaurantRepo.getOne(voteTo.getRestaurantId()));
        vote.setDate(dateTime.toLocalDate());
        vote.setTime(dateTime.toLocalTime());
        vote.setUser(userRepo.getOne(userId));

        return VoteUtil.asTo(voteRepo.save(vote));

    }

    @Override
    public List<ResultTo> getResults(LocalDate date) throws NotFoundException {
        return checkNotFound(voteRepo.getResults(date), "results");
    }
}
