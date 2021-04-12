package com.leo.daycare.model;

// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
CensusData censusData = om.readValue(myJsonString), CensusData.class); */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CensusData {

    private List<ArrayList<String>> data;

    public CensusData(List<ArrayList<String>> data) {
        this.data = data;
    }

    public CensusData() {
    }

    public List<ArrayList<String>> getData() {
        return data;
    }

    public void setData(List<ArrayList<String>> data) {
        this.data = data;
    }

}


