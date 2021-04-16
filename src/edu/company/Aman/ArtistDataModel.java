package edu.company.Aman;

import java.util.List;

public class ArtistDataModel {
    List<Artist> artists;

    public ArtistDataModel(List<Artist> artists) {
        this.artists = artists;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }
}
