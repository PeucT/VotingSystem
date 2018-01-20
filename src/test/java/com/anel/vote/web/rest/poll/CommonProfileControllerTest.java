package com.anel.vote.web.rest.poll;

import com.anel.vote.TestUtil;
import com.anel.vote.VoteTestData;
import com.anel.vote.model.Vote;
import com.anel.vote.repository.VoteRepo;
import com.anel.vote.service.VoteService;
import com.anel.vote.to.VoteTo;
import com.anel.vote.web.AbstractControllerTest;
import com.anel.vote.web.json.JsonUtil;
import com.anel.vote.web.rest.poll.CommonProfileController;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;


import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;

import static com.anel.vote.RestaurantTestData.*;
import static com.anel.vote.ResultsTestData.*;
import static com.anel.vote.TestUtil.*;
import static com.anel.vote.UserTestData.*;
import static com.anel.vote.VoteTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by PeucT on 18.01.2018.
 */
public class CommonProfileControllerTest extends AbstractControllerTest {

    private static final String REST_URL = CommonProfileController.REST_URL + '/';

    @Autowired
    private VoteService voteService;

    @Autowired
    private VoteRepo voteRepo;

    //Get All restaurants with dishes
    @Test
    public void testGetNow() throws Exception {
        mockMvc.perform(get(REST_URL + "restaurants")
                .with(userHttpBasic(VOTER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(Arrays.asList(RESTAURANT2_DISH_NOW, RESTAURANT1_DISH_NOW)));

    }

    //Get All restaurants with yesterday's dishes menu
    @Test
    public void testGetYestedray() throws Exception {
        mockMvc.perform(get(REST_URL + "restaurants?date=" + LocalDate.now().minus(Period.ofDays(1)))
                .with(userHttpBasic(VOTER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(Arrays.asList(RESTAURANT1_DISH_YESTERDAY, RESTAURANT2_DISH_YESTERDAY)));
    }

    // Get today's vote for VOTER2
    @Test
    public void testVoteGetNow() throws Exception {
        mockMvc.perform(get(REST_URL + "vote")
                .with(userHttpBasic(VOTER2)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(VOTE2_VOTER2_TO));
    }

    @Test
    public void testVoteGetYesterday() throws Exception {
        mockMvc.perform(get(REST_URL + "vote?date=" + LocalDate.now().minus(Period.ofDays(1)))
                .with(userHttpBasic(VOTER2)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(VOTE1_VOTER2_TO));
    }




    // Тест сохранения голоса (не забываем, что время голосования проставляется и проверяется непосредственно на сервере)
    @Test
    public void testCreate() throws Exception {
        VoteTo created = VoteTestData.getCreated();

        ResultActions action = mockMvc.perform(post(REST_URL + "vote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created))
                .with(userHttpBasic(VOTER1)))
                .andExpect(status().isCreated());

        VoteTo returned = TestUtil.readFromJson(action, VoteTo.class);
        created.setId(returned.getId());

        assertMatch(returned, created);
        assertMatch(voteService.getMyVote(created.getDateTime().toLocalDate(), VOTER1.getId()), created);
    }


    @Test
    public void testUpdate() throws Exception {
        VoteTo created = getUpdated();

        ResultActions action = mockMvc.perform(put(REST_URL + "vote/" + VOTE2_VOTER2_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created))
                .with(userHttpBasic(VOTER2)))
                .andExpect(status().isOk());

        assertMatch(voteService.getMyVote(created.getDateTime().toLocalDate(), VOTER2.getId()), created);
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + "vote")
                .with(userHttpBasic(VOTER2)))
                .andExpect(status().isNoContent());

        assertThat(voteRepo.getMyVote(LocalDate.now(), VOTER2_ID)).isEqualTo(null);

    }

    @Test
    public void testGetResults() throws Exception {
        mockMvc.perform(get(REST_URL + "results")
                .with(userHttpBasic(VOTER2)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonArray(RESULTS_REST2_NOW, RESULTS_REST1_NOW));
    }




}
