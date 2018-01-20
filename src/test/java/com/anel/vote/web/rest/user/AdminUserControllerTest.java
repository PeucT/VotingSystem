package com.anel.vote.web.rest.user;

import com.anel.vote.TestUtil;
import com.anel.vote.model.Role;
import com.anel.vote.model.User;
import com.anel.vote.service.UserService;
import com.anel.vote.web.AbstractControllerTest;
import com.anel.vote.web.json.JsonUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static com.anel.vote.TestUtil.userHttpBasic;
import static com.anel.vote.UserTestData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by PeucT on 19.01.2018.
 */

public class AdminUserControllerTest extends AbstractControllerTest {
    private static final String REST_URL = AdminUserController.REST_URL + "/";

    @Autowired
    private UserService userService;

    @Test
    public void testGet() throws Exception {
        ResultActions action = mockMvc.perform(get(REST_URL + VOTER1_ID)
                .with(userHttpBasic(ADMIN1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        User returned = TestUtil.readFromJson(action, User.class);
        assertMatch(VOTER1, returned);
    }



    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + VOTER1_ID)
                .with(userHttpBasic(ADMIN1)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertMatch(userService.getAll(), VOTER2, ADMIN2, ADMIN1);

    }

    @Test
    public void testGetUnauth() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetForbidden() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(VOTER1)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testUpdate() throws Exception {
        VOTER1.setName("UpdatedName");
        VOTER1.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        mockMvc.perform(put(REST_URL + VOTER1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN1))
                .content(JsonUtil.writeValue(VOTER1)))
                .andDo(print())
                .andExpect(status().isOk());

        assertMatch(userService.get(VOTER1_ID), VOTER1);
    }

    @Test
    public void testCreate() throws Exception {
        User expected = new User(null, "New", "newAdmin", "newPass", Role.ROLE_USER, Role.ROLE_ADMIN);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN1))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        User returned = TestUtil.readFromJson(action, User.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);

        //sorted by NAME
        assertMatch(userService.getAll(), VOTER2, expected, ADMIN2, VOTER1, ADMIN1);
    }
}