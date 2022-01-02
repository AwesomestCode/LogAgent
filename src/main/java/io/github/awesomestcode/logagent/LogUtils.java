package io.github.awesomestcode.logagent;

import okhttp3.*;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class LogUtils {
    private static LogUtils instance;
    private OkHttpClient client;
    private String serverName;

    private String payloadString;

    private LogUtils(String lokiServer) {
        this.server = lokiServer;
    }

    /**
     * The initialisation method is called by the main class to provide config.
     */
    static void initialiseInstance(String lokiServer, String serverName, String host, Logger logger) {
        instance = new LogUtils(lokiServer);
        instance.client = new OkHttpClient();
        instance.serverName = serverName;
        instance.payloadString = "{\n" +
                "    \"streams\": [\n" +
                "        {\n" +
                "            \"labels\": \"{source=\\\"" +  serverName + "\\\",job=\\\"" + serverName +"\\\", host=\\\"" + host + "\\\"}\",\n" +
                "            \"entries\": [\n" +
                "                {\n" +
                "                    \"ts\": \"{currentTime}\",\n" +
                "                    \"line\": \"{message}\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        logger.info("Initialised LogUtils.");
    }

    public static LogUtils getInstance() {
        if(instance == null) throw new RuntimeException("Instance wasn't properly initialised!");
        return instance;
    }

    private final String server;

    public void log(String text) throws IOException {
        // System.out.println("Logging " + text  + " to Loki");
        RequestBody body = RequestBody.create(payloadString.replace("{currentTime}", ZonedDateTime.now(ZoneId.of("America/New_York")).format( DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .replace("{message}", text),
                MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(server + "/api/prom/push")
                .post(body)
                .build();

        ResponseBody responseBody = client.newCall(request).execute().body();

        // System.out.println("Received back " + responseBody.string() + "from Loki.");
        responseBody.close();

    }

}
