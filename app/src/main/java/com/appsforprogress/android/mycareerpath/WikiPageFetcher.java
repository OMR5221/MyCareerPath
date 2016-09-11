package com.appsforprogress.android.mycareerpath;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ORamirez on 7/2/2016.
 */
public class WikiPageFetcher
{

    private static final String TAG = "WikiFetcher";
    // private static final String API_KEY;

    public byte[] getUrlBytes(String urlSpec) throws IOException
    {
        // Get and connect to URL specified:
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            // Communication streams:
            // a. out: Write data
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            // b. in: Read data
            InputStream in = connection.getInputStream();

            // Check connection success:
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)
            {
                throw new IOException(connection.getResponseMessage() + ": with " + urlSpec);
            }

            int bytesRead = 0;

            byte[] buffer = new byte[1024];

            // While there is still data to read from the buffer then continue processing
            while ( (bytesRead = in.read(buffer)) > 0 )
            {
                out.write(buffer, 0, bytesRead);
            }

            out.close();

            return out.toByteArray();
        }
        finally {

            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException
    {
        return new String(getUrlBytes(urlSpec));
    }


    public WikiPage fetchPage(String likePage)
    {
        WikiPage wikiPage = new WikiPage();

        try
        {
            String url = Uri.parse("https://en.wikipedia.org/w/api.php?action=query&format=json&prop=extracts%7Cpageterms%7Clinks%7Cimageinfo%7Cpageimages&titles=moon+(film)&redirects=1&converttitles=1&exsentences=3&exintro=1&explaintext=1&wbptterms=description%7Clabel&plnamespace=100%7C8%7C2600&pllimit=100&iiprop=url%7Cuserid%7Cdimensions&piprop=thumbnail%7Cname%7Coriginal" + likePage)
                    .buildUpon()
                    //.appendQueryParameter("titles", fetchPage)
                    .build().toString();

            String jsonString = getUrlString(url);

            Log.i(TAG, "Received JSON String: " + jsonString);

            // Create JSONObject from string fetched:
            JSONObject jsonPage = new JSONObject(jsonString);

            parsePage(items, jsonPage);

        }
        catch (JSONException je)
        {
            Log.e(TAG, "Failed to parse JSON", je);
        }
        catch (IOException ioe)
        {
            Log.e(TAG, "Failed to fetch items", ioe);
        }

        return items;
    }


    // get the Wikipedia Page entry containing links, images, description.
    private void parsePage(List<WikiPage> items, JSONObject jsonBody) throws IOException, JSONException
    {
        JSONObject pageJsonObject = jsonBody.getJSONObject("pages");

        JSONArray termsJsonArray= jsonBody.getJSONArray("terms");
        JSONArray imagesJsonArray = jsonBody.getJSONArray("images");
        JSONArray linksJsonArray = jsonBody.getJSONArray("links");

        // Loop through links:
        for (int i = 0; i < imagesJsonArray.length(); i++)
        {
            // Get a link Object:
            JSONObject imageJsonObject = imagesJsonArray.getJSONObject(i);

            WikiPage wikiPageItem = new WikiPage();

            wikiPageItem.setId(pageJsonObject.getString("pageid"));
            wikiPageItem.setTitle(pageJsonObject.getString("title"));
            wikiPageItem.setSummary(pageJsonObject.getString("extract"));
        }
    }

}
