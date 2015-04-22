package com.example.tanguinoche.discover.mainPackage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tanguinoche on 12/04/15.
 */
public class Album {
    private int id;
    private String title;
    private String cover;
    private String genre;
    private List<Track> tracks;

    public Album() {
        tracks = new ArrayList<>();
    }

    //GETTERS
    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getCover() {
        return cover;
    }
    public String getGenre() {
        return genre;
    }
    public List<Track> getTracks() {
        return tracks;
    }

    //SETTERS
    public void setID(int id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setCover(String cover) {
        this.cover = cover;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public void pushTrack(Track t) {
        tracks.add(t);
    }
}
