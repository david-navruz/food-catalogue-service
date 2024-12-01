package com.codedecode.foodcatalogue.service;

import com.codedecode.foodcatalogue.dto.FoodCataloguePage;
import com.codedecode.foodcatalogue.dto.FoodItemDTO;
import com.codedecode.foodcatalogue.dto.Restaurant;
import com.codedecode.foodcatalogue.entity.FoodItem;
import com.codedecode.foodcatalogue.mapper.FoodItemMapper;
import com.codedecode.foodcatalogue.repository.FoodItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class FoodCatalogueServiceImpl implements FoodCatalogueService {


    @Autowired
    private FoodItemRepository foodItemRepository;
    @Autowired
    private RestTemplate restTemplate;


    @Override
    public FoodItemDTO addFoodItem(FoodItemDTO foodItemDTO) {
        FoodItem foodItemSaved = foodItemRepository.save(FoodItemMapper.INSTANCE.mapFoodItemDTOToFoodItem(foodItemDTO));
        return FoodItemMapper.INSTANCE.mapFoodItemToFoodItemDto(foodItemSaved);
    }

    @Override
    public FoodItemDTO getById(Integer id) {
        Optional<FoodItem> foodItemFound = foodItemRepository.findById(id);
        return foodItemFound.map(FoodItemMapper.INSTANCE::mapFoodItemToFoodItemDto).orElse(null);
    }

    @Override
    public FoodCataloguePage fetchFoodCataloguePageDetails(Integer restaurantId) {
       List<FoodItem> foodItemList = getFoodItemList(restaurantId);
       Restaurant restaurant = fetchRestaurantDetailsFromRestaurantMS(restaurantId);
        return createFoodCataloguePage(foodItemList, restaurant);
    }

    private FoodCataloguePage createFoodCataloguePage(List<FoodItem> foodItemList, Restaurant restaurant){
        FoodCataloguePage foodCataloguePage = new FoodCataloguePage();
        foodCataloguePage.setFoodItemsList(foodItemList);
        foodCataloguePage.setRestaurant(restaurant);
        return foodCataloguePage;
    }

    private List<FoodItem> getFoodItemList(Integer restaurantId){
        return foodItemRepository.findByRestaurantId(restaurantId);
    }

    private Restaurant fetchRestaurantDetailsFromRestaurantMS(Integer restaurantId){
        return restTemplate.getForObject("http://RESTAURANT-SERVICE/restaurant/getById/"+restaurantId, Restaurant.class);
    }

}
