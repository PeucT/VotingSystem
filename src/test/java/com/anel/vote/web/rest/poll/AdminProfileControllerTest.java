package com.anel.vote.web.rest.poll;

import com.anel.vote.TestUtil;
import com.anel.vote.model.Dish;
import com.anel.vote.model.Restaurant;
import com.anel.vote.repository.RestaurantRepo;
import com.anel.vote.service.RestaurantService;
import com.anel.vote.web.AbstractControllerTest;
import com.anel.vote.web.json.JsonUtil;
import com.anel.vote.web.rest.poll.AdminProfileController;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;

import static com.anel.vote.DishTestData.*;
import static com.anel.vote.RestaurantTestData.*;
import static com.anel.vote.RestaurantTestData.assertMatch;
import static com.anel.vote.TestUtil.userHttpBasic;
import static com.anel.vote.UserTestData.ADMIN1;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

/**
 * Created by PeucT on 17.01.2018.
 */

public class AdminProfileControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminProfileController.REST_URL + '/';

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Test
    public void testCreateRestaurant() throws Exception {
        Restaurant created = getCreated();

        ResultActions action = mockMvc.perform(post(REST_URL + "restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created))
                .with(userHttpBasic(ADMIN1)))
                .andExpect(status().isCreated());

        Restaurant returned = TestUtil.readFromJson(action, Restaurant.class);
        created.setId(returned.getId());

        assertMatchWithoutDish(restaurantRepo.findById(returned.getId()).get(), created);
        assertMatchWithoutDish(returned, created);
    }

    @Test
    public void testUpdateRestaurant() throws Exception {
        RESTAURANT1.setName("Restaurant 1 Updated");

        mockMvc.perform(put(REST_URL + "restaurants/" + RESTAURANT1_ID )
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(RESTAURANT1))
                .with(userHttpBasic(ADMIN1)));

        assertMatchWithoutDish(restaurantRepo.findById(RESTAURANT1_ID).get(), RESTAURANT1);

    }

    @Test
    public void testCreateAndDeleteRestaurant() throws Exception {
        Restaurant created = getCreated();
        ResultActions action = mockMvc.perform(post(REST_URL + "restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created))
                .with(userHttpBasic(ADMIN1)));

        Restaurant returned = TestUtil.readFromJson(action, Restaurant.class);
        created.setId(returned.getId());

        mockMvc.perform(delete(REST_URL + "restaurants/" + RESTAURANT1_ID)
                .with(userHttpBasic(ADMIN1)))
                .andExpect(status().isNoContent());

        assertMatch(restaurantRepo.findAll(new Sort(Sort.Direction.ASC, "name")), Arrays.asList(RESTAURANT2, created));
    }

    @Test
    public void testSaveDish() throws Exception {
        Dish created = getCreatedDish();
        ResultActions action = mockMvc.perform(post(REST_URL + "restaurants/" + RESTAURANT2_ID + "/dishes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created))
                .with(userHttpBasic(ADMIN1)));

        Dish returned = TestUtil.readFromJson(action, Dish.class);
        created.setId(returned.getId());

        assertMatch(restaurantService.getAllForId(LocalDate.now(), RESTAURANT2_ID).get(0), RESTAURANT2_DISH_NOW_ADDED);

    }

    @Test
    public void testUpdateDish() throws Exception {
        DISH9.setName("Triple Cheeseburger Updated");

        mockMvc.perform(put(REST_URL + "restaurants/" + RESTAURANT1_ID + "/dishes/" + DISH9_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(DISH9))
                .with(userHttpBasic(ADMIN1)));

        assertMatch(restaurantService.getAllForId(LocalDate.now(), RESTAURANT1_ID).get(0), RESTAURANT1_DISH_NOW);
    }

    @Test
    public void testDeleteDish() throws Exception {
        mockMvc.perform(delete(REST_URL + "restaurants/" + RESTAURANT1_ID + "/dishes/" + DISH9_ID)
                .with(userHttpBasic(ADMIN1)))
                .andExpect(status().isNoContent());
        RESTAURANT1_DISH_NOW.setDishes(Arrays.asList(DISH10, DISH8));

        assertMatch(restaurantService.getAllForId(LocalDate.now(), RESTAURANT1_ID).get(0), RESTAURANT1_DISH_NOW);

    }
}