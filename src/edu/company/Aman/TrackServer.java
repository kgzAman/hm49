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
import server.Utils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

public class TrackServer extends BasicServer {
    private final static Configuration freemarker = initFreeMarker();

    List<Track> tracks = initTracks();;
    List<Album> albums = tracks.stream().map(Track::getAlbum).distinct().collect(Collectors.toList());
    List<Artist> artists = albums.stream().map(Album::getArtist).distinct().collect(Collectors.toList());


    protected TrackServer(String host, int port) throws IOException {
        super(host, port);
        System.out.println(albums);


        registerGet("/tracks",this::TracksHandler);
        registerGet("/albums",this::AlbumHandler);
        registerGet("/artist",this::ArtistHandler);

        registerPost("/artist",this::registerArtist);

    }

    private List<Track> initTracks() throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader("data\\json\\tracks.json"));



        return Arrays.asList(gson.fromJson(reader,Track[].class));
    }

    private void TracksHandler(HttpExchange exchange){
        TracksModel tracksModel = new TracksModel(tracks);
        renderTemplate(exchange,"track.ftl",tracksModel);
    }

    private void AlbumHandler(HttpExchange exchange){
        AlbumDataModel albumDataModel = new AlbumDataModel(albums);
            renderTemplate(exchange,"album.ftl",albumDataModel);
    }
    private void ArtistHandler(HttpExchange exchange){
        ArtistDataModel artistDataModel = new ArtistDataModel(artists);
            renderTemplate(exchange,"artist.ftl",artistDataModel);
    }
    private void registerArtist(HttpExchange exchange) {
        String cType = getContentType(exchange);
        String raw = getBody(exchange);
        Map<String, String> parsed = Utils.parseUrlEncoded(raw, "&");
        StringBuilder id = new StringBuilder(parsed.get("id"));

        int ide = 0;
        for (Artist artist : artists) {
            artist.setId(ide++);
            id.append(1);
        }
        String path = ("/artist?id=" + id);
        redirect303(exchange, path);

    }

    public static String getContentType(HttpExchange exchange) {
        return exchange.getRequestHeaders()
                .getOrDefault("Content-Type", List.of(""))
                .get(0);
    }
    protected String getBody(HttpExchange exchange) {
        InputStream input = exchange.getRequestBody();
        Charset utf8 = StandardCharsets.UTF_8;
        InputStreamReader isr = new InputStreamReader(input, utf8);
        try (BufferedReader reader = new BufferedReader(isr)) {
            return reader.lines().collect(joining(""));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
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

    protected void redirect303(HttpExchange exchange, String path) {
        try {
            exchange.getResponseHeaders().add("Location", path);
            exchange.sendResponseHeaders(303, 0);
            exchange.getResponseBody().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
