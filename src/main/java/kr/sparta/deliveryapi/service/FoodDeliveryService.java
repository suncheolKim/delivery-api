package kr.sparta.deliveryapi.service;

import com.sun.nio.sctp.IllegalReceiveException;
import kr.sparta.deliveryapi.model.Delivery;
import kr.sparta.deliveryapi.model.enumtype.DeliveryStatus;
import kr.sparta.deliveryapi.model.Food;
import kr.sparta.deliveryapi.model.enumtype.ItemType;
import kr.sparta.deliveryapi.repository.DeliveryRepository;
import kr.sparta.deliveryapi.repository.FoodRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class FoodDeliveryService {
    private final FoodRepository foodRepository;
    private final DeliveryRepository deliveryRepository;

    public FoodDeliveryService(FoodRepository foodRepository, DeliveryRepository deliveryRepository) {
        this.foodRepository = foodRepository;
        this.deliveryRepository = deliveryRepository;
    }

    public Delivery deliverFood(Long foodId) {
        final Food food = foodRepository.findById(foodId)
                .orElseThrow(IllegalArgumentException::new);

        final String trackingNo = generateTrackingNo(food.getName());
        final Delivery delivery = Delivery.builder()
                .trackingNumber(trackingNo)
                .itemType(ItemType.FOOD)
                .status(DeliveryStatus.SHIPPED)
                .itemId(food.getId())
                .name(food.getName())
                .build();

        deliveryRepository.save(delivery);

        return delivery;
    }

    private String generateTrackingNo(String description) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"))
                + String.valueOf(description.hashCode()).substring(0, 4);
    }

    public DeliveryStatus trackFood(String trackingNumber) {
        return deliveryRepository.findById(trackingNumber)
                .map(Delivery::getStatus)
                .orElseThrow(IllegalReceiveException::new);
    }

    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }
}
