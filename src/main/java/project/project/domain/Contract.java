package project.project.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Contract {

    @Id
    @GeneratedValue
    @Column(name = "contract_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "room_id")
    private Room room;





}
