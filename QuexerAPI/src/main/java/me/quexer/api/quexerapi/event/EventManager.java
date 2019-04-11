package me.quexer.api.quexerapi.event;

import me.quexer.api.quexerapi.QuexerAPI;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author LeonEnkn
 *
 * Copyright (c) 2015 - 2018 by ShortByte.me to present. All rights reserved.
 */
public class EventManager implements Listener {

    private final Plugin plugin;

    private final HashMap<EventListener, CopyOnWriteArrayList<ListenerExecutor>> executors;
    private final HashMap<String, CommandExecutor> commands;

    private final List<String> deniedCommands;

    public EventManager() {
        this.plugin = QuexerAPI.getInstance();
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.executors = new HashMap<>();
        this.commands = new HashMap<>();
        this.deniedCommands = new ArrayList<>();
    }

    //<editor-fold defaultstate="collapsed" desc="registerEvent">
    public void registerEvent(Class<? extends Event> cls, EventListener listener) {
        ListenerExecutor executor = new ListenerExecutor(cls, listener);

        this.plugin.getServer().getPluginManager().registerEvent(cls, new Listener() {

        }, EventPriority.NORMAL, executor, this.plugin);
        if(!this.executors.containsKey(listener))
            this.executors.put(listener, new CopyOnWriteArrayList<>());
        this.executors.get(listener).add(executor);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="unregisterEvent">
    public void unregisterEvent(Class<? extends Event> cls, EventListener listener) {
        if(!this.executors.containsKey(listener))
            return;
        this.executors.get(listener).stream().filter((executor) -> (executor.getListener().equals(listener))).forEach((executor) -> {
            executor.setDisable(true);
        });
    }
    //</editor-fold>

    public interface EventListener<T extends Event> {

        public void on(T event);
    }
}

