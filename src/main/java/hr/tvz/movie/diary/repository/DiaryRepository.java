package hr.tvz.movie.diary.repository;

import hr.tvz.movie.diary.domain.Diary;
import hr.tvz.movie.diary.domain.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Diary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
	
    Optional<Diary> findOneByTitle(String title);


}
