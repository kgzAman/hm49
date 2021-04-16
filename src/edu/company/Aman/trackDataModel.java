package edu.company.Aman;

import java.util.List;

public class trackDataModel {
    private List<Track> tracks;

    public trackDataModel(List<Track> tracks) {
        this.tracks = tracks;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }
}
