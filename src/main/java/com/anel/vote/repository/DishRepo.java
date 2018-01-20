package com.anel.vote.repository;

import com.anel.vote.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * Created by PeucT on 13.01.2018.
 */
public interface DishRepo extends JpaRepository<Dish, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Dish d WHERE d.restaurant.id = ?1 AND d.id = ?2")
    void delete(int restId, int dishId);
}
