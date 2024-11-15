package kr.sparta.deliveryapi.repository;

import kr.sparta.deliveryapi.model.Food;
import kr.sparta.deliveryapi.model.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParcelRepository extends JpaRepository<Parcel, Long> {
}
