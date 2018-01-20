package com.anel.vote;

import com.anel.vote.model.Role;
import com.anel.vote.model.User;

import java.util.Arrays;

import static com.anel.vote.model.AbstractBaseEntity.START_SEQ;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by PeucT on 18.01.2018.
 */
public class UserTestData {
    public static final int VOTER1_ID = START_SEQ;
    public static final int VOTER2_ID = START_SEQ + 1;
    public static final int ADMIN1_ID = START_SEQ + 2;
    public static final int ADMIN2_ID = START_SEQ + 3;

    public static final User VOTER1 = new User(VOTER1_ID, "Иванов Иван Иванович", "Voter1", "12345", Role.ROLE_USER);
    public static final User VOTER2 = new User(VOTER2_ID, "Ivanov Ivan Ivanovitch", "Voter2", "12345", Role.ROLE_USER);
    public static final User ADMIN1 = new User(ADMIN1_ID, "Петров Петр Петрович", "Admin1", "admin", Role.ROLE_ADMIN, Role.ROLE_USER);
    public static final User ADMIN2 = new User(ADMIN2_ID, "Petrov Petr Petrovitch", "Admin2", "admin", Role.ROLE_ADMIN, Role.ROLE_USER);

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "password");
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered","password").isEqualTo(expected);
    }

    /*public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "meals");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered", "meals").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(User... expected) {
        return content().json(writeIgnoreProps(Arrays.asList(expected), "registered"));
    }

    public static ResultMatcher contentJson(User expected) {
        return content().json(writeIgnoreProps(expected, "registered"));
    }*/
}
