package com.explotwons.api.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Township {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long townshipId;
    private String name;
    private String description;

    private String bannerImage;
    private String history;
    private String culturalSignificance;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @ElementCollection
    private List<String> images;

    @ElementCollection
    private List<String> videos;

    @Embedded
    private Demographics demographics;

    @Embedded
    private Amenities amenities;

    private Double averageRating;

    @OneToMany(mappedBy = "township", cascade = CascadeType.ALL)
    private List<Rating> ratings;

    @OneToMany(mappedBy = "township", cascade = CascadeType.ALL)
    private List<Experience> experiences;


    public Long getTownshipId() {
        return townshipId;
    }

    public void setTownshipId(Long townshipId) {
        this.townshipId = townshipId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getCulturalSignificance() {
        return culturalSignificance;
    }

    public void setCulturalSignificance(String culturalSignificance) {
        this.culturalSignificance = culturalSignificance;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getVideos() {
        return videos;
    }

    public void setVideos(List<String> videos) {
        this.videos = videos;
    }

    public Demographics getDemographics() {
        return demographics;
    }

    public void setDemographics(Demographics demographics) {
        this.demographics = demographics;
    }

    public Amenities getAmenities() {
        return amenities;
    }

    public void setAmenities(Amenities amenities) {
        this.amenities = amenities;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }


}
