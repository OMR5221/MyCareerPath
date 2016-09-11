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
import java.util.List;

/**
 * Created by ORamirez on 9/10/2016.
 */

// Handle the networking for LikeGallery
// Create an URL object from a string
public class WikiImageFetcher
{
    private static final String TAG = "ImageFetcher";
    private static final String API_KEY = "";


    public byte[] getUrlBytes(String urlSpec) throws IOException
    {
        URL url = new URL(urlSpec);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try
        {
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            InputStream in = connection.getInputStream();

            // If no connection established throw an exception
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)
            {
                throw new IOException(connection.getResponseMessage() + ": with " + urlSpec);
            }

            // Connection established so read in bytes from URL
            int bytesRead = 0;
            byte[] buffer = new byte[1024];

            // Continue to read in bytes into the buffer until none left
            while ((bytesRead = in.read(buffer)) > 0)
            {
                out.write(buffer, 0, bytesRead);
            }

            out.close();

            return out.toByteArray();

        }
        finally
        {
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException
    {
        return new String(getUrlBytes(urlSpec));
    }


    // Parse the items fro Wikipage to get specific element
    private void parseItems(List<WikiPage> items, JSONObject jsonBody) throws IOException, JSONException
    {
        JSONObject pageJsonObject = jsonBody.getJSONObject("pages");

        WikiPage wikiPageItem = new WikiPage();

        JSONArray termsJsonArray= jsonBody.getJSONArray("terms");
        JSONArray imagesJsonArray = jsonBody.getJSONArray("images");
        JSONArray linksJsonArray = jsonBody.getJSONArray("links");

        // Loop through links:
        for (int i = 0; i < imagesJsonArray.length(); i++)
        {
            // Get a link Object:
            JSONObject imageJsonObject = fetchPageItem(imagesJsonArray.getJSONObject(i).getString("title"));

            wikiPageItem.setId();
        }
    }

    public void fetchPageItem(String pageItem)
    {

        try {
            String url = Uri.parse("http://en.wikipedia.org/w/api.php?action=query&titles=" + pageItem + "&prop=imageinfo&iiprop=url&format=json")
                    .buildUpon()
                            //.appendQueryParameter("titles", fetchPage)
                    .build().toString();

            String jsonString = getUrlString(url);

            Log.i(TAG, "Received JSON String: " + jsonString);

            // Create JSONObject from string fetched:
            JSONObject jsonItem = new JSONObject(jsonString);

        }
        catch (JSONException je)
        {
            Log.e(TAG, "Failed to parse JSON", je);
        }
        catch (IOException ioe)
        {
            Log.e(TAG, "Failed to fetch items", ioe);
        }
    }

}
