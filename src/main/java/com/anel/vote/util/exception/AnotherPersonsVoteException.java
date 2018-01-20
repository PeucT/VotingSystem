package com.anel.vote.util.exception;

/**
 * Created by PeucT on 16.01.2018.
 */
public class AnotherPersonsVoteException extends RuntimeException {
    public AnotherPersonsVoteException(String message) {
        super(message);
    }
}
