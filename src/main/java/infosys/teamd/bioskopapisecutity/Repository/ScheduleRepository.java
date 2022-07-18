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

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    @Query("Select s from Schedule s where s.films.name =?1")
    public List<Schedule> getScheduleByFilmName(String name);

    @Query("Select s from Schedule s where s.films.name like %:name%")
    public List<Schedule> getScheduleFilmsNameLike (@Param("name")String name);

}