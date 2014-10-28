package com.ov3rk1ll.kinocast.api.mirror;

import com.ov3rk1ll.kinocast.utils.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class NowVideo extends Host {
    private static final String TAG = NowVideo.class.getSimpleName();
    public static final int HOST_ID = 40;

    public NowVideo(int id) {
        super(id);
    }

    @Override
    public String getVideoPath() {
        try {
            String id = url.substring(url.lastIndexOf("/") + 1);
            Document doc = Jsoup.connect("http://www.nowvideo.sx/mobile/video.php?id=" + id)
                    .userAgent(Utils.USER_AGENT)
                    .timeout(3000)
                    .get();

            return doc.select("source[type=video/mp4]").attr("src");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
