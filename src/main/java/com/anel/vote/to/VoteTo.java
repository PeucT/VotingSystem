package com.anel.vote.to;

import com.anel.vote.model.Vote;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Created by PeucT on 16.01.2018.
 */
public class VoteTo extends BaseTo {

    private LocalDateTime dateTime;

    @NotNull
    private Integer restaurantId;

    @NotBlank
    private String restaurantName;

    public VoteTo(){}

    public VoteTo(LocalDateTime dateTime, Integer restaurantId, String restaurantName){
        this(null, dateTime, restaurantId, restaurantName);
    }

    public VoteTo(Integer id, LocalDateTime dateTime, Integer restaurantId, String restaurantName) {
        super(id);
        this.dateTime = dateTime;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
    }

    public VoteTo(Vote vote) {
        super(vote.getId());
        this.dateTime = LocalDateTime.of(vote.getDate(), vote.getTime());
        this.restaurantId = vote.getRestaurant().getId();
        this.restaurantName = vote.getRestaurant().getName();
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VoteTo voteTo = (VoteTo) o;

        if (!dateTime.equals(voteTo.dateTime)) return false;
        if (!restaurantId.equals(voteTo.restaurantId)) return false;
        return restaurantName != null ? restaurantName.equals(voteTo.restaurantName) : voteTo.restaurantName == null;
    }

    @Override
    public int hashCode() {
        int result = dateTime.hashCode();
        result = 31 * result + restaurantId.hashCode();
        result = 31 * result + (restaurantName != null ? restaurantName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "VoteTo{" +
                "dateTime=" + dateTime +
                ", restaurantId=" + restaurantId +
                ", restaurantName='" + restaurantName + '\'' +
                '}';
    }
}
