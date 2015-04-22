import com.example.tanguinoche.discover.mainPackage.Artist;

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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tanguinoche on 22/04/15.
 */
public class SearchParser {

    //DESIGN PATTERN SINGLETON
    private static volatile SearchParser instance = new SearchParser();
    private SearchParser(){}
    public final static SearchParser getInstance() {
        return instance;
    }
    //FIN SINGLETON

    /* Ouvre une URL contenant un JSON à parser
    * MalformedURLException : url reçue en argument n'en est pas une.
    * IOException : problème I/O, connecté à Internet ? ;)
    * JSONException : problème JSON, c'est bien un fichier JSON ? ;)
    *
    * retoune la liste d'artistes
    */
    public List<Artist> searchArtist(String url) throws MalformedURLException, IOException, JSONException {
        JSONObject file = openURLToJSONObject(new java.net.URL(url));
        JSONArray array = new JSONArray(file.getString("id"));

        List artists = new ArrayList();
        for (int i = 0; i < array.length(); i++) {
            JSONObject artist = new JSONObject(array.getString(i));
            artists.add(new Artist("http://api.deezer.com/artist/"+artist.getString("id"))); //Ici : On a push les nouveaux artistes dans l'arraylist
        }
        return artists;
    }

    /* retourne liste artistes associés à un genre (A partir d'une radio) */

    /*Ouvre un JSON à partir d'une URL */
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
}
