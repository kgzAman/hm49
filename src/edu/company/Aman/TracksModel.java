package edu.company.Aman;

import java.util.List;

public class TracksModel {

    private List<Track> tracks;

    public TracksModel(List<Track> tracks) {
        this.tracks = tracks;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }
}
