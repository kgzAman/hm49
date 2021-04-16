package edu.company.Aman;

import java.util.List;

public class albumDataModel {
    private List<Album> albums;

    public albumDataModel(List<Album> albums) {
        this.albums = albums;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }
}
