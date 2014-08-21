package me.SuperiorCallz.Mianite;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
 
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
 
public class Mianite extends JavaPlugin implements Listener{
 
    public final Logger logger = Logger.getLogger("Minecraft");
    public static Mianite plugin;
 
    public ArrayList<UUID> cantMove = new ArrayList<UUID>();
 
    public List<UUID> isMianite = new ArrayList<UUID>();
    public List<UUID> isDianite = new ArrayList<UUID>();
    public List<UUID> isIanite = new ArrayList<UUID>();
    public HashMap<UUID, Long> coolDown = new HashMap<UUID, Long>();
    public HashMap<UUID, Long> coolDownKill = new HashMap<UUID, Long>();
    public HashMap<UUID, Long> coolDownSpawn = new HashMap<UUID, Long>();
    public HashMap<UUID, Integer> mobKillAmount = new HashMap<UUID, Integer>();
    public HashMap<UUID, Integer> netherKillAmount = new HashMap<UUID, Integer>();
    public HashMap<UUID, Integer> prayPoints = new HashMap<UUID, Integer>();
    public HashMap<UUID, UUID> teleport = new HashMap<UUID, UUID>();
    public PrayerPoints pp;
    public JoinEvents je;
    public TeamPrefixes tp;
    public TeamPVP teamp;
    public Prayers prayers;
    
    @Override
    public void onEnable() {
    	
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() { 
			@Override 
			public void run() { //Shameless plug
				Bukkit.getServer().broadcastMessage(ChatColor.DARK_PURPLE + "Mianite " + ChatColor.DARK_AQUA + "brought to you by " + ChatColor.AQUA + "http://www.youtube.com/SuperiorCallz");
			}
		}, 0, 144000);

    	
        PluginDescriptionFile pdfFile = this.getDescription();
        this.logger.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " has been enabled.");
        this.getCommand("mianite").setExecutor(new Teams(this));
        this.getCommand("dianite").setExecutor(new Teams(this));
        this.getCommand("ianite").setExecutor(new Teams(this));
        this.getCommand("pray").setExecutor(new Prayers(this));
        this.getCommand("praypoints").setExecutor(new CheckPoints(this));
        this.getCommand("devtools").setExecutor(new Developer(this));
        pp = new PrayerPoints(this);
        je = new JoinEvents(this);
        tp = new TeamPrefixes(this);
        teamp = new TeamPVP(this);
        prayers = new Prayers(this);
        getServer().getPluginManager().registerEvents(pp, this);
        getServer().getPluginManager().registerEvents(je, this);
        getServer().getPluginManager().registerEvents(tp, this);
        getServer().getPluginManager().registerEvents(teamp, this);
        getServer().getPluginManager().registerEvents(this, this);
        getConfig().options().copyDefaults(true);
        saveConfig();
        try {
            isMianite = SLAPI.load("isMianite.bin");
            isDianite = SLAPI.load("isDianite.bin");
            isIanite = SLAPI.load("isIanite.bin");
            coolDown = SLAPI.load("coolDown.bin");
            coolDownKill = SLAPI.load("coolDownKill.bin");
            prayPoints = SLAPI.load("prayPoints.bin");
            mobKillAmount = SLAPI.load("mobKillAmount.bin");
            netherKillAmount = SLAPI.load("netherKillAmount.bin");
            } catch(Exception e) {
            //handle the exception
            e.printStackTrace();
        }
    }
 
    @Override
    public void onDisable() {
        PluginDescriptionFile pdfFile = this.getDescription();
        this.logger.info(pdfFile.getName() + " has been disabled.");
        getConfig().options().copyDefaults(true);
        saveConfig();
 
        try {
            SLAPI.save(isMianite,"isMianite.bin");
            SLAPI.save(isDianite,"isDianite.bin");
            SLAPI.save(isIanite,"isIanite.bin");
            SLAPI.save(coolDown,"coolDown.bin");
            SLAPI.save(coolDownKill,"coolDownKill.bin");
            SLAPI.save(prayPoints,"prayPoints.bin");
            SLAPI.save(mobKillAmount,"mobKillAmount.bin");
            SLAPI.save(netherKillAmount,"netherKillAmount.bin");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}