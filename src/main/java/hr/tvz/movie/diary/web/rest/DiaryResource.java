package hr.tvz.movie.diary.web.rest;

import com.codahale.metrics.annotation.Timed;
import hr.tvz.movie.diary.domain.Diary;
import hr.tvz.movie.diary.repository.DiaryRepository;
import hr.tvz.movie.diary.service.DiaryService;
import hr.tvz.movie.diary.service.dto.DiaryDTO;
import hr.tvz.movie.diary.service.dto.UserDTO;
import hr.tvz.movie.diary.web.rest.errors.BadRequestAlertException;
import hr.tvz.movie.diary.web.rest.errors.LoginAlreadyUsedException;
import hr.tvz.movie.diary.web.rest.util.HeaderUtil;
import hr.tvz.movie.diary.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Diary.
 */
@RestController
@RequestMapping("/api")
public class DiaryResource {

    private final Logger log = LoggerFactory.getLogger(DiaryResource.class);

    private static final String ENTITY_NAME = "diary";

    private final DiaryService diaryService;
    
    private final DiaryRepository diaryRepository;

    public DiaryResource(DiaryService diaryService, DiaryRepository diaryRepository) {
        this.diaryService = diaryService;
        this.diaryRepository = diaryRepository;
    }

    /**
     * POST  /diaries : Create a new diary.
     *
     * @param diary the diary to create
     * @return the ResponseEntity with status 201 (Created) and with body the new diary, or with status 400 (Bad Request) if the diary has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/diaries")
    @Timed
    public ResponseEntity<Diary> createDiary(@RequestBody DiaryDTO diaryDTO) throws URISyntaxException {
    	
        log.debug("REST request to save Diary : {}", diaryDTO);
        
        if (diaryDTO.getId() != null) {
            throw new BadRequestAlertException("A new diary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        else if (diaryRepository.findOneByTitle(diaryDTO.getTitle().toLowerCase()).isPresent()) {
            throw new BadRequestAlertException("A new diary cannot already have an title", ENTITY_NAME, "idexists");
        }
        else {     	
        	Diary result = diaryService.createDiaryEntry(diaryDTO);
        	return ResponseEntity.created(new URI("/api/diaries/" + result.getId()))
        			.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
        			.body(result);
		}
    }

    /**
     * PUT  /diaries : Updates an existing diary.
     *
     * @param diary the diary to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated diary,
     * or with status 400 (Bad Request) if the diary is not valid,
     * or with status 500 (Internal Server Error) if the diary couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/diaries")
    @Timed
    public ResponseEntity<Diary> updateDiary(@RequestBody Diary diary) throws URISyntaxException {
        log.debug("REST request to update Diary : {}", diary);
        if (diary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Diary result = diaryService.save(diary);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, diary.getId().toString()))
            .body(result);
    }

    /**
     * GET  /diaries : get all the diaries.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of diaries in body
     */
    @GetMapping("/diaries")
    @Timed
    public ResponseEntity<List<Diary>> getAllDiaries(Pageable pageable) {
        log.debug("REST request to get a page of Diaries");
        Page<Diary> page = diaryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/diaries");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /diaries/:id : get the "id" diary.
     *
     * @param id the id of the diary to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the diary, or with status 404 (Not Found)
     */
    @GetMapping("/diaries/{id}")
    @Timed
    public ResponseEntity<Diary> getDiary(@PathVariable Long id) {
        log.debug("REST request to get Diary : {}", id);
        Optional<Diary> diary = diaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(diary);
    }

    /**
     * DELETE  /diaries/:id : delete the "id" diary.
     *
     * @param id the id of the diary to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/diaries/{id}")
    @Timed
    public ResponseEntity<Void> deleteDiary(@PathVariable Long id) {
        log.debug("REST request to delete Diary : {}", id);
        diaryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
