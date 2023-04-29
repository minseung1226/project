package project.project.domain;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

@Entity
public class Photo {
    @Id
    @GeneratedValue
    @Column(name = "photo_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

}
