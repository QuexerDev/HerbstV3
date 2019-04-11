package me.quexer.api.quexerapi.misc;

import me.quexer.api.quexerapi.QuexerAPI;
import org.bukkit.Bukkit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncTask {


    public AsyncTask(Runnable run) {
        QuexerAPI.getExecutor().execute(run);
    }
}
