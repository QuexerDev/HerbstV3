package me.quexer.herbst.herbstbungee.misc;


import me.quexer.herbst.herbstbungee.HerbstBungee;

public class AsyncTask {


    public AsyncTask(Runnable run) {
        HerbstBungee.getInstance().getExecutor().execute(run);
    }
}
