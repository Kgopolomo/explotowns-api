package com.explotwons.api.entity;

import javax.persistence.*;

@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "township_id")
    private Township township;

    private Long userId;
    private Integer rating;
    private String comment;

    public void setId(Long id) {
        this.id = id;
    }

    public void setTownship(Township township) {
        this.township = township;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getUserId() {
        return userId;
    }

    public String getComment() {
        return comment;
    }

}
