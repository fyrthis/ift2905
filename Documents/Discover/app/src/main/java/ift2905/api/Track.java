package com.example.tanguinoche.discover.mainPackage;

import android.media.MediaPlayer;

/**
 * Created by tanguinoche on 12/04/15.
 */
public class Track {
    private int id;
    private String title;
    private String preview;

    //GETTERS
    public int getID() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getPreview() {
        /*MediaPlayer mediaPlayer;
        mediaPlayer = MediaPlayer.create(VoceActivity.this,Uri.parse(URL_radio));
        mediaPlayer.start();
        */
        return preview;
    }

    //SETTERS
    public void setID(int id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setPreview(String audio) {
        this.preview = audio;
    }
}
