package com.leo.daycare.web;

import com.leo.daycare.model.CensusData;
import com.leo.daycare.resources.CensusResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping("/uspopulation")
public class CensusController {

    private CensusResource censusResource;
    private CensusData censusData;

    @Autowired
    public CensusController(CensusResource censusResource, CensusData censusData) {
        this.censusResource = censusResource;
        this.censusData = censusData;
    }

    @GetMapping
    public ModelAndView getPopulation() {
        return setCensusData(0, "All ages", "population");
    }

    @GetMapping("/zerotofour")
    public ModelAndView populationZeroToFour() {
        return setCensusData(1, "age 0 to 4 years", "population");
    }

    @GetMapping("/fivetonine")
    public ModelAndView populationFiveToNine() {
        return setCensusData(2, "age 5 to 9 years", "population");
    }

    @GetMapping("/daycare/zerotofour")
    public ModelAndView dayCarePopulationZeroToFour() {
        return setCensusData(1, "age 0 to 4 years", "daycarecalculator");
    }

    @GetMapping("/daycare/fivetonine")
    public ModelAndView dayCarePopulationFiveToNine() {
        return setCensusData(2, "age 5 to 9 years", "daycarecalculator");
    }

    @GetMapping("/daycare/zerotonine")
    public ModelAndView dayCarePopulationZeroToNine() {
        setCensusData(1, "age 0 to 4 years", "daycarecalculator");
        List<ArrayList<String>> dataZeroToFour = censusData.getData();
        dataZeroToFour.sort(Comparator.comparing(o -> o.get(0)));
        Integer totalZeroToFour = dataZeroToFour.stream()
                .mapToInt(l-> Integer.valueOf(l.get(1)))
                .sum();

        setCensusData(2, "age 5 to 9 years", "daycarecalculator");
        List<ArrayList<String>> dataFiveToNine = censusData.getData();
        dataFiveToNine.sort(Comparator.comparing(o -> o.get(0)));
        Integer totalFiveToNine = dataFiveToNine.stream()
                .mapToInt(l-> Integer.valueOf(l.get(1)))
                .sum();

        List<ArrayList<String>> dataZeroToNine = new ArrayList<>();
        for (int i = 0; i < dataZeroToFour.size(); i++) {
            Integer zeroToNine = Integer.valueOf(dataZeroToFour.get(i).get(1)) + Integer.valueOf(dataFiveToNine.get(i).get(1));
            dataZeroToNine.add(dataZeroToFour.get(i));
            dataZeroToNine.get(i).set(1, String.valueOf(zeroToNine));
        }

        String ageGroup = "age 0 to 9 years";
        ModelAndView mav = new ModelAndView("daycarecalculator");
        mav.addObject("dataList", dataZeroToNine);
        mav.addObject("ageGroup", ageGroup);
        mav.addObject("total", totalZeroToFour + totalFiveToNine);

        return mav;
    }

    public ModelAndView setCensusData(Integer age, String ageGroup, String viewName) {
        Map<String, Integer> ageMap = new HashMap<>();
        ageMap.put("age", age);

        censusResource.getCensusData(ageMap);

        List<ArrayList<String>> data = censusData.getData();
        data.sort(Comparator.comparing(o -> o.get(0)));
        Integer total = data.stream()
                .mapToInt(l-> Integer.valueOf(l.get(1)))
                .sum();

        ModelAndView mav = new ModelAndView(viewName);
        mav.addObject("dataList", data);
        mav.addObject("ageGroup", ageGroup);
        mav.addObject("total", total);

        return mav;
    }
}
