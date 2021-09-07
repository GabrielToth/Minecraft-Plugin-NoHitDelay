package br.toth.nohitdelay.nohitdelay;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


public final class NoHitDelay extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "NoHitDelay by Toth enable");
        getServer().getPluginManager().registerEvents(this, (Plugin)this);
        File f = new File(getDataFolder() + "/config.yml");
        if (!f.exists()) {
            saveResource("config.yml", true);
            saveConfig();
        }
        setup(false);
        // Plugin startup logic

    }

    void setup(boolean deepSearch){
        if(deepSearch){
            reloadConfig();
        }
        try {
            for(Player p : Bukkit.getOnlinePlayers())
                p.setMaximumNoDamageTicks(Integer.parseInt(getConfig().getString("hit-delay")));
        } catch (Exception s) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[NoHitDelay] hit-delay has been reset, no vailed 'hit-delay' set.");
            getConfig().set("hit-delay", Integer.valueOf(7));
            saveConfig();
            for (Player p : Bukkit.getOnlinePlayers())
                p.setMaximumNoDamageTicks(Integer.parseInt(getConfig().getString("hit-delay")));
        }

    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("nohitdelay")) {
            if (!sender.hasPermission("NoHitDelay.admin")) {
                sender.sendMessage(ChatColor.RED + "You don't have permission for this command.");
                return true;
            }
            if (args.length >= 1) {
                if (args.length == 2 && args[0].equalsIgnoreCase("setdelay")) {
                    try {
                        Integer.parseInt(args[1]);
                    } catch (Exception e) {
                        sender.sendMessage(ChatColor.RED + args[1] + " is not a vailed number.");
                        return false;
                    }
                    int i = Integer.parseInt(args[1]);
                    getConfig().set("hit-delay", Integer.valueOf(i));
                    saveConfig();
                    sender.sendMessage(ChatColor.GREEN + "[NoHitDelay] 'hit-delay'has been set to " + i);
                    setup(true);
                } else if (args.length == 2 && args[0].equalsIgnoreCase("setJoinmessage")) {
                    try {
                        Boolean.parseBoolean(args[1].toLowerCase());
                    } catch (Exception e) {
                        sender.sendMessage(ChatColor.RED + " is not 'true' or 'false'");
                        return false;
                    }
                    boolean b = Boolean.parseBoolean(args[1].toLowerCase());
                    getConfig().set("join-msg", Boolean.valueOf(b));
                    saveConfig();
                    sender.sendMessage(ChatColor.GREEN + "[NoHitDelay] 'join-msg'has been set to " + b);
                } else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                    sender.sendMessage(ChatColor.GREEN + "[NoHitDelay] Config Reloaded");
                    setup(true);
                } else if (args.length == 1 && args[0].equalsIgnoreCase("reloadConfig")) {
                    sender.sendMessage(ChatColor.GREEN + "[NoHitDelay] Config Reloaded");
                    setup(true);
                } else {
                    sender.sendMessage("-----------------");
                    sender.sendMessage("/nohitdelay setDelay in ticks");
                    sender.sendMessage("/nohitdelay setJoinMessage" );
                    sender.sendMessage("/nohitdelay reloadConfig");
                    sender.sendMessage("-----------------");
                }
            } else {
                sender.sendMessage("-----------------");
                sender.sendMessage("/nohitdelay setDelay in ticks");
                sender.sendMessage("/nohitdelay setJoinMessage");
                sender.sendMessage("/nohitdelay reloadConfig");
                sender.sendMessage("-----------------");
            }
        }
        return true;
    }
    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "NoHitDelay by Toth disable");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.getPlayer().setMaximumNoDamageTicks(Integer.parseInt(getConfig().getString("hit-delay")));
        if (getConfig().getBoolean("join-msg")) {
            final Player p = e.getPlayer();
            getServer().getScheduler().scheduleSyncDelayedTask((Plugin)this, new Runnable() {
                public void run() {
                    p.getPlayer().sendMessage("server is running NoHitDelay v0.1 developed by Toth");
                }
            },  3L);
        }
    }
    @EventHandler
    public void onMobSpawn(EntitySpawnEvent e) {
        List<EntityType> types = Arrays.asList(EntityType.AXOLOTL, EntityType.BAT, EntityType.BEE, EntityType.BLAZE,
                EntityType.CAT, EntityType.CAVE_SPIDER, EntityType.CHICKEN, EntityType.COD, EntityType.COW,
                EntityType.CREEPER, EntityType.DOLPHIN, EntityType.DONKEY, EntityType.DROWNED,
                EntityType.ELDER_GUARDIAN, EntityType.ENDER_DRAGON, EntityType.ENDERMAN, EntityType.ENDERMITE,
                EntityType.EVOKER, EntityType.FOX, EntityType.GIANT, EntityType.GLOW_SQUID, EntityType.GOAT,
                EntityType.GUARDIAN, EntityType.HOGLIN, EntityType.HORSE, EntityType.HUSK, EntityType.ILLUSIONER,
                EntityType.IRON_GOLEM, EntityType.LLAMA, EntityType.MAGMA_CUBE, EntityType.MULE, EntityType.MUSHROOM_COW,
                EntityType.OCELOT, EntityType.PANDA, EntityType.PARROT, EntityType.PHANTOM, EntityType.PIG,
                EntityType.PIGLIN, EntityType.PIGLIN_BRUTE, EntityType.PILLAGER, EntityType.POLAR_BEAR,
                EntityType.PUFFERFISH, EntityType.RABBIT, EntityType.RAVAGER, EntityType.SALMON, EntityType.SHEEP,
                EntityType.SHULKER, EntityType.SILVERFISH, EntityType.SKELETON, EntityType.SKELETON_HORSE, EntityType.SLIME,
                EntityType.SNOWMAN, EntityType.SPIDER, EntityType.SQUID, EntityType.STRAY, EntityType.STRIDER,
                EntityType.TRADER_LLAMA, EntityType.TROPICAL_FISH, EntityType.TURTLE, EntityType.VILLAGER,
                EntityType.VEX, EntityType.VINDICATOR, EntityType.WANDERING_TRADER, EntityType.WITCH, EntityType.WITHER,
                EntityType.WITHER_SKELETON, EntityType.WOLF, EntityType.ZOMBIE, EntityType.ZOMBIE_HORSE,
                EntityType.ZOMBIE_VILLAGER, EntityType.ZOMBIFIED_PIGLIN);
        if (!types.contains(e.getEntityType())) {
            return;
        }
        LivingEntity entity = (LivingEntity)e.getEntity();
        entity.setMaximumNoDamageTicks(1);
    }
}
