package com.explotwons.api.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Amenities {

    private String educationalInstitutions;
    private String healthcareFacilities;
    private String publicServices;
    private String transportation;

    public String getEducationalInstitutions() {
        return educationalInstitutions;
    }

    public void setEducationalInstitutions(String educationalInstitutions) {
        this.educationalInstitutions = educationalInstitutions;
    }

    public String getHealthcareFacilities() {
        return healthcareFacilities;
    }

    public void setHealthcareFacilities(String healthcareFacilities) {
        this.healthcareFacilities = healthcareFacilities;
    }

    public String getPublicServices() {
        return publicServices;
    }

    public void setPublicServices(String publicServices) {
        this.publicServices = publicServices;
    }

    public String getTransportation() {
        return transportation;
    }

    public void setTransportation(String transportation) {
        this.transportation = transportation;
    }
}
