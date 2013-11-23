/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.yournextgig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jason
 */
public class SesameTool {

    private static final Logger LOG = LoggerFactory.getLogger(SesameTool.class);
    String sesameURL;
    String workbenchURL;
    String sesameRepo;
    boolean createRepo;
    private static SesameTool instance = new SesameTool(new Properties());
    public static SesameTool getInstance()
    {
        if(instance == null)
        {
            instance = new SesameTool(new Properties());
        }
        return instance;
    }
    private SesameTool(Properties p) {
        sesameURL = p.getProperty("sesame.url", "http://localhost:8080/openrdf-sesame");
        workbenchURL = p.getProperty("workbench.url", "http://localhost:8080/openrdf-workbench");
        sesameRepo = p.getProperty("sesame.repo", "karma_data");
        createRepo = Boolean.parseBoolean(p.getProperty("create.repo", "false"));

    }

    public JSONArray queryForData(String sparqlQuery) {
        try {
            DefaultHttpClient httpClient;
            HttpResponse response;
            httpClient = new DefaultHttpClient();
            HttpPost hp = new HttpPost(sesameURL + "/repositories/" + sesameRepo);
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            formparams.add(new BasicNameValuePair("query", sparqlQuery));

            hp.setEntity(new UrlEncodedFormEntity(formparams, "UTF-8"));
            hp.setHeader("Accept", "application/sparql-results+json");
            response = httpClient.execute(hp);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                StringBuilder out = new StringBuilder();
                BufferedReader buf = new BufferedReader(new InputStreamReader(entity.getContent()));
                String line = buf.readLine();
                while (line != null) {
                    out.append(line);
                    out.append("\n");
                    line = buf.readLine();
                }
                httpClient.getConnectionManager().shutdown();
                String result = out.toString();
                LOG.trace("query result :" + result);
                JSONObject data = new JSONObject(result);

                JSONArray d1 = data.getJSONObject("results").getJSONArray("bindings");
                return d1;
            }
        }  catch (IOException ex) {
            LOG.error("Unable to query for data: ", ex);
        } catch (JSONException ex) {
            LOG.error("Unable to parse query results for data: ", ex);
        }
        return new JSONArray();
    }
}
