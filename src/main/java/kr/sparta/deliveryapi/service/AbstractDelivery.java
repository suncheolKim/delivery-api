package kr.sparta.deliveryapi.service;

import com.sun.nio.sctp.IllegalReceiveException;
import kr.sparta.deliveryapi.model.Delivery;
import kr.sparta.deliveryapi.model.DeliveryEntity;
import kr.sparta.deliveryapi.model.enumtype.DeliveryStatus;
import kr.sparta.deliveryapi.model.enumtype.ItemType;
import kr.sparta.deliveryapi.repository.DeliveryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public abstract class AbstractDelivery<T extends DeliveryEntity> implements Deliverable<T> {
	protected final DeliveryRepository deliveryRepository;
	protected final JpaRepository<T, Long> jpaRepository;

	protected AbstractDelivery(DeliveryRepository deliveryRepository, JpaRepository<T, Long> jpaRepository) {
		this.deliveryRepository = deliveryRepository;
        this.jpaRepository = jpaRepository; // Food, Parcel Repository를 자식 클래스에서 주입해 줌
    }

	protected abstract ItemType getItemType();

	// 송장번호 생성 메소드
	protected String generateTrackingNo(String description) {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"))
			+ String.valueOf(description.hashCode()).substring(0, 4);
	}

	@Override
	public DeliveryStatus track(String trackingNumber) {
		return deliveryRepository.findById(trackingNumber)
			.map(Delivery::getStatus)
			.orElseThrow(IllegalReceiveException::new);
	}

	@Override
	public Delivery deliver(Long id) {
		// Food, Parcel 둘다 Long 타입의 id로 findById를 하고 있으므로 추상화 가능하다.
		final DeliveryEntity deliveryEntity = jpaRepository.findById(id)
				.orElseThrow(IllegalArgumentException::new);

		final String trackingNo = generateTrackingNo(deliveryEntity.getName());
		final Delivery delivery = Delivery.builder()
				.trackingNumber(trackingNo)
				.itemType(getItemType()) // getItemType() 추상 메소드
				.status(DeliveryStatus.SHIPPED)
				.itemId(deliveryEntity.getId())  // DeliveryEntity.getId()를 선언하여 각 엔티티에서 이 값을 리턴함
				.name(deliveryEntity.getName())  // DeliveryEntity.getName()을 선언. Parcel 엔티티는 getName()을 description을 리턴하도록 구현
				.build();

		deliveryRepository.save(delivery);

		return delivery;
	}

	@Override
	public List<T> getAll() {
		return jpaRepository.findAll();
	}
}
