package infosys.teamd.bioskopapisecurity.Service;

import infosys.teamd.bioskopapisecurity.Model.Films;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface FilmsService {
   List<Films> findAllFilms();
   Optional<Films> findbyId(Long filmId);
   Films createFilm(Films films);
   Films updateFilm(Films films, Long filmId);
   void deleteFilmById(Long id);
   List<Films> getByIsPlaying(Integer isPlaying);
   List<Films> getIsPlaying(Integer isPlaying);
   Films getReferenceById (Long id);
   Page<Films> findPaginatedFilms(int pageNo, int pageSize);


}