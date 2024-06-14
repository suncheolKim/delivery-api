package kr.sparta.deliveryapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Parcel implements DeliveryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Override
    @JsonIgnore // DeliveryEntity를 구현하기 위한 getter일 뿐, 응답 값으로 갔을 때 노출하고 싶지 않아 JsonIgnore 처리
    public String getName() {
        return description;
    }
}

