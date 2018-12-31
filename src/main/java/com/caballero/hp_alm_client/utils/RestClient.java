package com.caballero.hp_alm_client.utils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.*;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;


public class RestClient {

    private static RestClient instance = new RestClient();

    private Client client;

    private RestClient() {
        client = ClientBuilder.newClient(new ClientConfig());
    }

    public static RestClient getInstance() {
        return instance;
    }

    public Response get(String url, MultivaluedMap<String, Object> headers, List<Cookie> cookieList, Map<String, String> queryParams) {

        WebTarget webTarget = client.target(url);

        if (queryParams != null) {
            for (Map.Entry<String, String> entry : queryParams.entrySet()) {
                webTarget = webTarget.queryParam(entry.getKey(), entry.getValue());
            }
        }

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_XML);


        System.out.println(webTarget.getUri());

        invocationBuilder.headers(headers);

        for (Cookie c : cookieList) {
            invocationBuilder.cookie(c);
        }

        return invocationBuilder.get();

    }

    public HttpResponse<String> getAlternative(String url, Map<String, String> headers, List<Cookie> cookieList, Map<String, String> queryParams) throws UnirestException {

        String cookieString = "";

        if (cookieList != null) {
            for (Cookie c : cookieList) {
                cookieString += c.getName() + "=" + c.getValue() + "; ";
            }
        }

        GetRequest request = Unirest.get(url)
                .header("accept", "application/xml");

        if (cookieString.length() > 1) {
            request.header("Cookie", cookieString);
        }

        if (queryParams != null && !queryParams.isEmpty()){
            for(Map.Entry<String,String> entry : queryParams.entrySet()){
                request.queryString(entry.getKey(), entry.getValue());
            }
        }

        if (headers != null && !headers.isEmpty()) {
            request.headers(headers);
        }

        return request.asString();

    }

    public Response post(String url, MultivaluedMap<String, Object> headers, List<Cookie> cookieList, Entity payload) {

        WebTarget webTarget = client.target(url);

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_XML);

        invocationBuilder.headers(headers);

        if (cookieList != null) {
            for (Cookie c : cookieList) {
                invocationBuilder.cookie(c);
            }
        }

        return invocationBuilder.post(payload);

    }

    public HttpResponse<String> postFile(String url, Map<String, String> headers, List<Cookie> cookieList, File file) throws URISyntaxException, FileNotFoundException, UnirestException {

        String cookieString = "";

        if (cookieList != null) {
            for (Cookie c : cookieList) {
                cookieString += c.getName() + "=" + c.getValue() + "; ";
            }
        }

        HttpRequestWithBody request = Unirest.post(url)
                .header("Content-Type", MediaType.APPLICATION_OCTET_STREAM)
                .header("Slug", file.getName())
                .header("accept", MediaType.APPLICATION_XML);

        if (cookieString.length() > 1) {
            request.header("Cookie", cookieString);
        }

        if (headers != null && !headers.isEmpty()) {
            request.headers(headers);
        }

        request.field("file", file);

        return request.asString();
    }

    public Response put(String url, MultivaluedMap<String, Object> headers, List<Cookie> cookieList, Entity payload) {

        WebTarget webTarget = client.target(url);

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_XML);

        invocationBuilder.headers(headers);

        if (cookieList != null) {
            for (Cookie c : cookieList) {
                invocationBuilder.cookie(c);
            }
        }

        return invocationBuilder.put(payload);

    }

}
