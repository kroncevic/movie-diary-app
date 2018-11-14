package hr.tvz.movie.diary.web.rest;

import hr.tvz.movie.diary.MovieDiaryApp;

import hr.tvz.movie.diary.domain.Diary;
import hr.tvz.movie.diary.repository.DiaryRepository;
import hr.tvz.movie.diary.service.DiaryService;
import hr.tvz.movie.diary.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static hr.tvz.movie.diary.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import hr.tvz.movie.diary.domain.enumeration.Type;
/**
 * Test class for the DiaryResource REST controller.
 *
 * @see DiaryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MovieDiaryApp.class)
public class DiaryResourceIntTest {

    private static final Type DEFAULT_TYPE = Type.MOVIE;
    private static final Type UPDATED_TYPE = Type.SERIES;

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_YEAR = "BBBBBBBBBB";

    private static final String DEFAULT_GENRE = "AAAAAAAAAA";
    private static final String UPDATED_GENRE = "BBBBBBBBBB";

    private static final String DEFAULT_WRITER = "AAAAAAAAAA";
    private static final String UPDATED_WRITER = "BBBBBBBBBB";

    private static final String DEFAULT_ACTORS = "AAAAAAAAAA";
    private static final String UPDATED_ACTORS = "BBBBBBBBBB";

    private static final String DEFAULT_PLOT = "AAAAAAAAAA";
    private static final String UPDATED_PLOT = "BBBBBBBBBB";

    private static final String DEFAULT_POSTER = "AAAAAAAAAA";
    private static final String UPDATED_POSTER = "BBBBBBBBBB";

    private static final Float DEFAULT_IMDBRATING = 1F;
    private static final Float UPDATED_IMDBRATING = 2F;

    private static final String DEFAULT_IMPRESSION = "AAAAAAAAAA";
    private static final String UPDATED_IMPRESSION = "BBBBBBBBBB";

    private static final Float DEFAULT_MYRATING = 1F;
    private static final Float UPDATED_MYRATING = 2F;

    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private DiaryService diaryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDiaryMockMvc;

    private Diary diary;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DiaryResource diaryResource = new DiaryResource(diaryService);
        this.restDiaryMockMvc = MockMvcBuilders.standaloneSetup(diaryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Diary createEntity(EntityManager em) {
        Diary diary = new Diary()
            .type(DEFAULT_TYPE)
            .title(DEFAULT_TITLE)
            .year(DEFAULT_YEAR)
            .genre(DEFAULT_GENRE)
            .writer(DEFAULT_WRITER)
            .actors(DEFAULT_ACTORS)
            .plot(DEFAULT_PLOT)
            .poster(DEFAULT_POSTER)
            .imdbrating(DEFAULT_IMDBRATING)
            .impression(DEFAULT_IMPRESSION)
            .myrating(DEFAULT_MYRATING);
        return diary;
    }

    @Before
    public void initTest() {
        diary = createEntity(em);
    }

    @Test
    @Transactional
    public void createDiary() throws Exception {
        int databaseSizeBeforeCreate = diaryRepository.findAll().size();

        // Create the Diary
        restDiaryMockMvc.perform(post("/api/diaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diary)))
            .andExpect(status().isCreated());

        // Validate the Diary in the database
        List<Diary> diaryList = diaryRepository.findAll();
        assertThat(diaryList).hasSize(databaseSizeBeforeCreate + 1);
        Diary testDiary = diaryList.get(diaryList.size() - 1);
        assertThat(testDiary.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDiary.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testDiary.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testDiary.getGenre()).isEqualTo(DEFAULT_GENRE);
        assertThat(testDiary.getWriter()).isEqualTo(DEFAULT_WRITER);
        assertThat(testDiary.getActors()).isEqualTo(DEFAULT_ACTORS);
        assertThat(testDiary.getPlot()).isEqualTo(DEFAULT_PLOT);
        assertThat(testDiary.getPoster()).isEqualTo(DEFAULT_POSTER);
        assertThat(testDiary.getImdbrating()).isEqualTo(DEFAULT_IMDBRATING);
        assertThat(testDiary.getImpression()).isEqualTo(DEFAULT_IMPRESSION);
        assertThat(testDiary.getMyrating()).isEqualTo(DEFAULT_MYRATING);
    }

    @Test
    @Transactional
    public void createDiaryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = diaryRepository.findAll().size();

        // Create the Diary with an existing ID
        diary.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiaryMockMvc.perform(post("/api/diaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diary)))
            .andExpect(status().isBadRequest());

        // Validate the Diary in the database
        List<Diary> diaryList = diaryRepository.findAll();
        assertThat(diaryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDiaries() throws Exception {
        // Initialize the database
        diaryRepository.saveAndFlush(diary);

        // Get all the diaryList
        restDiaryMockMvc.perform(get("/api/diaries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diary.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR.toString())))
            .andExpect(jsonPath("$.[*].genre").value(hasItem(DEFAULT_GENRE.toString())))
            .andExpect(jsonPath("$.[*].writer").value(hasItem(DEFAULT_WRITER.toString())))
            .andExpect(jsonPath("$.[*].actors").value(hasItem(DEFAULT_ACTORS.toString())))
            .andExpect(jsonPath("$.[*].plot").value(hasItem(DEFAULT_PLOT.toString())))
            .andExpect(jsonPath("$.[*].poster").value(hasItem(DEFAULT_POSTER.toString())))
            .andExpect(jsonPath("$.[*].imdbrating").value(hasItem(DEFAULT_IMDBRATING.doubleValue())))
            .andExpect(jsonPath("$.[*].impression").value(hasItem(DEFAULT_IMPRESSION.toString())))
            .andExpect(jsonPath("$.[*].myrating").value(hasItem(DEFAULT_MYRATING.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getDiary() throws Exception {
        // Initialize the database
        diaryRepository.saveAndFlush(diary);

        // Get the diary
        restDiaryMockMvc.perform(get("/api/diaries/{id}", diary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(diary.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR.toString()))
            .andExpect(jsonPath("$.genre").value(DEFAULT_GENRE.toString()))
            .andExpect(jsonPath("$.writer").value(DEFAULT_WRITER.toString()))
            .andExpect(jsonPath("$.actors").value(DEFAULT_ACTORS.toString()))
            .andExpect(jsonPath("$.plot").value(DEFAULT_PLOT.toString()))
            .andExpect(jsonPath("$.poster").value(DEFAULT_POSTER.toString()))
            .andExpect(jsonPath("$.imdbrating").value(DEFAULT_IMDBRATING.doubleValue()))
            .andExpect(jsonPath("$.impression").value(DEFAULT_IMPRESSION.toString()))
            .andExpect(jsonPath("$.myrating").value(DEFAULT_MYRATING.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDiary() throws Exception {
        // Get the diary
        restDiaryMockMvc.perform(get("/api/diaries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiary() throws Exception {
        // Initialize the database
        diaryService.save(diary);

        int databaseSizeBeforeUpdate = diaryRepository.findAll().size();

        // Update the diary
        Diary updatedDiary = diaryRepository.findById(diary.getId()).get();
        // Disconnect from session so that the updates on updatedDiary are not directly saved in db
        em.detach(updatedDiary);
        updatedDiary
            .type(UPDATED_TYPE)
            .title(UPDATED_TITLE)
            .year(UPDATED_YEAR)
            .genre(UPDATED_GENRE)
            .writer(UPDATED_WRITER)
            .actors(UPDATED_ACTORS)
            .plot(UPDATED_PLOT)
            .poster(UPDATED_POSTER)
            .imdbrating(UPDATED_IMDBRATING)
            .impression(UPDATED_IMPRESSION)
            .myrating(UPDATED_MYRATING);

        restDiaryMockMvc.perform(put("/api/diaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDiary)))
            .andExpect(status().isOk());

        // Validate the Diary in the database
        List<Diary> diaryList = diaryRepository.findAll();
        assertThat(diaryList).hasSize(databaseSizeBeforeUpdate);
        Diary testDiary = diaryList.get(diaryList.size() - 1);
        assertThat(testDiary.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDiary.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testDiary.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testDiary.getGenre()).isEqualTo(UPDATED_GENRE);
        assertThat(testDiary.getWriter()).isEqualTo(UPDATED_WRITER);
        assertThat(testDiary.getActors()).isEqualTo(UPDATED_ACTORS);
        assertThat(testDiary.getPlot()).isEqualTo(UPDATED_PLOT);
        assertThat(testDiary.getPoster()).isEqualTo(UPDATED_POSTER);
        assertThat(testDiary.getImdbrating()).isEqualTo(UPDATED_IMDBRATING);
        assertThat(testDiary.getImpression()).isEqualTo(UPDATED_IMPRESSION);
        assertThat(testDiary.getMyrating()).isEqualTo(UPDATED_MYRATING);
    }

    @Test
    @Transactional
    public void updateNonExistingDiary() throws Exception {
        int databaseSizeBeforeUpdate = diaryRepository.findAll().size();

        // Create the Diary

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiaryMockMvc.perform(put("/api/diaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diary)))
            .andExpect(status().isBadRequest());

        // Validate the Diary in the database
        List<Diary> diaryList = diaryRepository.findAll();
        assertThat(diaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDiary() throws Exception {
        // Initialize the database
        diaryService.save(diary);

        int databaseSizeBeforeDelete = diaryRepository.findAll().size();

        // Get the diary
        restDiaryMockMvc.perform(delete("/api/diaries/{id}", diary.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Diary> diaryList = diaryRepository.findAll();
        assertThat(diaryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Diary.class);
        Diary diary1 = new Diary();
        diary1.setId(1L);
        Diary diary2 = new Diary();
        diary2.setId(diary1.getId());
        assertThat(diary1).isEqualTo(diary2);
        diary2.setId(2L);
        assertThat(diary1).isNotEqualTo(diary2);
        diary1.setId(null);
        assertThat(diary1).isNotEqualTo(diary2);
    }
}
