package kr.sparta.deliveryapi.service;

import com.sun.nio.sctp.IllegalReceiveException;
import kr.sparta.deliveryapi.model.Delivery;
import kr.sparta.deliveryapi.model.enumtype.DeliveryStatus;
import kr.sparta.deliveryapi.model.enumtype.ItemType;
import kr.sparta.deliveryapi.model.Parcel;
import kr.sparta.deliveryapi.repository.DeliveryRepository;
import kr.sparta.deliveryapi.repository.ParcelRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ParcelDeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final ParcelRepository parcelRepository;

    public ParcelDeliveryService(DeliveryRepository deliveryRepository, ParcelRepository parcelRepository) {
        this.deliveryRepository = deliveryRepository;
        this.parcelRepository = parcelRepository;
    }

    public Delivery deliverParcel(Long parcelId) {
        final Parcel parcel = parcelRepository.findById(parcelId)
                .orElseThrow(IllegalArgumentException::new);

        final String trackingNo = generateTrackingNo(parcel.getDescription());
        final Delivery delivery = Delivery.builder()
                .trackingNumber(trackingNo)
                .itemType(ItemType.PARCEL)
                .status(DeliveryStatus.SHIPPED)
                .itemId(parcel.getId())
                .name(parcel.getDescription())
                .build();

        deliveryRepository.save(delivery);

        return delivery;
    }

    private String generateTrackingNo(String description) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"))
                + String.valueOf(description.hashCode()).substring(0, 4);
    }

    public DeliveryStatus trackParcel(String trackingNumber) {
        return deliveryRepository.findById(trackingNumber)
                .map(Delivery::getStatus)
                .orElseThrow(IllegalReceiveException::new);
    }

    public List<Parcel> getAllParcels() {
        return parcelRepository.findAll();
    }
}
