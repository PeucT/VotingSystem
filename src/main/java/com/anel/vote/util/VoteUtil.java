package com.anel.vote.util;

import com.anel.vote.model.Vote;
import com.anel.vote.to.VoteTo;

/**
 * Created by PeucT on 16.01.2018.
 */
public class VoteUtil {

    public static VoteTo asTo(Vote vote) {
        return new VoteTo(vote);
    }

}
