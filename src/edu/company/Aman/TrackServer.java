package edu.company.Aman;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.sun.net.httpserver.HttpExchange;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import server.BasicServer;
import server.ContentType;
import server.ResponseCodes;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrackServer extends BasicServer {
    private final static Configuration freemarker = initFreeMarker();

    List<Artist> artists= new ArrayList<>();
    List<Album> albums = new ArrayList<>();
    List<Track> track = new ArrayList<>();

    protected TrackServer(String host, int port) throws IOException {
        super(host, port);
        registerGet("/artist",this::artistsHandler);
        registerGet("/album",this::albumHandler);
        initMembers();
        initAlbums();

    }

    private void initMembers() throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader("data\\json\\artist.json"));
        artists= Arrays.asList(gson.fromJson(reader,Artist[].class));
        int id = 0;
        for (Artist artist : artists) {
            artist.setId(id++);
            id +=1;
        }
    }

    private void initAlbums() throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader("data\\json\\album.json"));
        albums= Arrays.asList(gson.fromJson(reader,Album[].class));
        int id = 0;
        for (Album album : albums) {
            album.setId(id++);
            id +=1;
        }
    }
    private void artistsHandler(HttpExchange exchange) {
        artistDataModel artistDataModel = new artistDataModel(artists);
        renderTemplate(exchange,"artist.ftl",artistDataModel);
    }
    private void albumHandler(HttpExchange exchange) {
        albumDataModel albumDataModel = new albumDataModel(albums);
        renderTemplate(exchange,"album.ftl",albumDataModel);
    }


    private static Configuration initFreeMarker() {
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
            cfg.setDirectoryForTemplateLoading(new File("data"));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            cfg.setWrapUncheckedExceptions(true);
            cfg.setFallbackOnNullLoopVariable(false);
            return cfg;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void renderTemplate(HttpExchange exchange, String templateFile, Object dataModel) {
        try {
            Template temp = freemarker.getTemplate(templateFile);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            try (OutputStreamWriter writer = new OutputStreamWriter(stream)) {
                temp.process(dataModel, writer);
                writer.flush();
                var data = stream.toByteArray();
                sendByteData(exchange, ResponseCodes.OK, ContentType.TEXT_HTML, data);
            }
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

}
