package hr.tvz.movie.diary.service;

import hr.tvz.movie.diary.config.Constants;
import hr.tvz.movie.diary.domain.Authority;
import hr.tvz.movie.diary.domain.Diary;
import hr.tvz.movie.diary.domain.User;
import hr.tvz.movie.diary.service.dto.DiaryDTO;
import hr.tvz.movie.diary.service.dto.UserDTO;
import hr.tvz.movie.diary.service.util.RandomUtil;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service Interface for managing Diary.
 */
public interface DiaryService {

    /**
     * Save a diary.
     *
     * @param diary the entity to save
     * @return the persisted entity
     */
    Diary save(Diary diary);

    /**
     * Get all the diaries.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Diary> findAll(Pageable pageable);


    /**
     * Get the "id" diary.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Diary> findOne(Long id);

    /**
     * Delete the "id" diary.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
    
    Diary createDiaryEntry (DiaryDTO diaryDTO);
}
