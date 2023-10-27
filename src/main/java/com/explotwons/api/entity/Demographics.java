package com.explotwons.api.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Demographics {
    private Integer population;
    private String area;
    private String demographicDetails;

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDemographicDetails() {
        return demographicDetails;
    }

    public void setDemographicDetails(String demographicDetails) {
        this.demographicDetails = demographicDetails;
    }
}
