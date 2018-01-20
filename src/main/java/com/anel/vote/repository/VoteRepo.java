package com.anel.vote.repository;

import com.anel.vote.model.Vote;
import com.anel.vote.to.ResultTo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by PeucT on 13.01.2018.
 */
public interface VoteRepo extends JpaRepository<Vote, Integer> {

    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.date = ?1 AND v.user.id=?2")
    Vote getMyVote(LocalDate date, int userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Vote v WHERE v.user.id = ?1 AND v.date = ?2")
    void deleteByUserId(int userId, LocalDate date);

    @Override
    Vote save(Vote vote);


    @Query("SELECT new com.anel.vote.to.ResultTo(r.id, r.name, count(v.id)) FROM Vote v JOIN v.restaurant r WHERE v.date = ?1 GROUP BY r.id ORDER BY count(v.id) DESC ")
    List<ResultTo> getResults(LocalDate date);
}
