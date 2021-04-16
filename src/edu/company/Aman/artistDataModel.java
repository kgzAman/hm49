package edu.company.Aman;

import java.util.List;

public class artistDataModel {
   private List<Artist> artists;

    public artistDataModel(List<Artist> artists) {
        this.artists = artists;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }
}
