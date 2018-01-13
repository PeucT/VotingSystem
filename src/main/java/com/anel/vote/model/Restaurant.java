package com.anel.vote.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Created by PeucT on 13.01.2018.
 */

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "restaurant")
@Entity
@Table(name = "restaurants")
public class Restaurant extends AbstractNamedEntity{

    // TODO: 13.01.2018 Надо бы что-то решить
    /*@Column(name = "registered", columnDefinition = "timestamp default now()")
    @NotNull
    private Date registered = new Date();*/

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(min = 2, max = 120)
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("dateTime DESC")
    private List<Dish> dishes;

    public Restaurant(){
    }

    public Restaurant(String name, Date registered, String description) {
        this(null, name, registered, description);
    }

    public Restaurant(Integer id, String name, Date registered, String description) {
        super(id, name);
        this.registered = registered;
        this.description = description;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name +
                ", dateTime=" + registered  +
                ", description='" + description + '\'' +
                '}';
    }
}
