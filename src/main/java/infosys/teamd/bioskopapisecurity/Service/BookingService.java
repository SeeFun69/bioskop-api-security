package infosys.teamd.bioskopapisecurity.Service;


import infosys.teamd.bioskopapisecurity.Model.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface BookingService {

    List<Booking> getAll();
    Optional<Booking> getBookingById(Long Id);
    Booking createBooking(Booking booking, Principal principal);
    void deleteSBookingById(Long Id);
    Booking updateBooking(Booking booking);
    Booking getReferenceById (Long Id);
    List<Booking> getBookingByFilmName(String name);

}
