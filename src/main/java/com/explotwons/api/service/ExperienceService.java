package com.explotwons.api.service;

import com.explotwons.api.entity.Experience;
import com.explotwons.api.entity.ExperienceRating;
import com.explotwons.api.entity.Location;
import com.explotwons.api.entity.Township;
import com.explotwons.api.repository.ExperienceRatingRepository;
import com.explotwons.api.repository.ExperienceRepository;
import com.explotwons.api.repository.TownshipRepository;
import com.explotwons.api.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.Join;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExperienceService {

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private TownshipRepository townshipRepository;

    @Autowired
    private ExperienceRatingRepository experienceRatingRepository;

    public List<Experience> getAllExperiences(String category, String province, Date date, Double minPrice, Double maxPrice, Double averageRating, String sortBy, String order) {

        Specification<Experience> spec = Specification.where(null);

        if (category != null && !category.trim().isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Constants.CATEGORY), category));
        }

        if (province != null && !province.trim().isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> {
                // Joining Experience with Township and then with Location to access the province attribute
                Join<Experience, Township> experienceTownshipJoin = root.join(Constants.TOWNSHIP);
                Join<Township, Location> townshipLocationJoin = experienceTownshipJoin.join(Constants.LOCATION);
                return criteriaBuilder.equal(townshipLocationJoin.get(Constants.PROVINCE), province);
            });
        }

        if (date != null) {
            // Assuming there's a date field in Experience
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Constants.DATE), date));
        }

        if (minPrice != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get(Constants.PRICE), minPrice));
        }

        if (maxPrice != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get(Constants.PRICE), maxPrice));
        }

        // You can add more filters as necessary...

        List<Experience> experiences = experienceRepository.findAll(spec);

        setAverageRating(experiences);

        // Filter based on averageRating
        if (averageRating != null) {
            experiences = experiences.stream().filter(e -> e.getAverageRating() >= averageRating).collect(Collectors.toList());
        }

        // Sorting based on the provided sortBy and order parameters
        if (sortBy != null) {
            Comparator<Experience> comparator = null;

            if (Constants.PRICE.equalsIgnoreCase(sortBy)) {
                comparator = Comparator.comparingDouble(Experience::getPrice);
            } else if (Constants.AVERAGE_RATING.equalsIgnoreCase(sortBy)) {
                comparator = Comparator.comparingDouble(Experience::getAverageRating);
            }

            if (comparator != null) {
                if (Constants.DESC.equalsIgnoreCase(order)) {
                    comparator = comparator.reversed();
                }
                experiences.sort(comparator);
            }
        }

        return experiences;
    }



    public Experience getExperienceById(Long experienceId) {

        Optional<Experience> optionalExperience = experienceRepository.findById(experienceId);

        if (!optionalExperience.isPresent()) {
            throw new EntityNotFoundException("No experience found for the given ID");
        }

        Experience experience = optionalExperience.get();

        List<ExperienceRating> ratings = experience.getRatings();
        if (ratings != null && !ratings.isEmpty()) {
            double totalRating = 0;
            for (ExperienceRating rating : ratings) {
                totalRating += rating.getRating();
            }
            double averageRating = totalRating / ratings.size();
            // Assuming you have a setAverageRating method in the Experience class
            experience.setAverageRating(averageRating);
        }

        return experience;
    }

    public List<Experience> getTopRatedExperiences() {

        List<Experience> allExperiences = experienceRepository.findAll();
        List<Experience> topRatedExperiences = new ArrayList<>();

        for (Experience experience : allExperiences) {
            List<ExperienceRating> ratings = experience.getRatings();

            if (ratings != null && ratings.size() >= 5) {
                double totalRating = 0;

                for (ExperienceRating rating : ratings) {
                    totalRating += rating.getRating();
                }

                double averageRating = totalRating / ratings.size();

                if (averageRating >= 4) {
                    experience.setAverageRating(averageRating);  // Assuming you have a setAverageRating method
                    topRatedExperiences.add(experience);
                }
            }
        }

        // Sort the list based on average rating in descending order
        topRatedExperiences.sort((e1, e2) -> Double.compare(e2.getAverageRating(), e1.getAverageRating()));

        return topRatedExperiences;
    }

    public Experience addExperience(Long townshipId, Experience experience) {
        if (experience == null) {
            throw new IllegalArgumentException("Experience cannot be null");
        }

        Optional<Township> optionalTownship = townshipRepository.findById(townshipId);

        if (!optionalTownship.isPresent()) {
            throw new EntityNotFoundException("No township found for the given ID");
        }

        experience.setTownship(optionalTownship.get());

        return experienceRepository.save(experience);

    }

    public Experience updateExperience(Long experienceId, Experience experience) {
        if (experienceId == null) {
            throw new IllegalArgumentException("Experience ID cannot be null");
        }

        Optional<Experience> optionalExistingExperience = experienceRepository.findById(experienceId);

        if (!optionalExistingExperience.isPresent()) {
            throw new EntityNotFoundException("No experience found for the given ID");
        }

        Experience existingExperience = optionalExistingExperience.get();

        // Update fields of the existing experience with values from the provided experience object
        existingExperience.setName(experience.getName());
        existingExperience.setDescription(experience.getDescription());
        existingExperience.setCategory(experience.getCategory());
        existingExperience.setImages(experience.getImages());
        existingExperience.setVideos(experience.getVideos());
        existingExperience.setDuration(experience.getDuration());
        existingExperience.setLocation(experience.getLocation());
        existingExperience.setLatitude(experience.getLatitude());
        existingExperience.setLongitude(experience.getLongitude());
        existingExperience.setPrice(experience.getPrice());
        existingExperience.setAvailability(experience.getAvailability());
        existingExperience.setBookingOptions(experience.getBookingOptions());
        existingExperience.setAgeRestrictions(experience.getAgeRestrictions());
        existingExperience.setSkillLevel(experience.getSkillLevel());
        existingExperience.setPhysicalRequirements(experience.getPhysicalRequirements());
        existingExperience.setAccessibilityInformation(experience.getAccessibilityInformation());

        // Save the updated experience to the database
        return experienceRepository.save(existingExperience);
    }


    public void deleteExperience(Long experienceId) {
        experienceRepository.deleteById(experienceId);
    }

    public ExperienceRating rateExperience(Long experienceId, ExperienceRating rating) {
        if (experienceId == null) {
            throw new IllegalArgumentException("Experience ID cannot be null");
        }
        Optional<Experience> optionalExperience = experienceRepository.findById(experienceId);

        if (!optionalExperience.isPresent()) {
            throw new EntityNotFoundException("No experience found for the given ID");
        }
        Experience experience = optionalExperience.get();
        // Assuming the Rating entity has a method setTownship() to set the associated township
        rating.setExperience(experience);

        // Assuming the Rating entity has a list of ratings in Township, we add the new rating to it
        experience.getRatings().add(rating);

        // Save the new rating to the database using the ratingRepository
        return experienceRatingRepository.save(rating);
    }

    public ExperienceRating updateExperienceRating(Long experienceId, ExperienceRating rating) {

        if (experienceId == null) {
            throw new IllegalArgumentException("Experience ID cannot be null");
        }

        Optional<Experience> optionalExperience = experienceRepository.findById(experienceId);

        if (!optionalExperience.isPresent()) {
            throw new EntityNotFoundException("No experience found for the given ID");
        }

        Experience experience = optionalExperience.get();

        // Assuming the Rating entity has a method getUserId() to get the user who rated
        Long userId = rating.getUserId();

        // Find the existing rating for the experience by the given user
        Optional<ExperienceRating> existingExperienceRatingOptional = experienceRatingRepository.findByExperienceAndUserId(experience, userId);

        if (!existingExperienceRatingOptional.isPresent()) {
            throw new EntityNotFoundException("No rating found for the given user and experience");
        }

        ExperienceRating existingExperienceRating = existingExperienceRatingOptional.get();

        // Update the fields of the existing rating with values from the provided rating object
        existingExperienceRating.setRating(rating.getRating());
        existingExperienceRating.setComment(rating.getComment());

        // You can add more field updates if necessary

        // Save the updated rating to the database
        return experienceRatingRepository.save(existingExperienceRating);
    }

    public void deleteExperienceRating(Long experienceId, Long userId) {

        if (experienceId == null || userId == null) {
            throw new IllegalArgumentException("Experience ID and User ID cannot be null");
        }

        Optional<Experience> optionalExperience = experienceRepository.findById(experienceId);

        if (!optionalExperience.isPresent()) {
            throw new EntityNotFoundException("No experience found for the given ID");
        }

        Experience experience = optionalExperience.get();

        // Find the rating for the township by the given user
        Optional<ExperienceRating> existingExperienceRatingOptional = experienceRatingRepository.findByExperienceAndUserId(experience, userId);

        if (!existingExperienceRatingOptional.isPresent()) {
            throw new EntityNotFoundException("No rating found for the given user and experience");
        }

        ExperienceRating experienceRatingToDelete = existingExperienceRatingOptional.get();

        // Delete the rating from the database
        experienceRatingRepository.delete(experienceRatingToDelete);
    }

    private static void setAverageRating(List<Experience> experiences) {
        for (Experience experience : experiences) {
            List<ExperienceRating> ratings = experience.getRatings();
            if (ratings != null && !ratings.isEmpty()) {
                double totalRating = 0;
                for (ExperienceRating rating : ratings) {
                    totalRating += rating.getRating();
                }
                double avgRating = totalRating / ratings.size();
                experience.setAverageRating(avgRating);
            }
        }
    }
}
