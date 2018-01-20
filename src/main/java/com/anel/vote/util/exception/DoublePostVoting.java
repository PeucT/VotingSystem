package com.anel.vote.util.exception;

/**
 * Created by PeucT on 19.01.2018.
 */
public class DoublePostVoting extends RuntimeException {
    public DoublePostVoting(String message) {
        super(message);
    }
}
