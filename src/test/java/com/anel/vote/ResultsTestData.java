package com.anel.vote;

import com.anel.vote.to.ResultTo;

import static com.anel.vote.RestaurantTestData.*;


/**
 * Created by PeucT on 18.01.2018.
 */
public class ResultsTestData {
    public static final ResultTo RESULTS_REST1_NOW = new ResultTo();
    public static final ResultTo RESULTS_REST2_NOW = new ResultTo();

    static {
        RESULTS_REST1_NOW.setCnt(1L);
        RESULTS_REST1_NOW.setName(RESTAURANT1.getName());
        RESULTS_REST1_NOW.setId(RESTAURANT1_ID);

        RESULTS_REST2_NOW.setCnt(2L);
        RESULTS_REST2_NOW.setName(RESTAURANT2.getName());
        RESULTS_REST2_NOW.setId(RESTAURANT2_ID);
    }
}
