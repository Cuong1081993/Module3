package com.example.bakeryshop.service.inMemory;

import com.example.bakeryshop.model.Country;
import com.example.bakeryshop.service.ICountryService;

import java.util.ArrayList;
import java.util.List;

public class CountryService implements ICountryService {
    private List<Country> countries;

    public CountryService() {
        countries = new ArrayList<>();
        countries.add(new Country(1L, "Viet Nam"));
        countries.add(new Country(2L, "Singapore"));
        countries.add(new Country(3L, "Thai Lan"));
        countries.add(new Country(4L, "USA"));
    }

    @Override
    public List<Country> getAllCountry() {
        return countries;
    }

    @Override
    public Country findCountryById(long id) {
        for (Country c : countries){
            if (c.getId()==id){
                return c;
            }
        }
        return null;
    }

    @Override
    public boolean insertCountryBySP(String nameCountry) {
        return false;
    }
}
