package infosys.teamd.bioskopapisecurity.Service;

import infosys.teamd.bioskopapisecurity.Model.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface SeatsService {

    List<Seats> findAllseats();
    Optional<Seats> findbyid(Long id);
    Seats createseat(Seats seat);
    Seats updateseat(Seats seat, Long seatId);
    void deleteseat(Long seatId);
    Seats getReferenceById (Long id);
    List<Seats> getSeatAvailable(Integer isAvailable);
    List<Seats> getSeatAvailableNew(Integer isAvailable);
    Page<Seats> findPaginatedSeats(int pageNo, int pageSize);
}
