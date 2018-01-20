package com.anel.vote.service;

import com.anel.vote.model.Restaurant;
import com.anel.vote.model.Vote;
import com.anel.vote.util.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by PeucT on 14.01.2018.
 */
public interface RestaurantService {
    List<Restaurant> getAll(LocalDate date);

    List<Restaurant> getAllForId(LocalDate date, int id) throws NotFoundException;

    List<Restaurant> getAllWithoutDish();

    Restaurant save(Restaurant restaurant);

    void update(Restaurant restaurant) throws NotFoundException;

    void delete(int restId) throws NotFoundException;
}
