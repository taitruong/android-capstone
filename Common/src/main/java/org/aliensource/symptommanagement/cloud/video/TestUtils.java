package org.aliensource.symptommanagement.cloud.video;

import org.aliensource.symptommanagement.cloud.video.client.VideoSvcApi;
import org.aliensource.symptommanagement.cloud.video.repository.Video;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

public class TestUtils {

    public static final String PROTOCOL = "https";
    public static final String PORT = "8443";
    public static final String USER_DOCTOR1 = "doctor1";
    public static final String USER_DOCTOR1_PASS = "pass";

    private static final String[] POSSIBLE_LOCALHOSTS = {
            "192.168.1.5", // my laptop
            "10.0.2.2", // Android Emulator
            "192.168.56.1" // Genymotion
    };

    public static String findTheRealLocalhostAddress() {
/*
        String realLocalHost = null;

        for(String localhost : POSSIBLE_LOCALHOSTS) {
            try {
                System.out.println(">>>>> " + localhost);
                URL url = new URL("https://"+localhost+":8443" + VideoSvcApi.TOKEN_PATH);
                URLConnection con = url.openConnection();
                con.setConnectTimeout(500);
                con.setReadTimeout(500);
                InputStream in = con.getInputStream();
                if (in != null){
                    realLocalHost = localhost;
                    in.close();
                    break;
                }
            } catch (Exception e) {e.printStackTrace();}
        }

        return realLocalHost;
*/
        return "192.168.1.5";

    }

    public static long randomVideoDuration(){
        return (long)Math.rint(Math.random() * 1000);
    }

    public static Video randomVideo() {
        return new Video(
                randomVideoName(),
                "http://" + randomVideoName(),
                randomVideoDuration());
    }

    public static String randomVideoName() {
        return UUID.randomUUID().toString();
    }

}

