package com.example.bakeryshop.service;

import com.example.bakeryshop.model.Country;

import java.util.List;

public interface ICountryService {
    List<Country> getAllCountry();
    Country findCountryById(long id);
    boolean insertCountryBySP(String nameCountry);
}
