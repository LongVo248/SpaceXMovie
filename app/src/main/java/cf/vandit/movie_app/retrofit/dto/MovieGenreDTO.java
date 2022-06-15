package cf.vandit.movie_app.retrofit.dto;

public class MovieGenreDTO {
    private int id;
    private String name;

    public MovieGenreDTO() {
    }

    public MovieGenreDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
