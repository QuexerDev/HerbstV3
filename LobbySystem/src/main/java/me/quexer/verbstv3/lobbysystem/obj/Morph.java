package me.quexer.verbstv3.lobbysystem.obj;

import de.robingrether.idisguise.disguise.Disguise;
import de.robingrether.idisguise.disguise.DisguiseType;
import de.robingrether.idisguise.disguise.MobDisguise;
import me.quexer.api.quexerapi.builder.ItemBuilder;
import me.quexer.herbst.herbstplugin.enums.EnumExtra;
import me.quexer.herbst.herbstplugin.enums.Rarity;
import me.quexer.verbstv3.lobbysystem.LobbySystem;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import sun.plugin2.main.server.Plugin;

public class Morph extends Extra{

    private DisguiseType disguiseType;
    private LobbySystem plugin;

    public Morph(String name, EnumExtra enumExtra, ItemStack logo, Rarity rarity, DisguiseType disguiseType, LobbySystem plugin) {
        super(name, enumExtra, new ItemBuilder(logo).setLore("§a","§7§lTyp§8: §eMorph","§a","§7§lSeltenheit§8: "+rarity.getName()).toItemStack(), rarity, plugin);
        this.plugin = plugin;
        this.disguiseType = disguiseType;
    }

    @Override
    public void equip(Player player) {
        if(player.hasMetadata("morph")) {
            plugin.getDisguiseAPI().undisguise(player);
            plugin.getQuexerAPI().removeMetadata(player, "morph");
        }
        MobDisguise mobDisguise = new MobDisguise(getDisguiseType());
        mobDisguise.setCustomName(player.getDisplayName());
        mobDisguise.setVisibility(Disguise.Visibility.EVERYONE);
        mobDisguise.setCustomNameVisible(true);
        plugin.getDisguiseAPI().disguise(player, mobDisguise);
        player.sendMessage(LobbySystem.getLobbyPrefix()+"§7Du hast den §eMorph§8: §e"+ getName()+" §7ausgerüstet§8!");
        plugin.getQuexerAPI().setMetadata(player, "morph", this);

    }

    @Override
    public void unequip(Player player) {
        if(player.hasMetadata("morph")) {
            player.sendMessage(LobbySystem.getLobbyPrefix() + "§cDu hast den §eMorph§8: §e" + getName() + " §centfernt§8!");
            plugin.getDisguiseAPI().undisguise(player);
            plugin.getQuexerAPI().removeMetadata(player, "morph");
            player.playSound(player.getLocation(), Sound.NOTE_BASS, 0.3F, 0.3F);
        }
    }

    public DisguiseType getDisguiseType() {
        return disguiseType;
    }


}
