package project.project.controller.form.room;

import lombok.Data;
import project.project.domain.Room;
import project.project.domain.enum_type.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class RoomDetailForm {
    private long roomId;
    private long userId;
    private String registrantName;

    private String registrant;
    private int postcode;
    private String address;
    private String detailAddress;
    private String extraAddress;
    private List<String> images;
    private double lat;
    private double lng;
    private int deposit;
    private int monthlyRent;
    private HouseType houseType;
    private RoomType roomType;
    private double maintenance;
    private List<MaintenanceItem> maintenanceItem;
    private String title;
    private String content;
    private String roomNumber;
    private Bearing bearing;
    private List<Option> options;
    private boolean animal;
    private boolean parking;
    private String floor;
    private String entireFloor;
    private double realSize;
    private double supplySize;

    private LocalDate moveInDate;


    public RoomDetailForm(Room room) {
        this.roomId = room.getId();
        this.userId = room.getUser().getId();
        this.registrantName=room.getUser().getName();
        this.registrant=room.getRegistrant();
        this.postcode = room.getAddress().getPostcode();
        this.address = room.getAddress().getAddress();
        this.detailAddress = room.getAddress().getDetailAddress();
        this.extraAddress = room.getAddress().getExtraAddress();
        this.images = room.getPhotos().stream().map(photo->photo.getImg()).collect(Collectors.toList());
        this.lat = room.getLat();
        this.lng = room.getLng();
        this.deposit = room.getDeposit();
        this.monthlyRent = room.getMonthlyRent();
        this.houseType = room.getHouseType();
        this.roomType = room.getRoomType();
        this.maintenance = room.getMaintenance();
        this.maintenanceItem = room.getMaintenanceItem();
        this.title = room.getTitle();
        this.content = room.getContent();
        this.roomNumber=room.getRoomNumber();
        this.bearing = room.getRoomInfo().getBearing();
        this.options = room.getRoomInfo().getOptions();
        this.animal = room.getRoomInfo().isAnimal();
        this.parking = room.getRoomInfo().isParking();
        this.floor = room.getRoomInfo().getFloor();
        this.entireFloor = room.getRoomInfo().getEntireFloor();
        this.realSize = room.getRoomInfo().getRealSize();
        this.supplySize = room.getRoomInfo().getSupplySize();
        this.moveInDate=room.getMoveInDate();
    }
}
