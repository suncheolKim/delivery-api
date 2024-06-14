package kr.sparta.deliveryapi.service;

import kr.sparta.deliveryapi.model.Parcel;
import kr.sparta.deliveryapi.model.enumtype.ItemType;
import kr.sparta.deliveryapi.repository.DeliveryRepository;
import kr.sparta.deliveryapi.repository.ParcelRepository;
import org.springframework.stereotype.Service;

@Service
public class ParcelDeliveryService extends AbstractDelivery<Parcel> {
    public ParcelDeliveryService(DeliveryRepository deliveryRepository, ParcelRepository parcelRepository) {
        super(deliveryRepository, parcelRepository);
    }

    @Override
    protected ItemType getItemType() {
        return ItemType.PARCEL;
    }
}
