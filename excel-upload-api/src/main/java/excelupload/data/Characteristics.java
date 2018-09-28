package excelupload.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Characteristics {
    private String occupancyScheme;
    private String occupancyType;

    private String constructionScheme;
    private String constructionClass;

    private int numOfStories;
    private int yearBuilt;

    public Characteristics() {
        // Needed by Jackson deserialization
    }

    public Characteristics(String occupancyScheme,
                           String occupancyType,
                           String constructionScheme,
                           String constructionClass,
                           int numOfStories,
                           int yearBuilt
                           ) {
        this.constructionScheme = constructionScheme;
        this.constructionClass = constructionClass;
        this.occupancyScheme = occupancyScheme;
        this.occupancyType = occupancyType;

        this.numOfStories = numOfStories;
        this.yearBuilt = yearBuilt;
    }

    @JsonProperty
    public String getOoccupancy() {
        return occupancyScheme + occupancyType;
    }

    @JsonProperty
    public String getConstruction() {
        return constructionScheme + constructionClass;
    }

    @JsonProperty
    public int getNumOfStories() {
        return numOfStories;
    }

    @JsonProperty
    public int getYearBuilt() {
        return yearBuilt;
    }

}
