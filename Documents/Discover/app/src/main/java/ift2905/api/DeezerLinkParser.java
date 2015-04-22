package com.example.tanguinoche.discover.mainPackage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tanguinoche on 10/04/15.
 */
final class DeezerLinkParser {
    private URL url;
    private JSONObject file;

    //DESIGN PATTERN SINGLETON
    private static volatile DeezerLinkParser instance = new DeezerLinkParser();
    private DeezerLinkParser(){}
    public final static DeezerLinkParser getInstance() {
        return instance;
    }
    //FIN SINGLETON

    /* Ouvre une URL contenant un JSON à parser
    * MalformedURLException : url reçue en argument n'en est pas une.
    * IOException : problème I/O, connecté à Internet ? ;)
    * JSONException : problème JSON, c'est bien un fichier JSON ? ;)
    */
    public void setURL(String url) throws MalformedURLException, IOException, JSONException {
        this.url = new java.net.URL(url);
        this.file = openURLToJSONObject(this.url);
    }

    /*Retourne le JSON associé à l'URL, attention ne correspond pas forcément à celui de l'artiste */
    private JSONObject openURLToJSONObject(URL url) throws IOException, JSONException {
        InputStream is = url.openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            String jsonText = sb.toString();
            return new JSONObject(jsonText);
        } finally {
            is.close();
        }
    }

    //GETTERS
    public int getIDFromArtist() throws JSONException { //OK
        return file.getInt("id");
    }
    public String getNameFromArtist() throws JSONException {
        return file.getString("name");
    }
    public Bitmap getImgFromArtist() throws JSONException, IOException {
        String imgUrl = file.getString("picture");
        InputStream in = new URL(imgUrl).openStream();
        Bitmap img = BitmapFactory.decodeStream(in);

        return img;
    }
    public String getGenreFromArtist() { //TODO !!!!!!!!!!!!!!
        return null;
    }

    /* Attention à l'artiste courant ..! */
    public String[] getRelated() {
        String[] result = {null, null, null, null, null};

        try {
            URL relatedUrl = new URL(url.toString()+"/related");
            JSONObject related = openURLToJSONObject(relatedUrl);
            JSONArray relatedArray = new JSONArray(related.getString("data"));
            for(int i = 0; i < 5 ; i++)
                result[i] = new JSONObject(relatedArray.getString(i)).getString("related");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /* Ne devrait pas renvoyer d'exception à priori, sauf si la connexion à Internet est perdue */
    public Map<String,Album> getTracksFromArtist() throws IOException, JSONException {

        URL albumsUrl = new URL(url.toString()+"/albums");
        Map<String,Album> albumsMap = new HashMap<>();

        try {
            //Contient tous les albums
            JSONObject albums = openURLToJSONObject(albumsUrl);
            JSONArray albumsArray = new JSONArray(albums.getString("data"));
            for (int i = 0; i < albumsArray.length(); i++) {
                //Contient un unique album
                JSONObject album = new JSONObject(albumsArray.getString(i));
                Album a = new Album();
                albumsMap.put(album.getString("id"), a);
                a.setID(album.getInt("id"));
                a.setTitle(album.getString("title"));
                a.setCover(album.getString("cover"));
                a.setGenre(album.getString("genre_id"));
                //Contient toutes les tracks de l'album
                JSONObject tracks = openURLToJSONObject(new URL("http://api.deezer.com/album/" + album.getString("id") + "/tracks"));
                JSONArray tracksArray = new JSONArray(tracks.getString("data"));
                for (int j = 0; j < tracksArray.length(); j++) {
                    //Contient une track
                    JSONObject track = new JSONObject(tracksArray.getString(j));
                    Track t = new Track();
                    t.setID(track.getInt("id"));
                    t.setTitle(track.getString("title"));
                    t.setPreview(track.getString("preview"));
                    a.pushTrack(t);
                }
            }
        }catch(Exception e){ Log.d("Parser :", "getTracksFromArtists"); }
        return albumsMap;
    }
}
