package com.example.tanguinoche.discover.mainPackage;

import android.graphics.Bitmap;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by tanguinoche on 10/04/15.
 */
public class Artist {

    private int id;
    private static int curId = 0;
    private String name;
    private String genre; //Idem, pourrait stocker l'url du genre ? mais le nom est important(offline)
    private Map<String, Album> tracks; //A vérifier

    private String artistUrl;
    private Bitmap img;

    //CONSTRUCTEUR // A retenir : classes URL, JSONObject
    public Artist(String url) throws IOException, JSONException {
        artistUrl = url;

        DeezerLinkParser.getInstance().setURL(artistUrl);

        try {
            id = DeezerLinkParser.getInstance().getIDFromArtist();
            curId = id;
            name = DeezerLinkParser.getInstance().getNameFromArtist();
            img = DeezerLinkParser.getInstance().getImgFromArtist();
            genre = DeezerLinkParser.getInstance().getGenreFromArtist();
            tracks = DeezerLinkParser.getInstance().getTracksFromArtist();
        }catch(Exception e) {
            Log.d("Artist :", "constructeur");
        }

    }

    private void majParser() {
        if(id != curId) { //Vérification que le parseur est bien attribué à l'artiste courant.
            try {
                DeezerLinkParser.getInstance().setURL(artistUrl);
                curId = id;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    //GETTERS
    public int getID(){
        majParser();
        return id;
    }
    public String getName(){
        majParser();
        return name;
    }
    public Bitmap getImg(){
        majParser();
        return this.img;
    }
    public String getGenre(){//Pas dispo dans l'api.. Aller voir chez developers.music-story.com
        majParser();
        return genre;
    }
    /*Les genres dépendent des albums
    * une solution : si un album passé en paramètre, on retourne e genre de l'album
    * Sinon, on peut retourne la liste complète des genres.
     */

    //De la forme : http://api.deezer.com/artist/27/related
    //Devrait retourner une ArrayList d'artistes
    public List<Artist> getRelated(){ //TODO : related, should be ok
        majParser();
        String[] related = DeezerLinkParser.getInstance().getRelated();
        List<Artist> artistsRelated = new ArrayList<>();
        for(int i = 0; i < related.length ; i++)
            try {
                artistsRelated.add(new Artist(related[i]));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return artistsRelated;
    }
    public void getBiography(){
        //psdjf
    } //Pas dispo dans l'api.. Aller voir chez developers.music-story.com


    public String getDiscography(){
        majParser();
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, Album> entry : tracks.entrySet()) {
            sb.append(entry.getValue().getTitle()); //Nom de l'album
            for(Track t : entry.getValue().getTracks()) {
                sb.append("--");
                sb.append(t.getTitle());
                sb.append("\n");
            }
            sb.append("\n");
        }
        return sb.toString();
    }


    public void putFavorites(){ } //On pourrait utiliser la même pour ajouter/enlever

    public void share(){ } //Android implémente déjà quelque chose, il faut aller voir la doc

}
