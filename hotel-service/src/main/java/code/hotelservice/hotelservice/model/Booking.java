package code.hotelservice.hotelservice.model;

import code.hotelservice.hotelservice.model.auth.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
public class Booking extends BaseEntity {
    @NotNull(message = "check in date is required")
    private LocalDate checkInDate;

    @Future(message = "check out date must be in the future")
    private LocalDate checkOutDate;

    @Min(value = 1, message = "Number of adults must not be less that 1")
    private int numOfAdults;

    @Min(value = 0, message = "Number of children must not be less that 0")
    private int numOfChildren;

    private int totalNumOfGuest;

    @Column(name = "code")
    private String bookingCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Rooms room;

    public void calculateNumberOfGuest(){
        totalNumOfGuest = numOfAdults + numOfChildren;
    }
    public void setNumOfAdults(int numOfAdults) {
        this.numOfAdults = numOfAdults;
        calculateNumberOfGuest();
    }

    public void setNumOfChildren(int numOfChildren) {
        this.numOfChildren = numOfChildren;
        calculateNumberOfGuest();
    }

    @Override
    public String toString() {
        return "Booking{" +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", numOfAdults=" + numOfAdults +
                ", numOfChildren=" + numOfChildren +
                ", totalNumOfGuest=" + totalNumOfGuest +
                ", Code='" + bookingCode + '\'' +
                '}';
    }
}