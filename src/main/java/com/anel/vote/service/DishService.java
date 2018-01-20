package com.anel.vote.service;

import com.anel.vote.model.Dish;
import com.anel.vote.util.exception.NotFoundException;

/**
 * Created by PeucT on 17.01.2018.
 */
public interface DishService {
    Dish save(int restId, Dish dish);

    void update(int restId, Dish dish) throws NotFoundException;

    void delete(int restId, int dishId) throws NotFoundException;
}
