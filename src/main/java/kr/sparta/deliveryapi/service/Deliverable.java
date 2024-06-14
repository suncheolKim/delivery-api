package kr.sparta.deliveryapi.service;

import kr.sparta.deliveryapi.model.Delivery;
import kr.sparta.deliveryapi.model.DeliveryEntity;
import kr.sparta.deliveryapi.model.enumtype.DeliveryStatus;

import java.util.List;

public interface Deliverable<T extends DeliveryEntity> {
	Delivery deliver(Long id);

	DeliveryStatus track(String trackingNumber);

	List<T> getAll();
}
