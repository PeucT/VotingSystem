package com.anel.vote;

import com.anel.vote.to.VoteTo;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;

import static com.anel.vote.model.AbstractBaseEntity.START_SEQ;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 * Created by PeucT on 18.01.2018.
 */
public class VoteTestData {
    public static final int VOTE1_VOTER1_ID = START_SEQ + 18;
    public static final int VOTE1_VOTER2_ID = START_SEQ + 19;
    public static final int VOTE1_ADMIN1_ID = START_SEQ + 20;
    public static final int VOTE1_ADMIN2_ID = START_SEQ + 21;
    public static final int VOTE2_VOTER2_ID = START_SEQ + 22;
    public static final int VOTE2_ADMIN1_ID = START_SEQ + 23;
    public static final int VOTE2_ADMIN2_ID = START_SEQ + 24;

    public static final VoteTo VOTE1_VOTER1_TO = new VoteTo(VOTE1_VOTER1_ID, LocalDateTime.of(LocalDate.now().minus(Period.ofDays(1)), LocalTime.of(8,0)), 100004, "Restaurant1");
    public static final VoteTo VOTE1_VOTER2_TO = new VoteTo(VOTE1_VOTER2_ID, LocalDateTime.of(LocalDate.now().minus(Period.ofDays(1)), LocalTime.of(8,58)), 100005, "Restaurant2");
    public static final VoteTo VOTE1_ADMIN1_TO = new VoteTo(VOTE1_ADMIN1_ID, LocalDateTime.of(LocalDate.now().minus(Period.ofDays(1)), LocalTime.of(10,0)), 100004, "Restaurant1");
    public static final VoteTo VOTE1_ADMIN2_TO = new VoteTo(VOTE1_ADMIN2_ID, LocalDateTime.of(LocalDate.now().minus(Period.ofDays(1)), LocalTime.of(10,58)), 100004, "Restaurant1");
    public static final VoteTo VOTE2_VOTER2_TO = new VoteTo(VOTE2_VOTER2_ID, LocalDateTime.of(LocalDate.now(), LocalTime.of(9,15, 40)), 100005, "Restaurant2");
    public static final VoteTo VOTE2_ADMIN1_TO = new VoteTo(VOTE2_ADMIN1_ID, LocalDateTime.of(LocalDate.now(), LocalTime.of(10,12, 55)), 100005, "Restaurant2");
    public static final VoteTo VOTE2_ADMIN2_TO = new VoteTo(VOTE2_ADMIN2_ID, LocalDateTime.of(LocalDate.now(), LocalTime.of(10,45)), 100004, "Restaurant1");

    public static VoteTo getCreated(){
        return new VoteTo (LocalDateTime.of(LocalDate.now(), LocalTime.of(10,15, 40)), 100005, "Restaurant2");
    }

    public static VoteTo getUpdated(){
        return new VoteTo (VOTE2_VOTER2_ID, LocalDateTime.of(LocalDate.now(), LocalTime.of(10,15, 40)), 100004, "Restaurant1");
    }

    public static void assertMatch(VoteTo actual, VoteTo expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "dateTime");
    }

    public static void assertAllMatch(VoteTo actual, VoteTo expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
