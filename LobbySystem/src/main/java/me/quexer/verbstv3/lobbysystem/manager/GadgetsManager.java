package me.quexer.verbstv3.lobbysystem.manager;

import de.robingrether.idisguise.disguise.DisguiseType;
import me.quexer.api.quexerapi.builder.ItemBuilder;
import me.quexer.herbst.herbstplugin.enums.EnumExtra;
import me.quexer.herbst.herbstplugin.enums.Rarity;
import me.quexer.verbstv3.lobbysystem.LobbySystem;
import me.quexer.verbstv3.lobbysystem.obj.Effekt;
import me.quexer.verbstv3.lobbysystem.obj.Extra;
import me.quexer.verbstv3.lobbysystem.obj.Head;
import me.quexer.verbstv3.lobbysystem.obj.Morph;
import me.quexer.verbstv3.lobbysystem.obj.specialextras.Coins;
import me.quexer.verbstv3.lobbysystem.obj.specialextras.Keys;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class GadgetsManager {

    private List<Extra> extras = new ArrayList<>();
    private HashMap<Rarity, List<Extra>> rarityExtrasMap = new HashMap<>();
    private LobbySystem plugin;

    public GadgetsManager(LobbySystem plugin) {
        this.plugin = plugin;
        register();
    }
    public void register() {
        extras.add(new Head("§4PluginYML", EnumExtra.PLUGINYML, new ItemBuilder(Material.SKULL_ITEM).setSkullOwner("PluginYML").setDurability((short) 3).setName("§4PluginYML").toItemStack(), Rarity.LEGENDARY, plugin));
        extras.add(new Head("§bQuexer", EnumExtra.QUEXER, new ItemBuilder(Material.SKULL_ITEM).setSkullOwner("Quexer").setDurability((short) 3).setName("§bQuexer").toItemStack(), Rarity.EPIC, plugin));
        extras.add(new Head("§5Paluten", EnumExtra.PALUTEN, new ItemBuilder(Material.SKULL_ITEM).setSkullOwner("Paluten").setDurability((short) 3).setName("§5Paluten").toItemStack(), Rarity.RARE, plugin));
        extras.add(new Head("§5ungespielt", EnumExtra.UNGESPIELT, new ItemBuilder(Material.SKULL_ITEM).setSkullOwner("ungespielt").setDurability((short) 3).setName("§5ungespielt").toItemStack(), Rarity.RARE, plugin));
        extras.add(new Head("§5GommeHD", EnumExtra.GOMMEHD, new ItemBuilder(Material.SKULL_ITEM).setSkullOwner("GommeHD").setDurability((short) 3).setName("§5GommeHD").toItemStack(), Rarity.RARE, plugin));
        extras.add(new Head("§5ConCrafter", EnumExtra.CONCRAFTER, new ItemBuilder(Material.SKULL_ITEM).setSkullOwner("ConCrafter").setDurability((short) 3).setName("§5ConCrafter").toItemStack(), Rarity.COMMON, plugin));
        extras.add(new Head("§5EJDAR", EnumExtra.EJDAR, new ItemBuilder(Material.SKULL_ITEM).setSkullOwner("EJDAR").setDurability((short) 3).setName("§5EJDAR").toItemStack(), Rarity.COMMON, plugin));
        extras.add(new Head("§5Chaosflo44", EnumExtra.CHAOSFLO44, new ItemBuilder(Material.SKULL_ITEM).setSkullOwner("Chaosflo44").setDurability((short) 3).setName("§5Chaosflo44").toItemStack(), Rarity.COMMON, plugin));
        extras.add(new Head("§5AbgegrieftHD", EnumExtra.ABGEGRIEFTHD, new ItemBuilder(Material.SKULL_ITEM).setSkullOwner("AbgegrieftHD").setDurability((short) 3).setName("§5AbgegrieftHD").toItemStack(), Rarity.COMMON, plugin));
        extras.add(new Head("§5SkyGuy", EnumExtra.SKYGUY, new ItemBuilder(Material.SKULL_ITEM).setSkullOwner("SkyGuy").setDurability((short) 3).setName("§5SkyGuy").toItemStack(), Rarity.COMMON, plugin));
        extras.add(new Head("§5Phorx", EnumExtra.PHORX, new ItemBuilder(Material.SKULL_ITEM).setSkullOwner("Phorx").setDurability((short) 3).setName("§5Phorx").toItemStack(), Rarity.COMMON, plugin));
        extras.add(new Head("§5SparkOfPhoenix", EnumExtra.SPARKOFPHOENIX, new ItemBuilder(Material.SKULL_ITEM).setSkullOwner("SparkOfPhoenix").setDurability((short) 3).setName("§5SparkOfPhoenix").toItemStack(), Rarity.EPIC, plugin));
        extras.add(new Head("§5ZinusHD", EnumExtra.ZINUSHD, new ItemBuilder(Material.SKULL_ITEM).setSkullOwner("ZinusHD").setDurability((short) 3).setName("§5ZinusHD").toItemStack(), Rarity.RARE, plugin));
        extras.add(new Head("§5rewinside", EnumExtra.REWINSIDE, new ItemBuilder(Material.SKULL_ITEM).setSkullOwner("rewinside").setDurability((short) 3).setName("§5rewinside").toItemStack(), Rarity.COMMON, plugin));
        extras.add(new Head("§5SturmwaffelHD", EnumExtra.STURMWAFFEL, new ItemBuilder(Material.SKULL_ITEM).setSkullOwner("SturmwaffelHD").setDurability((short) 3).setName("§5SturmwaffelHD").toItemStack(), Rarity.COMMON, plugin));

        extras.add(new Effekt("§6Fire", EnumExtra.FIRE, new ItemBuilder(Material.BLAZE_POWDER).setName("§6Fire").toItemStack(), Rarity.LEGENDARY, EnumParticle.FLAME, plugin));
        extras.add(new Effekt("§fSpell", EnumExtra.SPELL, new ItemBuilder(Material.POTION).setName("§fSpell").toItemStack(), Rarity.LEGENDARY, EnumParticle.SPELL, plugin));
        extras.add(new Effekt("§5Hearts", EnumExtra.HEARTS, new ItemBuilder(Material.RED_ROSE).setName("§5Hearts").toItemStack(), Rarity.EPIC, EnumParticle.HEART, plugin));
        extras.add(new Effekt("§dMagic", EnumExtra.MAGIC, new ItemBuilder(Material.IRON_SWORD).setName("§dMagic").toItemStack(), Rarity.EPIC, EnumParticle.CRIT_MAGIC, plugin));
        extras.add(new Effekt("§8Smoke", EnumExtra.SMOKE, new ItemBuilder(Material.SULPHUR).setName("§8Smoke").toItemStack(), Rarity.RARE, EnumParticle.SMOKE_LARGE, plugin));
        extras.add(new Effekt("§aNote", EnumExtra.NOTE, new ItemBuilder(Material.NOTE_BLOCK).setName("§aNote").toItemStack(), Rarity.RARE, EnumParticle.NOTE, plugin));
        extras.add(new Effekt("§cLava", EnumExtra.LAVA, new ItemBuilder(Material.LAVA_BUCKET).setName("§cLava").toItemStack(), Rarity.COMMON, EnumParticle.LAVA, plugin));
        extras.add(new Effekt("§bWater", EnumExtra.WATER, new ItemBuilder(Material.WATER_BUCKET).setName("§bWater").toItemStack(), Rarity.COMMON, EnumParticle.WATER_SPLASH, plugin));
        extras.add(new Effekt("§fSnow", EnumExtra.SNOWBALL, new ItemBuilder(Material.SNOW_BALL).setName("§fSnow").toItemStack(), Rarity.COMMON, EnumParticle.SNOWBALL, plugin));
        extras.add(new Effekt("§cAngry", EnumExtra.ANGRY_VILLAGER, new ItemBuilder(Material.FIREWORK_CHARGE).setName("§cAngry").toItemStack(), Rarity.EPIC, EnumParticle.VILLAGER_ANGRY, plugin));
        extras.add(new Effekt("§fCloud", EnumExtra.CLOUD, new ItemBuilder(Material.WOOL).setName("§fCloud").toItemStack(), Rarity.LEGENDARY, EnumParticle.CLOUD, plugin));
        extras.add(new Effekt("§5Portal", EnumExtra.PORTAL, new ItemBuilder(Material.OBSIDIAN).setName("§5Portal").toItemStack(), Rarity.COMMON, EnumParticle.PORTAL, plugin));

        extras.add(new Morph("§aCreeper", EnumExtra.CREEPER, new ItemBuilder(Material.MONSTER_EGG).setName("§aCreeper").setDurability((short) 50).toItemStack(), Rarity.LEGENDARY, DisguiseType.CREEPER, plugin));
        extras.add(new Morph("§eVillager", EnumExtra.VILLAGER, new ItemBuilder(Material.MONSTER_EGG).setName("§eVillager").setDurability((short) 120).toItemStack(), Rarity.LEGENDARY, DisguiseType.VILLAGER, plugin));
        extras.add(new Morph("§cHorse", EnumExtra.HORSE, new ItemBuilder(Material.MONSTER_EGG).setName("§cHorse").setDurability((short) 100).toItemStack(), Rarity.LEGENDARY, DisguiseType.HORSE, plugin));
        extras.add(new Morph("§fIronGolem", EnumExtra.IRONGOLEM, new ItemBuilder(Material.IRON_INGOT).setName("§fIronGolem").toItemStack(), Rarity.LEGENDARY, DisguiseType.IRON_GOLEM, plugin));
        extras.add(new Morph("§9Squid", EnumExtra.SQUID, new ItemBuilder(Material.MONSTER_EGG).setName("§9Squid").setDurability((short) 94).toItemStack(), Rarity.EPIC, DisguiseType.SQUID, plugin));
        extras.add(new Morph("§2Zombie", EnumExtra.ZOMBIE, new ItemBuilder(Material.MONSTER_EGG).setName("§2Zombie").setDurability((short) 55).toItemStack(), Rarity.EPIC, DisguiseType.ZOMBIE, plugin));
        extras.add(new Morph("§4Cow", EnumExtra.COW, new ItemBuilder(Material.MONSTER_EGG).setName("§4Cow").setDurability((short) 92).toItemStack(), Rarity.EPIC, DisguiseType.COW, plugin));
        extras.add(new Morph("§dPig", EnumExtra.PIG, new ItemBuilder(Material.MONSTER_EGG).setName("§dPig").setDurability((short) 90).toItemStack(), Rarity.EPIC, DisguiseType.PIG, plugin));
        extras.add(new Morph("§2Zombie §dPigmen", EnumExtra.PIGMEN, new ItemBuilder(Material.MONSTER_EGG).setName("§2Zombie §dPigmen").setDurability((short) 57).toItemStack(), Rarity.EPIC, DisguiseType.PIG_ZOMBIE, plugin));
        extras.add(new Morph("§5Witch", EnumExtra.WITCH, new ItemBuilder(Material.MONSTER_EGG).setName("§5Witch").setDurability((short) 66).toItemStack(), Rarity.RARE, DisguiseType.WITCH, plugin));
        extras.add(new Morph("§7Skeleton", EnumExtra.SKELETON, new ItemBuilder(Material.MONSTER_EGG).setName("§7Skeleton").setDurability((short) 51).toItemStack(), Rarity.RARE, DisguiseType.SKELETON, plugin));
        extras.add(new Morph("§cSpider", EnumExtra.SPIDER, new ItemBuilder(Material.MONSTER_EGG).setName("§cSpider").setDurability((short) 52).toItemStack(), Rarity.EPIC, DisguiseType.SPIDER, plugin));
        extras.add(new Morph("§aSlime", EnumExtra.SLIME, new ItemBuilder(Material.MONSTER_EGG).setName("§aSlime").setDurability((short) 55).toItemStack(), Rarity.EPIC, DisguiseType.SLIME, plugin));
        extras.add(new Morph("§5Enderman", EnumExtra.ENDERMAN, new ItemBuilder(Material.MONSTER_EGG).setName("§5Enderman").setDurability((short) 58).toItemStack(), Rarity.LEGENDARY, DisguiseType.ENDERMAN, plugin));
        extras.add(new Morph("§6Blaze", EnumExtra.BLAZE, new ItemBuilder(Material.MONSTER_EGG).setName("§6Blaze").setDurability((short) 61).toItemStack(), Rarity.LEGENDARY, DisguiseType.BLAZE, plugin));
        extras.add(new Morph("§4Magma Slime", EnumExtra.MAGMA_SLIME, new ItemBuilder(Material.MONSTER_EGG).setName("§4Magma Slime").setDurability((short) 62).toItemStack(), Rarity.RARE, DisguiseType.BLAZE, plugin));
        extras.add(new Morph("§3Guardian", EnumExtra.GUARDIAN, new ItemBuilder(Material.MONSTER_EGG).setName("§3Guardian").setDurability((short) 68).toItemStack(), Rarity.EPIC, DisguiseType.GUARDIAN, plugin));
        extras.add(new Morph("§fWolf", EnumExtra.WOLF, new ItemBuilder(Material.MONSTER_EGG).setName("§fWolf").setDurability((short) 95).toItemStack(), Rarity.LEGENDARY, DisguiseType.WOLF, plugin));
        extras.add(new Morph("§cMooshroom Cow", EnumExtra.MOOSHROOM, new ItemBuilder(Material.MONSTER_EGG).setName("§cMooshroom Cow").setDurability((short) 96).toItemStack(), Rarity.RARE, DisguiseType.MUSHROOM_COW, plugin));
        extras.add(new Morph("§eOcelot", EnumExtra.OCELOT, new ItemBuilder(Material.MONSTER_EGG).setName("§eOcelot").setDurability((short) 98).toItemStack(), Rarity.LEGENDARY, DisguiseType.OCELOT, plugin));
        extras.add(new Morph("§eRabbit", EnumExtra.RABBIT, new ItemBuilder(Material.MONSTER_EGG).setName("§eRabbit").setDurability((short) 101).toItemStack(), Rarity.RARE, DisguiseType.RABBIT, plugin));
        extras.add(new Morph("§fSheep", EnumExtra.SHEEP, new ItemBuilder(Material.MONSTER_EGG).setName("§fSheep").setDurability((short) 91).toItemStack(), Rarity.COMMON, DisguiseType.SHEEP, plugin));
        extras.add(new Morph("§8Wither", EnumExtra.WITHER, new ItemBuilder(Material.SKULL_ITEM).setName("§8Wither").toItemStack(), Rarity.LEGENDARY, DisguiseType.WITHER, plugin));

        extras.add(new Coins("§e2.000 Coins", EnumExtra.NORMAL_COINS, new ItemBuilder(Material.GOLD_NUGGET).setName("§e2000 Coins").toItemStack(), Rarity.COMMON, 2000, plugin));
        extras.add(new Coins("§e5.000 Coins", EnumExtra.PREMIUM_COINS, new ItemBuilder(Material.GOLD_INGOT).setName("§e5000 Coins").toItemStack(), Rarity.RARE, 5000, plugin));
        extras.add(new Coins("§e10.000 Coins", EnumExtra.EPIC_COINS, new ItemBuilder(Material.GOLD_BLOCK).setName("§e10000 Coins").toItemStack(), Rarity.EPIC, 10000, plugin));

        extras.add(new Keys("§52 Keys", EnumExtra.NORMAL_KEYS, new ItemBuilder(Material.TRIPWIRE_HOOK, 2).setName("§52 Keys").toItemStack(), Rarity.RARE, 2, plugin));
        extras.add(new Keys("§55 Keys", EnumExtra.PREMIUM_KEYS, new ItemBuilder(Material.TRIPWIRE_HOOK, 5).setName("§55 Keys").toItemStack(), Rarity.EPIC, 5, plugin));
        extras.add(new Keys("§510 Keys", EnumExtra.EPIC_KEYS, new ItemBuilder(Material.TRIPWIRE_HOOK, 10).setName("§510 Keys").toItemStack(), Rarity.LEGENDARY, 10, plugin));

        rarityExtrasMap.put(Rarity.COMMON, getByRarity(Rarity.COMMON));
        rarityExtrasMap.put(Rarity.RARE, getByRarity(Rarity.RARE));
        rarityExtrasMap.put(Rarity.EPIC, getByRarity(Rarity.EPIC));
        rarityExtrasMap.put(Rarity.LEGENDARY, getByRarity(Rarity.LEGENDARY));
    }
    public Extra getByType(EnumExtra extraType) {
        return extras.stream().filter(extra -> extra.getEnumExtra().equals(extraType)).collect(Collectors.toList()).get(0);
    }
    public List<Extra> getByRarity(Rarity rarity) {
        return extras.stream().filter(extra -> extra.getRarity().equals(rarity)).collect(Collectors.toList());
    }
    public Extra getByLogo(ItemStack itemStack) {
        return extras.stream().filter(extra -> extra.getLogo().getItemMeta().getDisplayName().equals(itemStack.getItemMeta().getDisplayName())).collect(Collectors.toList()).get(0);
    }
    public Extra getByName(String name) {
        return extras.stream().filter(extra -> ChatColor.stripColor(extra.getName()).toLowerCase().equals(name.toLowerCase())).collect(Collectors.toList()).get(0);
    }

    public List<Extra> getExtras() {
        return extras;
    }

    public HashMap<Rarity, List<Extra>> getRarityExtrasMap() {
        return rarityExtrasMap;
    }
}
