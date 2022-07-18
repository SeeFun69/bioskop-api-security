package infosys.teamd.bioskopapisecurity.DTO;

import infosys.teamd.bioskopapisecurity.Model.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingRequestDTO {
    private Long id;
    private User usr;
    private Schedule sch;

    public Booking covertToEntitiy(){
        return Booking.builder()
                .bookingId(this.id)
                .user(this.usr)
                .schedule(this.sch)
                .build();
    }
}

