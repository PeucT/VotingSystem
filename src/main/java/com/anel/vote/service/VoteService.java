package com.anel.vote.service;

import com.anel.vote.model.Vote;
import com.anel.vote.to.ResultTo;
import com.anel.vote.to.VoteTo;
import com.anel.vote.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by PeucT on 16.01.2018.
 */
public interface VoteService {
    VoteTo getMyVote(LocalDate date, int userId) throws NotFoundException;

    void delete(int userId, LocalDateTime dateTime) throws NotFoundException;

    VoteTo saveOrUpdate(int userId, LocalDateTime dateTime, VoteTo voteTo) throws NotFoundException;

    List<ResultTo> getResults(LocalDate date) throws NotFoundException;
}
