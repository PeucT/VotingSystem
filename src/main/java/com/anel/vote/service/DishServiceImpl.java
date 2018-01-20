package com.anel.vote.service;

import com.anel.vote.model.Dish;
import com.anel.vote.model.Restaurant;
import com.anel.vote.repository.DishRepo;
import com.anel.vote.repository.RestaurantRepo;
import com.anel.vote.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.anel.vote.util.ValidationUtil.checkNew;
import static com.anel.vote.util.ValidationUtil.checkNotFoundWithId;
/**
 * Created by PeucT on 17.01.2018.
 */
@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishRepo dishRepo;

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Override
    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public Dish save(int restId, Dish dish) {
        dish.setRestaurant(restaurantRepo.getOne(restId));
        return dishRepo.save(dish);
    }

    @Override
    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public void update(int restId, Dish dish) throws NotFoundException {
        dish.setRestaurant(restaurantRepo.getOne(restId));
        checkNotFoundWithId(dishRepo.save(dish), dish.getId());
    }

    @Override
    @CacheEvict(value = "restaurants", allEntries = true)
    public void delete(int restId, int dishId) throws NotFoundException{
        dishRepo.delete(restId, dishId);
    }
}
