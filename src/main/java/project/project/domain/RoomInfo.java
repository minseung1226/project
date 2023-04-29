package project.project.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class RoomInfo {

    @Id
    @GeneratedValue
    @Column(name = "room_info_id")
    private Long id;

    private String 방구조;
    private String 관리비;
    private String 관리비포함항목;
    private String 주실방향;
    private String 옵션;
    private String 반려동물여부;
    private String 주차여부;
    private String 엘리베이터여부;
    private String 입주가능일;
}
