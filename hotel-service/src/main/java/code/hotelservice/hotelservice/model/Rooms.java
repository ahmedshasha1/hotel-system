package code.hotelservice.hotelservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Data
public class Rooms extends BaseEntity {

    @Column(name = "room_type")
    private String roomType;
    @NotBlank(message = "price.notnull")
    @Column(name = "room_price")
    private Double roomPrice;
    @Column(name = "photo_path")
    private String logoPath;
    private String description;
    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Booking> bookings;

    @Override
    public String toString() {
        return "Room{" +
                ", roomType='" + roomType + '\'' +
                ", roomPrice=" + roomPrice +
                ", roomPhotoUrl='" + logoPath + '\'' +
                ", roomDescription='" + description + '\'' +
                '}';
    }
}

