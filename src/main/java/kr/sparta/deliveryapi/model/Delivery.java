package kr.sparta.deliveryapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import kr.sparta.deliveryapi.model.enumtype.DeliveryStatus;
import kr.sparta.deliveryapi.model.enumtype.ItemType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {
    @Id
    private String trackingNumber;

    @Enumerated(value = EnumType.STRING)
    private DeliveryStatus status;

    @Enumerated(value = EnumType.STRING)
    private ItemType itemType;

    private Long itemId;

    private String name;
}
