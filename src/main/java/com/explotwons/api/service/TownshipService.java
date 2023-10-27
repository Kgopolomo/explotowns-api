package com.explotwons.api.service;

import com.explotwons.api.entity.Amenities;
import com.explotwons.api.entity.Demographics;
import com.explotwons.api.entity.Rating;
import com.explotwons.api.entity.Township;
import com.explotwons.api.repository.RatingRepository;
import com.explotwons.api.repository.TownshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TownshipService {

    @Autowired
    private TownshipRepository townshipRepository;

    @Autowired
    private RatingRepository ratingRepository;

    public List<Township> getAllTownships(String province) {
        List<Township> townships;
        if (province != null && !province.trim().isEmpty()) {
            townships = townshipRepository.findByLocationProvince(province);
        } else {
            townships = townshipRepository.findAll();
        }

        for (Township township : townships) {
            List<Rating> ratings = township.getRatings();
            if (ratings != null && !ratings.isEmpty()) {
                double totalRating = 0;
                for (Rating rating : ratings) {
                    totalRating += rating.getRating();
                }
                double averageRating = totalRating / ratings.size();
                // Assuming you have a setAverageRating method in the Township class
                township.setAverageRating(averageRating);
            }
        }
        return townships;
    }

    @Transactional
    public Township addTownship(Township township) {
        if (township == null) {
            throw new IllegalArgumentException("Township cannot be null");
        }
        return townshipRepository.save(township);
    }

    public Township getTownshipById(Long townshipId) {
        if (townshipId == null) {
            throw new IllegalArgumentException("Township ID cannot be null");
        }
        Optional<Township> optionalTownship = townshipRepository.findById(townshipId);

        if (!optionalTownship.isPresent()) {
            throw new EntityNotFoundException("No township found for the given ID");
        }

        Township township = optionalTownship.get();
        List<Rating> ratings = township.getRatings();
        if (ratings != null && !ratings.isEmpty()) {
            double totalRating = 0;
            for (Rating rating : ratings) {
                totalRating += rating.getRating();
            }
            double averageRating = totalRating / ratings.size();
            // Assuming you have a setAverageRating method in the Township class
            township.setAverageRating(averageRating);
        }
        return township;
    }

    public Township updateTownship(Long townshipId, Township township) {
        if (townshipId == null) {
            throw new IllegalArgumentException("Township ID cannot be null");
        }

        Optional<Township> optionalExistingTownship = townshipRepository.findById(townshipId);

        if (!optionalExistingTownship.isPresent()) {
            throw new EntityNotFoundException("No township found for the given ID");
        }

        Township existingTownship = optionalExistingTownship.get();

        // Update fields of the existing township with values from the provided township object
        existingTownship.setName(township.getName());
        existingTownship.setDescription(township.getDescription());
        existingTownship.setHistory(township.getHistory());
        existingTownship.setCulturalSignificance(township.getCulturalSignificance());
        existingTownship.setLocation(township.getLocation());
        existingTownship.setImages(township.getImages());
        existingTownship.setVideos(township.getVideos());

        // Update individual fields for Demographics
        Demographics newDemographics = township.getDemographics();
        if (newDemographics != null) {
            existingTownship.getDemographics().setPopulation(newDemographics.getPopulation());
            existingTownship.getDemographics().setArea(newDemographics.getArea());
            existingTownship.getDemographics().setDemographicDetails(newDemographics.getDemographicDetails());
        }

        // Update individual fields for Amenities
        Amenities newAmenities = township.getAmenities();
        if (newAmenities != null) {
            existingTownship.getAmenities().setEducationalInstitutions(newAmenities.getEducationalInstitutions());
            existingTownship.getAmenities().setHealthcareFacilities(newAmenities.getHealthcareFacilities());
            existingTownship.getAmenities().setPublicServices(newAmenities.getPublicServices());
            existingTownship.getAmenities().setTransportation(newAmenities.getTransportation());
        }

        existingTownship.setRatings(township.getRatings());

        // Save the updated township to the database
        return townshipRepository.save(existingTownship);
    }

    public void deleteTownship(Long townshipId) {
        if (townshipId == null) {
            throw new IllegalArgumentException("Township ID cannot be null");
        }
        if (!townshipRepository.existsById(townshipId)) {
            throw new EntityNotFoundException("No township found for the given ID");
        }
        townshipRepository.deleteById(townshipId);
    }

    public Rating rateTownship(Long townshipId, Rating rating) {
        if (townshipId == null) {
            throw new IllegalArgumentException("Township ID cannot be null");
        }
        Optional<Township> optionalTownship = townshipRepository.findById(townshipId);

        if (!optionalTownship.isPresent()) {
            throw new EntityNotFoundException("No township found for the given ID");
        }
        Township township = optionalTownship.get();
        // Assuming the Rating entity has a method setTownship() to set the associated township
        rating.setTownship(township);

        // Assuming the Rating entity has a list of ratings in Township, we add the new rating to it
        township.getRatings().add(rating);

        // Save the new rating to the database using the ratingRepository
        return ratingRepository.save(rating);
    }

    public Rating updateRating(Long townshipId, Rating rating) {
        if (townshipId == null) {
            throw new IllegalArgumentException("Township ID cannot be null");
        }

        Optional<Township> optionalTownship = townshipRepository.findById(townshipId);

        if (!optionalTownship.isPresent()) {
            throw new EntityNotFoundException("No township found for the given ID");
        }

        Township township = optionalTownship.get();

        // Assuming the Rating entity has a method getUserId() to get the user who rated
        Long userId = rating.getUserId();

        // Find the existing rating for the township by the given user
        Optional<Rating> existingRatingOptional = ratingRepository.findByTownshipAndUserId(township, userId);

        if (!existingRatingOptional.isPresent()) {
            throw new EntityNotFoundException("No rating found for the given user and township");
        }

        Rating existingRating = existingRatingOptional.get();

        // Update the fields of the existing rating with values from the provided rating object
        existingRating.setRating(rating.getRating());
        existingRating.setComment(rating.getComment());

        // You can add more field updates if necessary

        // Save the updated rating to the database
        return ratingRepository.save(existingRating);
    }

    public void deleteRating(Long townshipId, Long userId) {
        if (townshipId == null || userId == null) {
            throw new IllegalArgumentException("Township ID and User ID cannot be null");
        }

        Optional<Township> optionalTownship = townshipRepository.findById(townshipId);

        if (!optionalTownship.isPresent()) {
            throw new EntityNotFoundException("No township found for the given ID");
        }

        Township township = optionalTownship.get();

        // Find the rating for the township by the given user
        Optional<Rating> existingRatingOptional = ratingRepository.findByTownshipAndUserId(township, userId);

        if (!existingRatingOptional.isPresent()) {
            throw new EntityNotFoundException("No rating found for the given user and township");
        }

        Rating ratingToDelete = existingRatingOptional.get();

        // Delete the rating from the database
        ratingRepository.delete(ratingToDelete);
    }
}

