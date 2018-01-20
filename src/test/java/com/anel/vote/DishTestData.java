package com.anel.vote;

import com.anel.vote.model.Dish;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;

import static com.anel.vote.RestaurantTestData.RESTAURANT1;
import static com.anel.vote.model.AbstractBaseEntity.START_SEQ;

/**
 * Created by PeucT on 18.01.2018.
 */
public class DishTestData {
    public static final int DISH1_ID = START_SEQ + 6;
    public static final int DISH2_ID = START_SEQ + 7;
    public static final int DISH3_ID = START_SEQ + 8;
    public static final int DISH4_ID = START_SEQ + 9;
    public static final int DISH5_ID = START_SEQ + 10;
    public static final int DISH6_ID = START_SEQ + 11;
    public static final int DISH7_ID = START_SEQ + 12;
    public static final int DISH8_ID = START_SEQ + 13;
    public static final int DISH9_ID = START_SEQ + 14;
    public static final int DISH10_ID = START_SEQ + 15;
    public static final int DISH11_ID = START_SEQ + 16;
    public static final int DISH12_ID = START_SEQ + 17;

    public static final Dish DISH1 = new Dish(DISH1_ID, "Бигмак", LocalDate.now().minus(Period.ofDays(1)), new BigDecimal("10.25"));
    public static final Dish DISH2 = new Dish(DISH2_ID, "Hamburger", LocalDate.now().minus(Period.ofDays(1)), new BigDecimal("17"));
    public static final Dish DISH3 = new Dish(DISH3_ID, "Кола лайт 200мл.", LocalDate.now().minus(Period.ofDays(1)), new BigDecimal("30.99"));
    public static final Dish DISH4 = new Dish(DISH4_ID, "Вафельный рожок", LocalDate.now().minus(Period.ofDays(1)), new BigDecimal("22"));
    public static final Dish DISH5 = new Dish(DISH5_ID, "Маргарита", LocalDate.now().minus(Period.ofDays(1)), new BigDecimal("24"));
    public static final Dish DISH6 = new Dish(DISH6_ID, "Пепперони", LocalDate.now().minus(Period.ofDays(1)), new BigDecimal("15.1"));
    public static final Dish DISH7 = new Dish(DISH7_ID, "Овощи гриль", LocalDate.now().minus(Period.ofDays(1)), new BigDecimal("19"));
    public static final Dish DISH8 = new Dish(DISH8_ID, "Биг тейсти", LocalDate.now(), new BigDecimal("14.02"));
    public static final Dish DISH9 = new Dish(DISH9_ID, "Triple Cheeseburger", LocalDate.now(), new BigDecimal("64.00"));
    public static final Dish DISH10 = new Dish(DISH10_ID, "Pure beef", LocalDate.now(), new BigDecimal("156.32"));
    public static final Dish DISH11 = new Dish(DISH11_ID, "Sicilian Cheese Pizza", LocalDate.now(), new BigDecimal("12.99"));
    public static final Dish DISH12 = new Dish(DISH12_ID, "Куриная", LocalDate.now(), new BigDecimal("4.05"));

    public static final Dish DISH_ADDED = new Dish(START_SEQ + 25, "Тест блюдо", LocalDate.now(), new BigDecimal("15.15"));

    {
        DISH_ADDED.setRestaurant(RESTAURANT1);
    }
    public static Dish getCreatedDish(){
        return new Dish("Тест блюдо", LocalDate.now(), new BigDecimal("15.15"));
    }

}
