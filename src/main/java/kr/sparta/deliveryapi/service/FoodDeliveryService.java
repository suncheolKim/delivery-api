package kr.sparta.deliveryapi.service;

import kr.sparta.deliveryapi.model.Food;
import kr.sparta.deliveryapi.model.enumtype.ItemType;
import kr.sparta.deliveryapi.repository.DeliveryRepository;
import kr.sparta.deliveryapi.repository.FoodRepository;
import org.springframework.stereotype.Service;

@Service
public class FoodDeliveryService extends AbstractDelivery<Food> {
    public FoodDeliveryService(FoodRepository foodRepository, DeliveryRepository deliveryRepository) {
        super(deliveryRepository, foodRepository);
    }

    @Override
    protected ItemType getItemType() {
        return ItemType.FOOD;
    }
}
