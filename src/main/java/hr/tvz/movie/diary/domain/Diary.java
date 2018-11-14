package hr.tvz.movie.diary.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;

import hr.tvz.movie.diary.domain.enumeration.Type;

/**
 * A Diary.
 */
@Entity
@Table(name = "diary")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Diary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private Type type;

    @NotNull
    @Column(name = "title")
    private String title;

    @Column(name = "jhi_year")
    private String year;

    @Column(name = "genre")
    private String genre;

    @Column(name = "writer")
    private String writer;

    @Column(name = "actors")
    private String actors;

    @Column(name = "plot")
    private String plot;

    @Column(name = "poster")
    private String poster;

    @Column(name = "imdbrating")
    private String imdbrating;

    @Column(name = "impression")
    private String impression;

    @Column(name = "myrating")
    private String myrating;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public Diary type(Type type) {
        this.type = type;
        return this;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public Diary title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public Diary year(String year) {
        this.year = year;
        return this;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public Diary genre(String genre) {
        this.genre = genre;
        return this;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getWriter() {
        return writer;
    }

    public Diary writer(String writer) {
        this.writer = writer;
        return this;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getActors() {
        return actors;
    }

    public Diary actors(String actors) {
        this.actors = actors;
        return this;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPlot() {
        return plot;
    }

    public Diary plot(String plot) {
        this.plot = plot;
        return this;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getPoster() {
        return poster;
    }

    public Diary poster(String poster) {
        this.poster = poster;
        return this;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getImdbrating() {
        return imdbrating;
    }

    public Diary imdbrating(String imdbrating) {
        this.imdbrating = imdbrating;
        return this;
    }

    public void setImdbrating(String imdbrating) {
        this.imdbrating = imdbrating;
    }

    public String getImpression() {
        return impression;
    }

    public Diary impression(String impression) {
        this.impression = impression;
        return this;
    }

    public void setImpression(String impression) {
        this.impression = impression;
    }

    public String getMyrating() {
        return myrating;
    }

    public Diary myrating(String myrating) {
        this.myrating = myrating;
        return this;
    }

    public void setMyrating(String myrating) {
        this.myrating = myrating;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Diary diary = (Diary) o;
        if (diary.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), diary.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Diary{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", title='" + getTitle() + "'" +
            ", year='" + getYear() + "'" +
            ", genre='" + getGenre() + "'" +
            ", writer='" + getWriter() + "'" +
            ", actors='" + getActors() + "'" +
            ", plot='" + getPlot() + "'" +
            ", imdbrating=" + getImdbrating() +
            ", impression='" + getImpression() + "'" +
            ", myrating=" + getMyrating() +
            "}";
    }
}
