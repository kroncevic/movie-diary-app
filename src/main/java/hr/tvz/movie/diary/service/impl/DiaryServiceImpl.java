package hr.tvz.movie.diary.service.impl;

import hr.tvz.movie.diary.service.DiaryService;
import hr.tvz.movie.diary.service.dto.DiaryDTO;
import hr.tvz.movie.diary.web.rest.errors.BadRequestAlertException;
import hr.tvz.movie.diary.domain.Diary;
import hr.tvz.movie.diary.repository.DiaryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Optional;

/**
 * Service Implementation for managing Diary.
 */
@Service
@Transactional
public class DiaryServiceImpl implements DiaryService {
	
    private static final String ENTITY_NAME = "diary";

    private final Logger log = LoggerFactory.getLogger(DiaryServiceImpl.class);

    private final DiaryRepository diaryRepository;

    public DiaryServiceImpl(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    /**
     * Save a diary.
     *
     * @param diary the entity to save
     * @return the persisted entity
     */
    @Override
    public Diary save(Diary diary) {
        log.debug("Request to save Diary : {}", diary);
        return diaryRepository.save(diary);
    }

    /**
     * Get all the diaries.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Diary> findAll(Pageable pageable) {
        log.debug("Request to get all Diaries");
        return diaryRepository.findAll(pageable);
    }


    /**
     * Get one diary by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Diary> findOne(Long id) {
        log.debug("Request to get Diary : {}", id);
        return diaryRepository.findById(id);
    }

    /**
     * Delete the diary by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Diary : {}", id);
        diaryRepository.deleteById(id);
    }
    
    public Diary createDiaryEntry (DiaryDTO diaryDTO) {
    	
        Diary diary = new Diary();
        try {
			Diary apiDiary = callOmdbAPI(diaryDTO.getTitle());
			
			diary.setTitle(apiDiary.getTitle());
	        diary.setGenre(apiDiary.getGenre());
	        diary.setActors(apiDiary.getActors());
	        diary.setWriter(apiDiary.getWriter());
	        diary.setPlot(apiDiary.getPlot());
	        diary.setPoster(apiDiary.getPoster());
	        diary.setImdbrating(apiDiary.getImdbrating());
	        diary.setYear(apiDiary.getYear());
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        diary.setType(diaryDTO.getType());
        diary.setImpression(diaryDTO.getImpression());
        diary.setMyrating(diaryDTO.getMyrating());
        
        diaryRepository.save(diary);

        log.debug("Created Information for Diary: {}", diary);
        return diary;
    }
    
    public Diary callOmdbAPI (String title) throws IOException {
    	
    	Diary apiDiary = new Diary();
    	
        String sURL = "http://www.omdbapi.com/?i=tt3896198&apikey=345e9e1e&t=" + URLEncoder.encode(title, "UTF-8");

        // Connect to the URL using java's native library
        URL url = new URL(sURL);
        URLConnection request = url.openConnection();
        log.debug("Request to delete Diary : {}", request.getHeaderFields());
        log.debug("Request to delete Diary : {}", request.getURL());
        request.connect();
        
        BufferedReader in = new BufferedReader(new InputStreamReader(
                request.getInputStream()));
        String inputLine = in.readLine();

        // Convert to a JSON object to print data
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(inputLine);
        
        if (jsonNode.has("Error"))
        	throw new BadRequestAlertException("Movie/Series title not found!", ENTITY_NAME, "idexists");
        
        apiDiary.setTitle(jsonNode.get("Title").textValue());
        apiDiary.setYear(jsonNode.get("Year").textValue());
        apiDiary.setActors(jsonNode.get("Actors").textValue());
        apiDiary.setGenre(jsonNode.get("Genre").textValue());
        apiDiary.setImdbrating(jsonNode.get("imdbRating").textValue());
        apiDiary.setPlot(jsonNode.get("Plot").textValue());
        apiDiary.setPoster(jsonNode.get("Poster").textValue());
        apiDiary.setWriter(jsonNode.get("Writer").textValue());
        
        return apiDiary;
   	
    }
   
}
