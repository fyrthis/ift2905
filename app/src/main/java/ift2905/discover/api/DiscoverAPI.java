package ift2905.discover.api;

/**
 * Created by Julien on 2015-04-21.
 * jcdsjnk,xmzcasmvlkdfs
 */

import android.graphics.drawable.Drawable;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/**
 * Cette classe s'occupe de charger
 * les données de musique depuis l'internet.
 * Elle charge un fichier JSON depuis
 * une URL spécifique, puis interprète
 * ce fichier pour finalement obtenir
 * les valeurs et les charger dans
 * ses variables internes.
 **/

public class DiscoverAPI {
    //String artistName=MainActivity.edit.getText().toString();

    // Sera null s'il n'y a pas d'erreur
    String erreur;

    DiscoverAPI() {

        erreur = null;
        String url = "https://api.deezer.com/search/artist?q=" + artistName + "&limit=2&index=0";
        Log.d("URL", url);

        try {
            // Charge le fichier JSON à l'URL donné depuis le web
            HttpEntity page = getHttp(url);

        } catch (ClientProtocolException e) {
            erreur = "Erreur HTTP (protocole) :" + e.getMessage();
        } catch (IOException e) {
            erreur = "Erreur HTTP (IO) :" + e.getMessage();
        } catch (ParseException e) {
            erreur = "Erreur JSON (parse) :" + e.getMessage();
        } //catch (JSONException e) {
            //erreur = "Erreur JSON :" + e.getMessage();
        //}
    }


    /*
     * Méthode utilitaire qui permet de rapidement
     * charger et obtenir une page web depuis
     * l'internet - Rania
     *
     */
    private HttpEntity getHttp(String url) throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet http = new HttpGet(url);
        HttpResponse response = httpClient.execute(http);
        return response.getEntity();
    }


    /*
     * Méthode utilitaire qui permet
     * d'obtenir une image depuis une URL.
     *
     */
    private Drawable loadHttpImage(String url) throws ClientProtocolException, IOException {
        InputStream is = getHttp(url).getContent();
        Drawable d = Drawable.createFromStream(is, "src");
        return d;
    }
}
