package com.ov3rk1ll.kinocast.api.mirror;

import android.os.SystemClock;
import android.util.Log;

import com.ov3rk1ll.kinocast.utils.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StreamCloud extends Host {
    private static final String TAG = StreamCloud.class.getSimpleName();
    public static final int HOST_ID = 30;

    public StreamCloud(int id) {
        super(id);
    }

    @Override
    public String getVideoPath() {
        Pattern pattern = Pattern.compile("http:\\/\\/streamcloud\\.eu\\/(.*)\\/(.*)\\.html");
        Matcher matcher = pattern.matcher(url);
        Log.d(TAG, "resolve " + url);
        if (matcher.find() && matcher.groupCount() == 2) {
            Log.d(TAG, "Request player [id:" + matcher.group(1) + ", fname: " + matcher.group(2) + "]");
            String link = getLink(url, matcher.group(1), matcher.group(2));
            Log.d(TAG, "1st Request. Got " + link);
            if(link != null) return link;
            Log.d(TAG, "single request failed. Waiting 10s and retry.");
            SystemClock.sleep(11 * 1000);
            link = getLink(url, matcher.group(1), matcher.group(2));
            Log.d(TAG, "2nd Request. Got " + link);
            return link;
        }
        return null;
    }

    private String getLink(String url, String id, String fname){
        try {
            // op=download1&usr_login=&id=kp95f217fxwr&fname=Bones.S01E01.DVDRip.XviD-TOPAZ.avi
            // &referer=http%3A%2F%2Fwww.kinox.to%2FStream%2FBones.html
            // &hash=&imhuman=Weiter+zum+Video
            Document doc = Jsoup.connect(url)
                    .data("op", "download1")
                    .data("id", id)
                    .data("fname", fname)
                    .data("imhuman", "Weiter zum Video")
                    .data("usr_login", "")
                    .data("referer", "") // http://www.kinox.to
                    .data("hash", "")
                    .cookie("playermode", "html5")
                    .cookie("lang", "german")
                    .userAgent(Utils.USER_AGENT)
                    .timeout(3000)
                    .post();
            Pattern p = Pattern.compile("file: \\\"(.*)\\/video\\.mp4\\\",");
            Matcher m = p.matcher(doc.html());
            if(m.find()){
                return m.group(1) + "/video.mp4";
            } else {
                Log.d(TAG, "file-pattern not found in '" + doc.select("script:contains(mediaplayer)").html() + "'");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
