package infosys.teamd.bioskopapisecutity.Repository;

import infosys.teamd.bioskopapisecutity.DTO.*;
import infosys.teamd.bioskopapisecutity.Model.*;
import infosys.teamd.bioskopapisecutity.Exception.*;
import infosys.teamd.bioskopapisecutity.Response.*;
import infosys.teamd.bioskopapisecutity.Service.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    @Query("Select b from Booking b where b.schedule.films.name like %:name%")
    public List<Booking> getBookingByFilmName(@Param("name")String name);

}

