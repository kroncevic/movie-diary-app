package hr.tvz.movie.diary.service.dto;

import hr.tvz.movie.diary.domain.Diary;
import hr.tvz.movie.diary.domain.enumeration.Type;

public class DiaryDTO {

    private Long id;

    private Type type;

    private String title;

    private String year;

    private String genre;

    private String writer;

    private String actors;

    private String plot;

    private String poster;

    private String imdbrating;

    private String impression;

    private String myrating;
    
    public DiaryDTO () {
    	
    }

    public DiaryDTO(Diary diary) {
    	
		this.id = diary.getId();
		this.type = diary.getType();
		this.title = diary.getTitle();
		this.year = diary.getYear();
		this.genre = diary.getGenre();
		this.writer = diary.getWriter();
		this.actors = diary.getActors();
		this.plot = diary.getPlot();
		this.poster = diary.getPoster();
		this.imdbrating = diary.getImdbrating();
		this.impression = diary.getImpression();
		this.myrating = diary.getMyrating();
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getImdbrating() {
        return imdbrating;
    }

    public void setImdbrating(String imdbrating) {
        this.imdbrating = imdbrating;
    }

    public String getImpression() {
        return impression;
    }

    public void setImpression(String impression) {
        this.impression = impression;
    }

    public String getMyrating() {
        return myrating;
    }

    public void setMyrating(String myrating) {
        this.myrating = myrating;
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
