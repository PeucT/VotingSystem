package com.anel.vote.util.exception;

/**
 * Created by PeucT on 16.01.2018.
 */
public class VoteTooLateException extends RuntimeException {
    public VoteTooLateException(String message) {
        super(message);
    }
}
