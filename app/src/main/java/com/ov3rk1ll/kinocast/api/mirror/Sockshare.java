package com.ov3rk1ll.kinocast.api.mirror;

import com.ov3rk1ll.kinocast.ui.DetailActivity;

public class Sockshare extends Host {
    private static final String TAG = Sockshare.class.getSimpleName();
    public static final int HOST_ID = 5;

    public Sockshare(int id) {
        super(id);
    }

    @Override
    public String getVideoPath(DetailActivity.QueryPlayTask queryTask) {
        //TODO implement Sockshare
        return null;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
