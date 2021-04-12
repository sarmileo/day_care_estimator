package com.leo.daycare.resources;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.leo.daycare.model.CensusData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/data")
public class CensusResource {

    private CensusData censusData;

    @Autowired
    public CensusResource(CensusData censusData) {
        this.censusData = censusData;
    }

    public CensusResource() {
    }

    @GetMapping
    public List<ArrayList<String>> getCensusData(Map<String, Integer> value) {
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://api.census.gov/data/2019/pep/charagegroups?get=NAME,POP&for=state:*&AGEGROUP={age}&key=893d0c348c0ed0180dbd828c80380dd99d849226";
        String censusData =
                restTemplate.getForObject(url, String.class, value);

        JsonArray convertedArray = new Gson().fromJson(censusData, JsonArray.class);

        ArrayList<String> listData = new ArrayList<>();
        List<ArrayList<String>> listCensusData = new ArrayList<ArrayList<String>>();

        for (JsonElement arr : convertedArray) {
            JsonArray array = new Gson().fromJson(arr, JsonArray.class);
            for (JsonElement e : array) {
                String el = e.getAsString();
                listData.add(el);
            }
            listCensusData.add(new ArrayList<String>(listData));
            listData.clear();
        }

        // Do not need the first list elements
        listCensusData.remove(0);
        this.censusData.setData(listCensusData);

        return listCensusData;
    }
}
