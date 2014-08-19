package me.SuperiorCallz.Mianite;
 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Logger;
 









import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Monster;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
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
 
    @Override
    public void onEnable() {
    	
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() { 
			@Override 
			public void run() {
				Bukkit.getServer().broadcastMessage(ChatColor.RED + "Mianite " + ChatColor.DARK_AQUA + "brought to you by " + ChatColor.AQUA + "http://www.youtube.com/SuperiorCallz");
			}
		}, 0, 144000);

    	
        PluginDescriptionFile pdfFile = this.getDescription();
        this.logger.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " has been enabled.");
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
 
    //Careful. If you don't like shameless plugs, don't continue :P
    
    @EventHandler
    public void move(PlayerMoveEvent move)
    {
        //Check if player is in the cantMove list, and if so, don't allow them to move.
        if(!move.getPlayer().hasPermission("mianite.canMove")){
            if(cantMove.contains(move.getPlayer().getUniqueId())){
                Location from=move.getFrom();
                Location to=move.getTo();
                double x=Math.floor(from.getX());
                double z=Math.floor(from.getZ());
                if(Math.floor(to.getX())!=x||Math.floor(to.getZ())!=z)
                {
                    x+=.5;
                    z+=.5;
                    move.getPlayer().teleport(new Location(from.getWorld(),x,from.getY(),z,from.getYaw(),from.getPitch()));
                }
                move.getPlayer().sendMessage(ChatColor.AQUA + "You may not move until a team has been picked. " + ChatColor.GREEN + "/mianite," + ChatColor.DARK_RED + " /dianite, " + ChatColor.YELLOW + "/ianite");
            }
        }
    }
 
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
      if(!event.getPlayer().hasPlayedBefore()) {
        event.getPlayer().sendMessage(ChatColor.AQUA + "Welcome to the world of Mianite. Pick your team. " + ChatColor.GREEN + "/mianite," + ChatColor.DARK_RED + " /dianite, " + ChatColor.YELLOW + "/ianite");
        cantMove.add(event.getPlayer().getUniqueId());
      }
    }
 
    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        Player player = (Player) sender;
        if(cmd.getName().equalsIgnoreCase("mianite")){
            if(!(isDianite.contains(player.getUniqueId()) || isMianite.contains(player.getUniqueId()) || isIanite.contains(player.getUniqueId()))){
                //Remove player from cantMove list.
                cantMove.remove(player.getPlayer().getUniqueId());
                //Add player to isMianite list.
                isMianite.add(player.getUniqueId());
                //getConfig().addDefault("Mianite Followers", this.isMianite);
                player.sendMessage(ChatColor.AQUA + "Congratulations. You are now following " + ChatColor.GREEN + "Mianite");
 
                //Teleport player to coordinates set in config as "mianite:x:y:z
                String[] rawValues = getConfig().getString("mianite").split(" ");
                int x, y, z;
                try {
                    x = Integer.parseInt(rawValues[0]);
                    y = Integer.parseInt(rawValues[1]);
                    z = Integer.parseInt(rawValues[2]);
                    Location tpLoc = new Location (Bukkit.getWorlds().get(0), (double) (x), (double) (y), (double) (z));
                    player.teleport(tpLoc); /*this is whoever issued the command*/
                } catch(NullPointerException e) {
                    //If the config is fucked up it goes here.
                }
            }else{
                player.sendMessage(ChatColor.DARK_RED + "You are already in a team.");
            }
 
        }
 
        if(cmd.getName().equalsIgnoreCase("dianite")){
            if(!(isDianite.contains(player.getUniqueId()) || isMianite.contains(player.getUniqueId()) || isIanite.contains(player.getUniqueId()))){
                cantMove.remove(player.getPlayer().getUniqueId());
                isDianite.add(player.getUniqueId());;
                //String uid = player.getUniqueId().toString();
                //getConfig().addDefault("Dianite Followers", uid);
                //saveConfig();
                player.sendMessage(ChatColor.AQUA + "Congratulations. You are now following " + ChatColor.DARK_RED + "Dianite");
 
                String[] rawValues = getConfig().getString("dianite").split(" ");
                int x, y, z;
                try {
                    x = Integer.parseInt(rawValues[0]);
                    y = Integer.parseInt(rawValues[1]);
                    z = Integer.parseInt(rawValues[2]);
                    Location tpLoc = new Location (Bukkit.getWorlds().get(0), (double) (x), (double) (y), (double) (z));
                    player.teleport(tpLoc); /*this is whoever issued the command*/
                } catch(NullPointerException e) {
                    //If the config is fucked up it goes here.
                }
            }else{
                player.sendMessage(ChatColor.DARK_RED + "You are already in a team.");
            }
 
        }
 
        if(cmd.getName().equalsIgnoreCase("ianite")){
            if(!(isDianite.contains(player.getUniqueId()) || isMianite.contains(player.getUniqueId()) || isIanite.contains(player.getUniqueId()))){
                cantMove.remove(player.getPlayer().getUniqueId());
                isIanite.add(player.getUniqueId());
                getConfig().addDefault("Ianite Followers", this.isIanite);
                saveConfig();
                player.sendMessage(ChatColor.AQUA + "Congratulations. You are now following " + ChatColor.YELLOW + "Ianite");
 
                String[] rawValues = getConfig().getString("ianite").split(" ");
                int x, y, z;
                try {
                    x = Integer.parseInt(rawValues[0]);
                    y = Integer.parseInt(rawValues[1]);
                    z = Integer.parseInt(rawValues[2]);
                    Location tpLoc = new Location (Bukkit.getWorlds().get(0), (double) (x), (double) (y), (double) (z));
                    player.teleport(tpLoc); /*this is whoever issued the command*/
                } catch(NullPointerException e) {
                    //If the config is fucked up it goes here.
                }
            }else{
                player.sendMessage(ChatColor.DARK_RED + "You are already in a team.");
            }
 
        }
 
        if(cmd.getName().equalsIgnoreCase("pray") || cmd.getName().equalsIgnoreCase("prayer")){
            if(args.length == 0){
                player.sendMessage(ChatColor.DARK_PURPLE + "You must specify a prayer type. These are found in the book of Mianite.");
            }else{
                if(args[0].equalsIgnoreCase("kill")){
                    if(isMianite.contains(player.getUniqueId()) || isDianite.contains(player.getUniqueId()) || isIanite.contains(player.getUniqueId())){
                        if(args.length == 1){
                            player.sendMessage(ChatColor.RED + "You must specify a player.");
                        }else{
                            int coolDownTime = 259200;
                            if(coolDown.containsKey(player.getUniqueId())){
                                long secondsLeft = ((coolDown.get(player.getUniqueId())/1000)+coolDownTime) - (System.currentTimeMillis()/1000);
 
                                if(secondsLeft > 0){
                                    sender.sendMessage(ChatColor.RED + "You may not pray for kills to the Gods for another " + ChatColor.AQUA + secondsLeft + " seconds.");
                                    return true;
                                }
                            }
                            coolDown.put(player.getUniqueId(), System.currentTimeMillis());
                            Player target = sender.getServer().getPlayer(args[1]);
                            if (target == null){
                                sender.sendMessage(ChatColor.AQUA + args[1] + ChatColor.RED + " is not currently online.");
                                return true;
                            }
                            Random random = new Random();
                            int randomChance = 100;    //100 %
                            final int DROP_NUMBER = random.nextInt(randomChance);
                            if (DROP_NUMBER <= 45) {
                                target.sendMessage(ChatColor.DARK_RED + "A fellow Mianitee, who calls himself " + ChatColor.AQUA + target.getName() + ChatColor.DARK_RED +  ", has prayed to his God for your death.");
                                target.setHealth(0);
                                sender.sendMessage(ChatColor.GREEN + "Your God has faithfully accepted your prayer. " + ChatColor.RED + args[1] + ChatColor.GREEN + " has died.");
                            }else{
                                sender.sendMessage(ChatColor.DARK_RED + "Your God has denied your prayer.");
                            }
                            return true;
                        }
                    }else{
                        player.sendMessage(ChatColor.RED + "You must have a God to pray.");
                    }
                }else if(args[0].equalsIgnoreCase("location") || args[0].equalsIgnoreCase("loc")){
                    if(isMianite.contains(player.getUniqueId()) || isDianite.contains(player.getUniqueId()) || isIanite.contains(player.getUniqueId())){
                        if(args.length == 1){
                            player.sendMessage(ChatColor.RED + "You must specify a player.");
                        }else{
                            if(prayPoints.containsKey(player.getUniqueId())){
                                if(prayPoints.get(player.getUniqueId()) >= 3){
                                    prayPoints.put(player.getUniqueId(), prayPoints.get(player.getUniqueId()) - 3);
                                    Player playerLoc = Bukkit.getPlayer(args[1]);
                                        if(playerLoc != null && playerLoc.isOnline()){
                                            sender.sendMessage(ChatColor.RED + "3 prayer points reducted. The coordinates are: " + ChatColor.AQUA + "x " + playerLoc.getLocation().getBlockX() + " y " + playerLoc.getLocation().getBlockY() + " x " + playerLoc.getLocation().getBlockZ());
                                        }
                                    }else{
                                    	player.sendMessage(ChatColor.RED + "You must have 3 or more prayer points.");
                                    }
                                }else{
                                    player.sendMessage(ChatColor.RED + "You do not yet have any prayer points.");
                                }
                            }
                        }
                    }else if(args[0].equalsIgnoreCase("item") || args[0].equalsIgnoreCase("spawn") || args[0].equalsIgnoreCase("give")){
                        if(isMianite.contains(player.getUniqueId()) || isDianite.contains(player.getUniqueId()) || isIanite.contains(player.getUniqueId())){
                            if(args.length == 1){
                                player.sendMessage(ChatColor.RED + "You must specify an item.");
                            }else{
                                if(prayPoints.containsKey(player.getUniqueId())){
                                    if(prayPoints.get(player.getUniqueId()) >= 10){
                                        Material material = Material.matchMaterial(args[1]);
                                        if(material != null){
                                            prayPoints.put(player.getUniqueId(), prayPoints.get(player.getUniqueId()) - 10);
                                            ItemStack itemStack = new ItemStack(material, (int) (10*Math.random()+3));
                                            player.getInventory().addItem(itemStack);
                                            player.sendMessage(ChatColor.RED + "You have received a random amount of " + ChatColor.AQUA +  material + ChatColor.RED + ". 10 prayer points have been reducted.");
                                        }else{
                                            player.sendMessage(ChatColor.RED + "The item you prayed for does not exist.");
                                        }
                                    }else{
                                        player.sendMessage(ChatColor.RED + "You do not have 10 prayer points.");
                                    }
                                }else{
                                    player.sendMessage(ChatColor.RED + "You do not yet have any prayer points.");
                                }
                            }
                        }else{
                            player.sendMessage(ChatColor.RED + "You must have a God to pray.");
                        }
                    }else if(args[0].equalsIgnoreCase("tpa") || args[0].equalsIgnoreCase("tp")){
                        if(isMianite.contains(player.getUniqueId()) || isDianite.contains(player.getUniqueId()) || isIanite.contains(player.getUniqueId())){
                            if(args.length == 1){
                                player.sendMessage(ChatColor.RED + "You must specify a player you would like to request a teleport to.");
                            }else{
                                if(prayPoints.containsKey(player.getUniqueId())){
                                    if(prayPoints.get(player.getUniqueId()) >= 1){
                                        if(Bukkit.getServer().getPlayer(args[1]).isOnline() && Bukkit.getServer().getPlayer(args[1]) != null){
                                            prayPoints.put(player.getUniqueId(), prayPoints.get(player.getUniqueId()) - 1);
                                            Player target = (Bukkit.getServer().getPlayer(args[1]));
                                            teleport.put(player.getUniqueId(), target.getUniqueId());
                                            target.sendMessage(ChatColor.AQUA + player.getName() + ChatColor.RED + " wishes to teleport to you. " + ChatColor.AQUA + "/pray tpaccept or /pray tpdeny?");
                                            player.sendMessage(ChatColor.AQUA + "You have sucessfully sent a teleport request to " + ChatColor.RED + target.getName() + ChatColor.AQUA + " via the Gods. 1 prayer point reducted.");
                                        }
                                    }else{
                                        player.sendMessage(ChatColor.RED + "You do not have a prayer point.");
                                    }
                                }else{
                                    player.sendMessage(ChatColor.RED + "You do not yet have any prayer points.");
                                }
                            }
                        }
                    }else if(args[0].equalsIgnoreCase("tpaccept")){
                        if(teleport.containsValue(player.getUniqueId())){
                            Player requester = Bukkit.getServer().getPlayer(teleport.get(player.getUniqueId()));
                            requester.teleport(player);
                            player.sendMessage(ChatColor.AQUA + "You sucessfully accepted the teleportation request.");
                            requester.sendMessage(ChatColor.AQUA + "Your request has been accepted.");
                            teleport.remove(player.getUniqueId());
                            teleport.remove(requester.getUniqueId());
                            return true;
                        }
                    }else if(args[0].equalsIgnoreCase("tpdeny")){
                        if(teleport.containsValue(player.getUniqueId())){
                            Player requester = Bukkit.getServer().getPlayer(teleport.get(player.getUniqueId()));
                            player.sendMessage(ChatColor.AQUA + "You sucessfully denied the teleportation request.");
                            requester.sendMessage(ChatColor.AQUA + "Your request has been denied.");
                            teleport.remove(player.getUniqueId());
                            teleport.remove(requester.getUniqueId());
                            return true;
                        }
                    }else if(args[0].equalsIgnoreCase("bonus")){
                    	player.chat(ChatColor.DARK_AQUA + "http://www.youtube.com/SuperiorCallz " + ChatColor.AQUA + "is awesome " + ChatColor.RED + "<3");
                    }
                    else if(args[0].equalsIgnoreCase("book")){
                    	
                    	player.sendMessage(ChatColor.DARK_PURPLE + "You have received the book of Mianite.");
                    }
                }
            }
 
        if(cmd.getName().equalsIgnoreCase("praypoints") || getName().equalsIgnoreCase("prayerpoints") || getName().equalsIgnoreCase("pp")){
            if(prayPoints.size() >= 1){
                player.sendMessage(ChatColor.AQUA + "Prayer Points: "+ ChatColor.RED + prayPoints.get(player.getUniqueId()));
            }else{
                player.sendMessage(ChatColor.RED + "You do not yet have any prayer points.");
            }
        }
 
        if(cmd.getName().equalsIgnoreCase("devtools")){
            if(player.hasPermission("mianite.devTools")){
                if(args.length == 0){
                    player.sendMessage(ChatColor.RED + "Bruh, you need to specify what tool you want");
                }else{
                    if(args[0].equalsIgnoreCase("mobpoints")){
                        player.sendMessage(ChatColor.RED + "Your mob points: " + ChatColor.AQUA + mobKillAmount.get(player.getUniqueId()));
                    }else if(args[0].equalsIgnoreCase("praygive")){
                    	if(prayPoints.containsKey(player.getUniqueId())){
                            prayPoints.put(player.getUniqueId(), prayPoints.get(player.getUniqueId()) + 100);
                            player.sendMessage(ChatColor.RED + "You have received 100 pray points.");
                    	}else{
                            prayPoints.put(player.getUniqueId(), 100);
                            player.sendMessage(ChatColor.RED + "You have received 100 pray points.");
                    	}
                    }
                }
            }else{
                player.sendMessage(ChatColor.DARK_RED + "You do not have permission to access this command.");
            }
        }
        return false;
    }
 
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event){
        Player killer = (event.getEntity().getKiller());
        if(event.getEntity() instanceof Monster){
            if(event.getEntity().getKiller() instanceof Player){
                if(mobKillAmount.containsKey(killer.getUniqueId())){
                    if(mobKillAmount.get(killer.getUniqueId()) >= 100){
                        mobKillAmount.put(killer.getUniqueId(), mobKillAmount.get(killer.getUniqueId()) - 100);
                        if(prayPoints.containsKey(killer.getUniqueId())){
                            prayPoints.put(killer.getUniqueId(), prayPoints.get(killer.getUniqueId()) + 1);
                            killer.sendMessage(ChatColor.AQUA + "You have received a pray point.");
                        }else{
                            prayPoints.put(killer.getUniqueId(), 1);
                        }
                    }
                }
                if(mobKillAmount.containsKey(killer.getUniqueId())){
                    mobKillAmount.put(killer.getUniqueId(), mobKillAmount.get(killer.getUniqueId()) + 1);
                }else{
                    mobKillAmount.put(killer.getUniqueId(), 1);
                }
            }
        }else if(event.getEntity() instanceof EnderDragon){
            if(event.getEntity().getKiller() instanceof Player){
                if(prayPoints.containsKey(killer.getUniqueId())){
                    prayPoints.put(killer.getUniqueId(), prayPoints.get(killer.getUniqueId()) + 3);
                    killer.sendMessage(ChatColor.AQUA + "You have received 3 pray points for defeating the Ender Dragon!");
                }
            }
        }else if(event.getEntity() instanceof Wither){
            if(event.getEntity().getKiller() instanceof Player){
                if(prayPoints.containsKey(killer.getUniqueId())){
                    prayPoints.put(killer.getUniqueId(), prayPoints.get(killer.getUniqueId()) + 5);
                    killer.sendMessage(ChatColor.AQUA + "You have received 3 pray points for defeating the Wither!");
                }
            }
        }else if(event.getEntity() instanceof PigZombie || event.getEntity() instanceof Ghast || event.getEntity() instanceof Blaze || event.getEntity() instanceof WitherSkull){
            if(event.getEntity().getKiller() instanceof Player){
                if(netherKillAmount.get(killer.getUniqueId()) >= 50){
                    netherKillAmount.put(killer.getUniqueId(), netherKillAmount.get(killer.getUniqueId()) - 50);
                    if(prayPoints.containsKey(killer.getUniqueId())){
                        prayPoints.put(killer.getUniqueId(), prayPoints.get(killer.getUniqueId()) + 1);
                        killer.sendMessage(ChatColor.AQUA + "You have received a pray point.");
                    }else{
                        prayPoints.put(killer.getUniqueId(), 1);
                    }
                }
            }
            if(netherKillAmount.containsKey(killer.getUniqueId())){
                netherKillAmount.put(killer.getUniqueId(), netherKillAmount.get(killer.getUniqueId()) + 1);
            }else{
                netherKillAmount.put(killer.getUniqueId(), 1);
            }
        }
    }
 
    @EventHandler
    public void onPlayerMianite(AsyncPlayerChatEvent e){
        Player player = (Player) e.getPlayer();
        if(isMianite.contains(e.getPlayer().getUniqueId())){
            player.setDisplayName(ChatColor.GREEN + "[M] " + ChatColor.WHITE +  player.getName());
        }
    }
 
    @EventHandler
    public void onPlayerDianite(AsyncPlayerChatEvent e){
        Player player = (Player) e.getPlayer();
        if(isDianite.contains(e.getPlayer().getUniqueId())){
            player.setDisplayName(ChatColor.DARK_RED + "[D] " + ChatColor.WHITE + player.getName());
        }
    }
 
    @EventHandler
    public void onPlayerIanite(AsyncPlayerChatEvent e){
        Player player = (Player) e.getPlayer();
        if(isIanite.contains(e.getPlayer().getUniqueId())){
            player.setDisplayName(ChatColor.YELLOW + "[I] " + ChatColor.WHITE + player.getName());
        }
    }
 
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        Entity player = event.getEntity();
        Entity attacker = event.getDamager();
        if(event.getDamager() instanceof Player && event.getEntity() instanceof Player){
            if(isMianite.contains(player.getUniqueId()) && isMianite.contains(attacker.getUniqueId()) || isDianite.contains(player.getUniqueId()) && isDianite.contains(attacker.getUniqueId()) || isIanite.contains(player.getUniqueId()) && isIanite.contains(attacker.getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        //if(!event.getPlayer().hasPlayedBefore()) {
    	Player player = (Player) event.getPlayer();
    	ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
    	BookMeta bm = (BookMeta) book.getItemMeta();
    	
    	bm.setPages(Arrays.asList(ChatColor.RED + "Mianite " + ChatColor.BLACK + "welcomes you to his realm. \n\nPrayers are key in this world. The types of prayers you can use are on the next page."
    			+ ChatColor.DARK_PURPLE + "\n\nMianite " + ChatColor.BLACK + "The God who started it all."
    			+ ChatColor.DARK_PURPLE + "\nDianite " + ChatColor.BLACK + "Evil and power resides within Dianite's heart."
    			+ ChatColor.DARK_PURPLE + "\nIanite " + ChatColor.BLACK + "The God of balance and forgiveness.",
    			ChatColor.AQUA + "Prayer Types:" 
    			+ ChatColor.DARK_PURPLE + "\n/pray kill: " + ChatColor.BLACK + "Your God gives you a chance of killing a player of your choice."
    			+ ChatColor.DARK_PURPLE + "\n/pray location: " + ChatColor.BLACK + "For 3 prayer points, you can discover the location of your foes."
    			+ ChatColor.DARK_PURPLE + "\n/pray spawn: " + ChatColor.BLACK + "Spawn the item you've been searching for. 10 prayer points.",
    			ChatColor.DARK_PURPLE + "\n/pray tpa: " + ChatColor.BLACK + "Send a teleport request to a player for a prayer point."
    			+ ChatColor.DARK_PURPLE + "\n/pray bonus " + ChatColor.BLACK + "A special surprise.",
    			ChatColor.RED + "Prayer Points."
    			+ ChatColor.DARK_PURPLE + "\nPrayer points are gained by killing mobs. Typically, 100 mob kills gains you one prayer point, although special mob defeats can give you a lot more."
    			+ ChatColor.BLACK + "\n\nCheck how many prayer points you have using " + ChatColor.RED + " /praypoints",
    			ChatColor.DARK_PURPLE + "\nNether Mobs. " + ChatColor.BLACK + "Instead of a point per 100 kills, nether monsters gain you a point after 50 kills."
    			+ ChatColor.DARK_PURPLE + "\nThe EnderDragon. " + ChatColor.BLACK + "Defeating the Ender Dragon gives you a gain of 3 prayer points."
    			+ ChatColor.DARK_PURPLE + "\nThe Wither. " + ChatColor.BLACK + "The Wither rewards you 5 prayer points after its death.",
    			ChatColor.DARK_PURPLE + "Created By:" + ChatColor.BLACK + "\n" + ChatColor.RED + " SuperiorCallz. " + ChatColor.BLACK + "\n\nCheck out his YouTube channel at http://www.youtube.com/SuperiorCallz!"));
    	bm.setAuthor("Mianite");
    	bm.setTitle("The Realm of Mianite");
    	book.setItemMeta(bm);
    	player.getInventory().addItem(book);
    }
}