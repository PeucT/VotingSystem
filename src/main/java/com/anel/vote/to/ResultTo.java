package com.anel.vote.to;

/**
 * Created by PeucT on 19.01.2018.
 */
public class ResultTo {
    private Integer id;
    private String name;
    private Long cnt;

    public ResultTo() {
    }

    public ResultTo(Integer id, String name, Long cnt) {
        this.id = id;
        this.name = name;
        this.cnt = cnt;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getCnt() {
        return cnt;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCnt(Long cnt) {
        this.cnt = cnt;
    }

    @Override
    public String toString() {
        return "ResultTo{" +
                "restaurantId=" + id +
                ", name='" + name + '\'' +
                ", votes=" + cnt +
                '}';
    }
}
