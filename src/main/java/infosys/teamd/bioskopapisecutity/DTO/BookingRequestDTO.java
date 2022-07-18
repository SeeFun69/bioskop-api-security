package infosys.teamd.bioskopapisecutity.DTO;

import infosys.teamd.bioskopapisecutity.DTO.*;
import infosys.teamd.bioskopapisecutity.Model.*;
import infosys.teamd.bioskopapisecutity.Exception.*;
import infosys.teamd.bioskopapisecutity.Response.*;
import infosys.teamd.bioskopapisecutity.Service.*;
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

