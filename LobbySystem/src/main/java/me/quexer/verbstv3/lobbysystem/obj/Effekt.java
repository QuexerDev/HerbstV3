package me.quexer.verbstv3.lobbysystem.obj;

import me.quexer.api.quexerapi.builder.ItemBuilder;
import me.quexer.api.quexerapi.misc.Particle;
import me.quexer.herbst.herbstplugin.enums.EnumExtra;
import me.quexer.herbst.herbstplugin.enums.Rarity;
import me.quexer.verbstv3.lobbysystem.LobbySystem;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

public class Effekt extends Extra {

    private EnumParticle particle;

    private BukkitTask bukkitTask;
    private LobbySystem plugin;

    public Effekt(String name, EnumExtra enumExtra, ItemStack logo, Rarity rarity, EnumParticle particle, LobbySystem plugin) {
        super(name, enumExtra, new ItemBuilder(logo).setLore("§a","§7§lTyp§8: §eEffekt","§a","§7§lSeltenheit§8: "+rarity.getName()).toItemStack(), rarity, plugin);
        this.plugin = plugin;
        this.particle = particle;
    }

    @Override
    public void equip(Player player) {
        if(player.hasMetadata("effekt")) {
            ((Effekt)player.getMetadata("effekt").get(0).value()).getBukkitTask().cancel();
            plugin.getQuexerAPI().removeMetadata(player, "effekt");
        }
            player.sendMessage(LobbySystem.getLobbyPrefix() + "§7Du hast den §eEffekt§8: §e" + getName() + " §7ausgerüstet§8!");

            bukkitTask = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
                Particle particle = new Particle(getParticle(), player.getLocation(), false, 0.5F, 0, 0.5F, 0.1F, 10);
                Bukkit.getOnlinePlayers().forEach(o -> {
                    if(!o.hasMetadata("hider")) {
                        particle.sendPlayer(o);
                    } else {
                        particle.sendPlayer(player);
                    }
                });
            }, 1, 3);
            player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 0.3F, 0.3F);
            plugin.getQuexerAPI().setMetadata(player, "effekt", this);

    }

    @Override
    public void unequip(Player player) {
        if(player.hasMetadata("effekt")) {
            ((Effekt) player.getMetadata("effekt").get(0).value()).getBukkitTask().cancel();
            plugin.getQuexerAPI().removeMetadata(player, "effekt");
            player.sendMessage(LobbySystem.getLobbyPrefix() + "§cDu hast den §eEffekt§8: §e" + getName() + " §centfernt§8!");
            player.playSound(player.getLocation(), Sound.NOTE_BASS, 0.3F, 0.3F);
        }
    }

    public EnumParticle getParticle() {
        return particle;
    }

    public BukkitTask getBukkitTask() {
        return bukkitTask;
    }
}
