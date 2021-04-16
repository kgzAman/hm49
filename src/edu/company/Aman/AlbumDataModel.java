package edu.company.Aman;

import java.util.List;

public class AlbumDataModel {
    List<Album>  albums;

    public AlbumDataModel(List<Album> albums) {
        this.albums = albums;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }
}
