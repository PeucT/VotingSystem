package com.anel.vote.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@NamedEntityGraph(name = Restaurant.GRAPH_WITH_DISHES, attributeNodes =
        {
                @NamedAttributeNode("dishes")
        })
@Table(name = "restaurants")
public class Restaurant extends AbstractNamedEntity{

    public static final String GRAPH_WITH_DISHES = "Restaurant.withDishes";

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(min = 2, max = 120)
    private String description;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("name ASC ")
    @JsonManagedReference(value = "restaurant-dishes")
    private List<Dish> dishes;

    public Restaurant(){
    }

    public Restaurant(String name,String description) {
        this(null, name, description);
    }

    public Restaurant(Integer id, String name, String description) {
        super(id, name);
        this.description = description;
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
                ", description='" + description +
                '}';
    }
}
