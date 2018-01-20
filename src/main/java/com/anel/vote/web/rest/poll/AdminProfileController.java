package com.anel.vote.web.rest.poll;

import com.anel.vote.AuthorizedUser;
import com.anel.vote.model.Dish;
import com.anel.vote.model.Restaurant;
import com.anel.vote.service.DishService;
import com.anel.vote.service.RestaurantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static com.anel.vote.util.ValidationUtil.assureIdConsistent;
import static com.anel.vote.util.ValidationUtil.checkNew;

/**
 * Created by PeucT on 14.01.2018.
 */
@RestController
@RequestMapping(AdminProfileController.REST_URL)
public class AdminProfileController {
    static final String REST_URL = "/rest/v1/admin";
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private DishService dishService;

    private static LocalDate getDate(LocalDate date){
        if (date == null) {
            date = LocalDate.now();
        }
        return date;
    }

    @GetMapping(value = "/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
    private List<Restaurant> getAll(){
        log.info("getRestaurantList for Admin");
        return restaurantService.getAllWithoutDish();
    }

    @PostMapping(value = "/restaurants", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> saveRestaurant(@AuthenticationPrincipal AuthorizedUser authorizedUser,
                                                     @RequestBody Restaurant restaurant) {
        int authUserId = authorizedUser.getUserTo().getId();
        log.info("saveNewRestaurant by admin {}", authUserId);
        checkNew(restaurant);
        Restaurant created = restaurantService.save(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/restaurants")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/restaurants/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> updateRestaurant(@AuthenticationPrincipal AuthorizedUser authorizedUser,
                                                           @RequestBody Restaurant restaurant,
                                                           @PathVariable("id") int restId) {
        int authUserId = authorizedUser.getUserTo().getId();
        log.info("updateRestaurant with id {} by admin {}",restId, authUserId);
        assureIdConsistent(restaurant, restId);
        restaurantService.update(restaurant);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @DeleteMapping(value = "/restaurants/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteRestaurant(@AuthenticationPrincipal AuthorizedUser authorizedUser,
                                 @PathVariable("id") int restId) {
        int authUserId = authorizedUser.getUserTo().getId();
        log.info("deleRestaurant with id {} by admin {}",restId, authUserId);
        restaurantService.delete(restId);
    }

    @PostMapping(value = "/restaurants/{restId}/dishes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> saveDish(@AuthenticationPrincipal AuthorizedUser authorizedUser,
                                               @RequestBody Dish dish,
                                               @PathVariable("restId") int restId) {
        int authUserId = authorizedUser.getUserTo().getId();
        log.info("saveNewDish by admin {}", authUserId);
        checkNew(dish);
        Dish created = dishService.save(restId, dish);
        return new ResponseEntity<Dish>(created, HttpStatus.OK);
    }

    @PutMapping(value = "/restaurants/{restId}/dishes/{dishId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> updateDish(@AuthenticationPrincipal AuthorizedUser authorizedUser,
                                                 @RequestBody Dish dish,
                                                 @PathVariable("restId") int restId,
                                                 @PathVariable("dishId") int dishId) {
        int authUserId = authorizedUser.getUserTo().getId();
        log.info("updateDish with id {} in restaurant {} by admin {}",dishId, restId, authUserId);
        assureIdConsistent(dish, dishId);
        dishService.update(restId, dish);
        return new ResponseEntity<>(dish, HttpStatus.OK);
    }

    @DeleteMapping(value = "/restaurants/{restId}/dishes/{dishId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteDish(@AuthenticationPrincipal AuthorizedUser authorizedUser,
                           @PathVariable("restId") int restId,
                           @PathVariable("dishId") int dishId) {
        int authUserId = authorizedUser.getUserTo().getId();
        log.info("deleDish with id {} in restaurant {} by admin {}",dishId, restId, authUserId);
        dishService.delete(restId, dishId);
    }
}
