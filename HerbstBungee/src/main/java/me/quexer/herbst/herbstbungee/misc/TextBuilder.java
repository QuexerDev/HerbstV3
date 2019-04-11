package me.quexer.herbst.herbstbungee.misc;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class TextBuilder {

    private TextComponent text;

    public TextBuilder(String text) {
        this.text = new TextComponent(text);
        this.text.setText(text);
    }

    public TextBuilder setText(String text) {
        this.text.setText(text);
        return this;
    }

    public TextBuilder setHover(String text) {
        this.text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(text)).create()));
        return this;
    }

    public TextBuilder setClick(String text) {
        this.text.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, "/" + text));
        return this;
    }

    public TextBuilder setClickText(String text) {
        this.text.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.SUGGEST_COMMAND, text));
        return this;
    }

    public TextBuilder addExtra(TextBuilder builder) {
        this.text.addExtra(builder.build());
        return this;
    }

    public void sendToPlayer(ProxiedPlayer p) {
        p.sendMessage(this.text);
    }

    public TextComponent build() {
        return this.text;
    }

}
