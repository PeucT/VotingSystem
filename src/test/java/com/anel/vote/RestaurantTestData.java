package com.anel.vote;

import com.anel.vote.model.Restaurant;

import java.util.Arrays;

import static com.anel.vote.DishTestData.*;
import static com.anel.vote.model.AbstractBaseEntity.START_SEQ;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by PeucT on 18.01.2018.
 */
public class RestaurantTestData {
    public static final int RESTAURANT1_ID = START_SEQ + 4;
    public static final int RESTAURANT2_ID = START_SEQ + 5;

    public static final Restaurant RESTAURANT1 = new Restaurant(RESTAURANT1_ID, "Restaurant1", "Restaurant 1 description");
    public static final Restaurant RESTAURANT2 = new Restaurant(RESTAURANT2_ID, "Restaurant2", "Restaurant 2 description");

    public static final Restaurant RESTAURANT1_DISH_NOW = new Restaurant(RESTAURANT1_ID, "Restaurant1", "Restaurant 1 description");
    public static final Restaurant RESTAURANT2_DISH_NOW = new Restaurant(RESTAURANT2_ID, "Restaurant2", "Restaurant 2 description");

    public static final Restaurant RESTAURANT2_WRONG_DISH_NOW = new Restaurant(RESTAURANT2_ID, "Restaurant2", "Restaurant 2 description");
    public static final Restaurant RESTAURANT2_DISH_NOW_ADDED = new Restaurant(RESTAURANT2_ID, "Restaurant2", "Restaurant 2 description");

    public static final Restaurant RESTAURANT1_DISH_YESTERDAY = new Restaurant(RESTAURANT1_ID, "Restaurant1", "Restaurant 1 description");
    public static final Restaurant RESTAURANT2_DISH_YESTERDAY = new Restaurant(RESTAURANT2_ID, "Restaurant2", "Restaurant 2 description");

    static {
        /*RESTAURANT1_DISH_NOW.setDishes(Arrays.asList(DISH8, DISH9, DISH10));*/
        RESTAURANT1_DISH_NOW.setDishes(Arrays.asList(DISH10, DISH9, DISH8));
        RESTAURANT2_DISH_NOW.setDishes(Arrays.asList(DISH11, DISH12));

        RESTAURANT2_WRONG_DISH_NOW.setDishes(Arrays.asList(DISH11, DISH5));
        RESTAURANT2_DISH_NOW_ADDED.setDishes(Arrays.asList(DISH11, DISH12, DISH_ADDED));

        RESTAURANT1_DISH_YESTERDAY.setDishes(Arrays.asList(DISH1, DISH2, DISH3, DISH4));
        RESTAURANT2_DISH_YESTERDAY.setDishes(Arrays.asList(DISH5, DISH6, DISH7));

    }

    public static Restaurant getCreated(){
        return new Restaurant ("Restaurant3", "Restaurant 3 description");
    }



    public static void assertMatch(Restaurant actual, Restaurant expected) {
        /*assertThat(actual).isEqualTo(expected);*/
        assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
        //assertThat(actual).isEqualToIgnoringGivenFields(expected, "dishes");
    }

    public static void assertMatchWithoutDish(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual)./*usingElementComparatorIgnoringFields("dish").*/isEqualTo(expected);
    }
}
