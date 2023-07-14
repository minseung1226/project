package project.project.search;

import lombok.Data;

@Data
public class RoomSearch {
    private Integer minDeposit;
    private Integer maxDeposit;
    private Integer minMonthlyRent;
    private Integer maxMonthlyRent;
    private Integer minRealSize;
    private Integer maxRealSize;
    private Double minLat;
    private Double minLng;
    private Double maxLat;
    private Double maxLng;

}
