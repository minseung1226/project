package project.project.domain;

import jakarta.persistence.*;
import lombok.*;
import project.project.domain.converter.EnumListConverter;
import project.project.domain.enum_type.Bearing;

import project.project.domain.enum_type.Option;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude ={"maintenanceList","options"})
@ToString(exclude ={"maintenanceList","options"})
public class RoomInfo {

    @Id
    @GeneratedValue
    @Column(name = "room_info_id")
    private Long id;


    @Enumerated(EnumType.STRING)
    private Bearing bearing; // 주실 방향

    @Convert(converter = EnumListConverter.class)
    @Enumerated(EnumType.STRING)
    private List<Option> options=new ArrayList<>(); // 옵션
    private boolean animal; // 반려동물
    private boolean parking; // 주차 여부
    private boolean ev;      // 엘리베이터 유무

    private String floor;  // 층수
    private String entireFloor; //전체층
    private Integer household; //총 세대 수

    private double realSize;   //전용면적
    private double supplySize; //공급 면적
}
