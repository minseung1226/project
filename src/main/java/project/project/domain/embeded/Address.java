package project.project.domain.embeded;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public class Address {
    private String 주소;
    private String 상세주소;
}
