package com.codedecode.foodcatalogue.service;

import com.codedecode.foodcatalogue.dto.FoodCataloguePage;
import com.codedecode.foodcatalogue.dto.FoodItemDTO;

import java.util.List;

public interface FoodCatalogueService {


    public FoodItemDTO addFoodItem(FoodItemDTO foodItem);

    public FoodItemDTO getById(Integer id);

    public FoodCataloguePage fetchFoodCataloguePageDetails(Integer restaurantId);

}
