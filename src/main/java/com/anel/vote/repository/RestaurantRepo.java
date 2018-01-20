package com.anel.vote.repository;

import com.anel.vote.model.Restaurant;
import com.anel.vote.to.ResultTo;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by PeucT on 13.01.2018.
 */
@Transactional(readOnly = true)
public interface RestaurantRepo extends JpaRepository<Restaurant, Integer> {

    @EntityGraph(attributePaths = {"dishes"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT r FROM Restaurant r JOIN FETCH r.dishes d WHERE d.date =?1 ORDER BY r.name ASC ")
    List<Restaurant> findAllWithDish(LocalDate date);

    @EntityGraph(attributePaths = {"dishes"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT r FROM Restaurant r JOIN FETCH r.dishes d WHERE d.date = ?1 AND r.id =?2 ORDER BY r.name ASC ")
    List<Restaurant> findAllWithDishForId(LocalDate date, int id);

}
