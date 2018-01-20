package com.anel.vote.to;

import com.anel.vote.HasId;

/**
 * Created by PeucT on 14.01.2018.
 */
public abstract class BaseTo implements HasId {
    protected Integer id;

    public BaseTo() {
    }

    public BaseTo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
