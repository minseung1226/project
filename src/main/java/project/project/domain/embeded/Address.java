package project.project.domain.embeded;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
    private String address;
    private String detail_address;

    public Address(String address, String detail_address) {
        this.address = address;
        this.detail_address = detail_address;
    }
}
