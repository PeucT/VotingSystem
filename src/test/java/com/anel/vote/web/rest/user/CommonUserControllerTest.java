package com.anel.vote.web.rest.user;

import com.anel.vote.TestUtil;
import com.anel.vote.model.User;
import com.anel.vote.service.UserService;
import com.anel.vote.to.UserTo;
import com.anel.vote.util.UserUtil;
import com.anel.vote.web.AbstractControllerTest;
import com.anel.vote.web.json.JsonUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static com.anel.vote.TestUtil.contentJson;
import static com.anel.vote.TestUtil.contentJsonArray;
import static com.anel.vote.TestUtil.userHttpBasic;
import static com.anel.vote.UserTestData.*;
import static com.anel.vote.UserTestData.assertMatch;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by PeucT on 19.01.2018.
 */
public class CommonUserControllerTest extends AbstractControllerTest {

    @Autowired
    private UserService userService;

    private static final String REST_URL = CommonUserController.REST_URL + '/';

    @Test
    public void testGet() throws Exception {
        ResultActions action = mockMvc.perform(get(REST_URL)
                        .with(userHttpBasic(VOTER1)))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        User returned = TestUtil.readFromJson(action, User.class);
        assertMatch(VOTER1, returned);
    }

    @Test
    public void testGetUnauth() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL)
                .with(userHttpBasic(VOTER1)))
                .andExpect(status().isNoContent());

        assertMatch(userService.getAll(), VOTER2, ADMIN2, ADMIN1);

    }

    @Test
    public void testUpdate() throws Exception {
        UserTo updatedTo = new UserTo(null, "Voter1 new name", "Voter1 new nickname", "newPassword");

        mockMvc.perform(put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(VOTER1))
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isOk());

        assertMatch(userService.getByNickname("Voter1 new nickname"), UserUtil.updateFromTo(VOTER1,updatedTo));
    }
}