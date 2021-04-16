package edu.company.Aman;

public class Track {
    private Integer id;
    private String name;
    private Album album;
    private String duration;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }
}
