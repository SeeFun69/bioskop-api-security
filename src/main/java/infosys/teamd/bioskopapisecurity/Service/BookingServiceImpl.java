package infosys.teamd.bioskopapisecurity.Service;

import infosys.teamd.bioskopapisecurity.Model.*;
import infosys.teamd.bioskopapisecurity.Exception.*;
import infosys.teamd.bioskopapisecurity.Repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService{

    private BookingRepository bookingRepository;
    private UserRepository userRepository;
    private RoleRepo roleRepo;

    @Override
    public List<Booking> getAll() {
        List<Booking> optionalBooking = bookingRepository.findAll();
        if(optionalBooking.isEmpty()){
            throw new ResourceNotFoundException("Data Booking not exist");
        }
        return this.bookingRepository.findAll();
    }

    @Override
    public Optional<Booking> getBookingById(Long Id) throws ResourceNotFoundException {
        Optional<Booking> optionalBooking = bookingRepository.findById(Id);
        if(optionalBooking.isEmpty()){
            throw new ResourceNotFoundException("Booking not exist with id " + Id);
        }
        return this.bookingRepository.findById(Id);
    }

    @Override
    public Booking updateBooking(Booking booking) throws ResourceNotFoundException {
        Optional<Booking> optionalBooking = bookingRepository.findById(booking.getBookingId());
        if(optionalBooking.isEmpty()){
            throw new ResourceNotFoundException("Booking not exist with id " + booking.getBookingId());
        }
        return this.bookingRepository.save(booking);
    }

    @Override
    public Booking getReferenceById(Long Id) {
        return this.bookingRepository.getReferenceById(Id);
    }

    @Override
    public Booking createBooking(Booking booking, Principal principal) {
        String username = principal.getName();
        User user = this.userRepository.findByUsername(username);

//        booking.setCreatedAt(ZonedDateTime.now(ZoneId.of("Asia/Tokyo")));
//        booking.setUpdatedAt(ZonedDateTime.now(ZoneId.of("Asia/Tokyo")));

        if (principal == null){
            throw new ResourceNotFoundException("Login First");
        }
        if (booking.getUser() == null){
            booking.setUser(user);
        } else if(!(user.getRoles().contains(this.roleRepo.findByName("ROLE_ADMIN")))) {
            throw new ResourceNotFoundException("Not authenticated as an admin");
        }
        return this.bookingRepository.save(booking);
    }

    @Override
    public void deleteSBookingById(Long Id) throws ResourceNotFoundException{
        Optional<Booking> optionalBooking = bookingRepository.findById(Id);
        if(optionalBooking.isEmpty()){
            throw new ResourceNotFoundException("Booking not exist with id " + Id);
        }
        Booking booking = bookingRepository.getReferenceById(Id);
        this.bookingRepository.delete(booking);
    }

    //custom select
    @Override
    public List<Booking> getBookingByFilmName(String name) {
        List<Booking> optionalBooking = bookingRepository.getBookingByFilmName(name);
        if(optionalBooking.isEmpty()){
            throw new ResourceNotFoundException("Booking not exist with Filmname " +name);
        }
        return this.bookingRepository.getBookingByFilmName(name);
    }

}

