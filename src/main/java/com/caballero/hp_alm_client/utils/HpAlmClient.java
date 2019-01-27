package com.caballero.hp_alm_client.utils;

import com.caballero.hp_alm_client.model.*;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import sun.misc.BASE64Encoder;

import javax.ws.rs.core.*;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HpAlmClient {

    private Config config;
    private List<Cookie> cookies;

    private final String DEFAULT_ENTITY_TYPE = MediaType.APPLICATION_XML;

    public HpAlmClient(Config config) throws Exception {
        this.config = config;
        cookies = new ArrayList<>();
        login(config.username(), config.password());
    }

    private void login(String username, String password) throws ResponseException {
        String url = String.format("http://%s/qcbin/api/authentication/sign-in", config.host());

        MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();

        String authString = username + ":" + password;
        String authStringEnc = new BASE64Encoder().encode(authString.getBytes());

        headers.add("Authorization", "Basic " + authStringEnc);

        Response response = RestClient.getInstance().post(url, headers, null, null);

        if (response.getStatus() == 200) {
            Map<String, NewCookie> responseCookies = response.getCookies();
            cookies.add(responseCookies.get("QCSession").toCookie());
            cookies.add(responseCookies.get("LWSSO_COOKIE_KEY").toCookie());
        } else {
            throw new ResponseException(response, url);
        }

    }

    public Test loadTest(String testId) throws ResponseException {
        String url = String.format(
                "http://%s/qcbin/rest/domains/%s/projects/%s/tests/%s",
                config.host(), config.domain(), config.project(), testId
        );

        MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();

        Response response = RestClient.getInstance().get(url, headers, cookies, null);

        if (response.hasEntity()) {

            Entity entity = response.readEntity(Entity.class);

            return new Test(entity);

        } else {
            throw new ResponseException(response, url);
        }
    }

    public TestSet loadTestSet(String testSetId) throws ResponseException {
        String url = String.format(
                "http://%s/qcbin/rest/domains/%s/projects/%s/test-sets/%s",
                config.host(), config.domain(), config.project(), testSetId
        );

        MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();

        Response response = RestClient.getInstance().get(url, headers, cookies, null);

        if (response.hasEntity()) {

            Entity entity = response.readEntity(Entity.class);

            return new TestSet(entity);

        } else {
            throw new ResponseException(response, url);
        }
    }

    public TestSet saveTestSet(TestSet testSet) throws ResponseException {
        String url = String.format(
                "http://%s/qcbin/rest/domains/%s/projects/%s/test-sets/",
                config.host(), config.domain(), config.project()
        );

        MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();

        javax.ws.rs.client.Entity payload = javax.ws.rs.client.Entity.entity(jaxbObjectToXML(testSet), DEFAULT_ENTITY_TYPE);

        Response response = RestClient.getInstance().post(url, headers, cookies, payload);

        if (response.hasEntity()) {

            Entity entity = response.readEntity(Entity.class);

            return new TestSet(entity);
        } else {
            throw new ResponseException(response, url);
        }
    }

    public TestInstance createTestInstance(TestInstance testInstance) throws ResponseException {
        String url = String.format(
                "http://%s/qcbin/rest/domains/%s/projects/%s/test-instances",
                config.host(), config.domain(), config.project()
        );

        MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();

        javax.ws.rs.client.Entity payload = javax.ws.rs.client.Entity.entity(jaxbObjectToXML(testInstance), DEFAULT_ENTITY_TYPE);

        Response response = RestClient.getInstance().post(url, headers, cookies, payload);

        if (response.hasEntity()) {

            Entity entity = response.readEntity(Entity.class);

            return new TestInstance(entity);

        } else {
            throw new ResponseException(response, url);
        }
    }

    public TestInstance updateTestInstance(TestInstance testInstance) throws ResponseException {
        String url = String.format(
                "http://%s/qcbin/rest/domains/%s/projects/%s/test-instances/%s",
                config.host(), config.domain(), config.project(), testInstance.id()
        );

        MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();

        javax.ws.rs.client.Entity payload = javax.ws.rs.client.Entity.entity(jaxbObjectToXML(testInstance), DEFAULT_ENTITY_TYPE);

        Response response = RestClient.getInstance().put(url, headers, cookies, payload);

        if (response.hasEntity()) {

            Entity entity = response.readEntity(Entity.class);

            return new TestInstance(entity);

        } else {
            throw new ResponseException(response, url);
        }
    }

    public List<Run> loadRunByTestInstance(String testInstanceId) throws ResponseException {
        String url = String.format(
                "http://%s/qcbin/rest/domains/%s/projects/%s/runs",
                config.host(), config.domain(), config.project()
        );

        Map<String, String> queryParams = new HashMap<>();

        queryParams.put("query", "{testcycl-id[" + testInstanceId + "]}");

        Map<String, String> headers = new HashMap<>();
        HttpResponse<String> response;

        try {
            response = RestClient.getInstance().getAlternative(url, headers, cookies, queryParams);

            String xml = response.getBody();

            Entities entities = JAXB.unmarshal(new StringReader(xml), Entities.class);

            List<Run> result = new ArrayList<>();

            for (Object e : entities.entities()){
                result.add(new Run((Entity) e));
            }

            return result;
        } catch (UnirestException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Run createRun(Run run) throws ResponseException {
        String url = String.format(
                "http://%s/qcbin/rest/domains/%s/projects/%s/runs",
                config.host(), config.domain(), config.project()
        );

        MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();

        javax.ws.rs.client.Entity payload = javax.ws.rs.client.Entity.entity(jaxbObjectToXML(run), DEFAULT_ENTITY_TYPE);

        Response response = RestClient.getInstance().post(url, headers, cookies, payload);

        if (response.hasEntity()) {

            Entity entity = response.readEntity(Entity.class);

            return new Run(entity);

        } else {
            throw new ResponseException(response, url);
        }
    }

    public List<RunStep> loadRunStepsByRun(String runId) throws ResponseException {
        String url = String.format(
                "http://%s/qcbin/rest/domains/%s/projects/%s/run-steps",
                config.host(), config.domain(), config.project()
        );

        Map<String, String> queryParams = new HashMap<>();

        queryParams.put("query", "{parent-id[" + runId + "]}");
        queryParams.put("order-by", "{execution-date[ASC]}");
        queryParams.put("fields", "id,name,status,parent-id");

        Map<String, String> headers = new HashMap<>();
        HttpResponse<String> response;

        try {
            response = RestClient.getInstance().getAlternative(url, headers, cookies, queryParams);

            String xml = response.getBody();
            Entities entities = JAXB.unmarshal(new StringReader(xml), Entities.class);

            List<RunStep> result = new ArrayList<>();

            for (Object e : entities.entities()){
                result.add(new RunStep((Entity) e));
            }

            return result;
        } catch (UnirestException e) {
            e.printStackTrace();
            return null;
        }
    }

    public RunStep updateRunStep(RunStep runStep) throws ResponseException {
        String url = String.format(
                "http://%s/qcbin/rest/domains/%s/projects/%s/run-steps/%s",
                config.host(), config.domain(), config.project(), runStep.id()
        );

        MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();

        javax.ws.rs.client.Entity payload = javax.ws.rs.client.Entity.entity(jaxbObjectToXML(runStep), DEFAULT_ENTITY_TYPE);

        Response response = RestClient.getInstance().put(url, headers, cookies, payload);

        if (response.hasEntity()) {

            String xml = response.readEntity(String.class);

            Entity entity = JAXB.unmarshal(new StringReader(xml), Entity.class);
            return new RunStep(entity);

        } else {
            throw new ResponseException(response, url);
        }
    }

    public Attachment uploadAttachmentToTestInstance(String testInstanceId, File attachment) {
        String url = String.format(
                "http://%s/qcbin/rest/domains/%s/projects/%s/test-instances/%s/attachments",
                config.host(), config.domain(), config.project(), testInstanceId
        );

        Map<String, String> headers = new HashMap<>();

        try {
            HttpResponse<String> response = RestClient.getInstance().postFile(url, headers, cookies, attachment);

            Entity entity = JAXB.unmarshal(new StringReader(response.getBody()), Entity.class);

            return new Attachment(entity);

        } catch (URISyntaxException | UnirestException | IOException e) {
            return null;
        }
    }

    public static String jaxbObjectToXML(Entity entity) {
        String xmlString = "";
        try {
            JAXBContext context = JAXBContext.newInstance(Entity.class);
            Marshaller m = context.createMarshaller();

            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // To format XML

            StringWriter sw = new StringWriter();
            m.marshal(entity, sw);
            xmlString = sw.toString();

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return xmlString;
    }
}
