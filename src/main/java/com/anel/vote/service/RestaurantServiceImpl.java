package com.anel.vote.service;

import com.anel.vote.model.Restaurant;
import com.anel.vote.model.Vote;
import com.anel.vote.repository.RestaurantRepo;
import com.anel.vote.repository.VoteRepo;
import com.anel.vote.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static com.anel.vote.util.ValidationUtil.checkNew;
import static com.anel.vote.util.ValidationUtil.checkNotFoundWithId;

/**
 * Created by PeucT on 11.01.2018.
 */
@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Override
    @Cacheable("restaurants")
    public List<Restaurant> getAll(LocalDate date) {
        return restaurantRepo.findAllWithDish(date);
    }

    @Override
    public List<Restaurant> getAllForId(LocalDate date, int id) throws NotFoundException {
        return checkNotFoundWithId(restaurantRepo.findAllWithDishForId(date, id), id);
    }

    @Override
    public List<Restaurant> getAllWithoutDish() {
        return restaurantRepo.findAll();
    }

    @Override
    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public Restaurant save(Restaurant restaurant) {
        return restaurantRepo.save(restaurant);
    }

    @Override
    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public void update(Restaurant restaurant) throws NotFoundException{
        try{
            restaurantRepo.findById(restaurant.getId()).get();
        }catch (NoSuchElementException e) {
            throw new NotFoundException("not found restaurant with id:=" + restaurant.getId());
        }
        checkNotFoundWithId(restaurantRepo.save(restaurant), restaurant.getId());
    }

    @Override
    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public void delete(int restId) throws NotFoundException{
        restaurantRepo.deleteById(restId);
    }

}
