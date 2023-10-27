package com.explotwons.api.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "experiences")
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long experienceId;

    private String name;
    private String description;
    private String category;

    private String bannerImage;

    private Double averageRating;

    @ElementCollection
    @CollectionTable(name = "experience_images", joinColumns = @JoinColumn(name = "experience_id"))
    @Column(name = "image_url")
    private List<String> images;

    @ElementCollection
    @CollectionTable(name = "experience_videos", joinColumns = @JoinColumn(name = "experience_id"))
    @Column(name = "video_url")
    private List<String> videos;

    private String duration;
    private String location;
    private Double latitude;
    private Double longitude;
    private Double price;
    private String availability;
    private String bookingOptions;
    private String ageRestrictions;
    private String skillLevel;
    private String physicalRequirements;
    private String accessibilityInformation;

    @ManyToOne
    @JoinColumn(name = "township_id")
    private Township township;

    @OneToMany(mappedBy = "experience", cascade = CascadeType.ALL)
    private List<ExperienceRating> ratings;


    public Long getExperienceId() {
        return experienceId;
    }

    public void setExperienceId(Long experienceId) {
        this.experienceId = experienceId;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getBookingOptions() {
        return bookingOptions;
    }

    public void setBookingOptions(String bookingOptions) {
        this.bookingOptions = bookingOptions;
    }

    public String getAgeRestrictions() {
        return ageRestrictions;
    }

    public void setAgeRestrictions(String ageRestrictions) {
        this.ageRestrictions = ageRestrictions;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }

    public String getPhysicalRequirements() {
        return physicalRequirements;
    }

    public void setPhysicalRequirements(String physicalRequirements) {
        this.physicalRequirements = physicalRequirements;
    }

    public String getAccessibilityInformation() {
        return accessibilityInformation;
    }

    public void setAccessibilityInformation(String accessibilityInformation) {
        this.accessibilityInformation = accessibilityInformation;
    }

    public Township getTownship() {
        return township;
    }

    public void setTownship(Township township) {
        this.township = township;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public List<ExperienceRating> getRatings() {
        return ratings;
    }

    public void setRatings(List<ExperienceRating> ratings) {
        this.ratings = ratings;
    }
}
