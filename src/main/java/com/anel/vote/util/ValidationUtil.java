package com.anel.vote.util;


import com.anel.vote.HasId;
import com.anel.vote.model.Vote;
import com.anel.vote.util.exception.AnotherPersonsVoteException;
import com.anel.vote.util.exception.NotFoundException;
import com.anel.vote.util.exception.VoteTooLateException;

import java.time.LocalTime;

public class ValidationUtil {

    private static final LocalTime deadlineTime = LocalTime.of(11,00);

    private ValidationUtil() {
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalArgumentException(bean + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(HasId bean, int id) {
//      http://stackoverflow.com/a/32728226/548473
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.getId() != id) {
            throw new IllegalArgumentException(bean + " must be with id=" + id);
        }
    }

    //  http://stackoverflow.com/a/28565320/548473
    public static Throwable getRootCause(Throwable t) {
        Throwable result = t;
        Throwable cause;

        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }

    public static void checkTimeForOperations(LocalTime time) {
        if (time.isAfter(deadlineTime)) {
            throw new VoteTooLateException("It's too late to delete vote");
        }
    }

    public static void checkVoteUserBelongTo(int userId, Vote vote) {
        if (vote.getUser().getId() != userId) {
            throw new AnotherPersonsVoteException("You are trying to change another persons vote!");
        }
    }
}