package me.SuperiorCallz.Mianite;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Teams implements CommandExecutor{

	public Mianite plugin; 
	
	public Teams(Mianite plugin) {
	    this.plugin = plugin;
	}
	
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        Player player = (Player) sender;
        if(cmd.getName().equalsIgnoreCase("mianite")){
            if(!(plugin.isDianite.contains(player.getUniqueId()) || plugin.isMianite.contains(player.getUniqueId()) || plugin.isIanite.contains(player.getUniqueId()))){
                //Remove player from cantMove list.
                plugin.cantMove.remove(player.getPlayer().getUniqueId());
                //Add player to plugin.isMianite list.
                plugin.isMianite.add(player.getUniqueId());
                //plugin.getConfig().addDefault("Mianite Followers", this.plugin.isMianite);
                player.sendMessage(ChatColor.AQUA + "Congratulations. You are now following " + ChatColor.GREEN + "Mianite." + ChatColor.DARK_PURPLE + "Read the book of Mianite to get started.");
 
                //Teleport player to coordinates set in config as "mianite:x:y:z
                String[] rawValues = plugin.getConfig().getString("mianite").split(" ");
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
            if(!(plugin.isDianite.contains(player.getUniqueId()) || plugin.isMianite.contains(player.getUniqueId()) || plugin.isIanite.contains(player.getUniqueId()))){
                plugin.cantMove.remove(player.getPlayer().getUniqueId());
                plugin.isDianite.add(player.getUniqueId());;
                //String uid = player.getUniqueId().toString();
                //plugin.getConfig().addDefault("Dianite Followers", uid);
                //plugin.saveConfig();
                player.sendMessage(ChatColor.AQUA + "Congratulations. You are now following " + ChatColor.DARK_RED + "Dianite." + ChatColor.DARK_PURPLE + "Read the book of Mianite to get started.");
 
                String[] rawValues = plugin.getConfig().getString("dianite").split(" ");
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
            if(!(plugin.isDianite.contains(player.getUniqueId()) || plugin.isMianite.contains(player.getUniqueId()) || plugin.isIanite.contains(player.getUniqueId()))){
                plugin.cantMove.remove(player.getPlayer().getUniqueId());
                plugin.isIanite.add(player.getUniqueId());
                plugin.getConfig().addDefault("Ianite Followers", this.plugin.isIanite);
                plugin.saveConfig();
                player.sendMessage(ChatColor.AQUA + "Congratulations. You are now following " + ChatColor.YELLOW + "Ianite." + ChatColor.DARK_PURPLE + "Read the book of Mianite to get started.");
 
                String[] rawValues = plugin.getConfig().getString("ianite").split(" ");
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
		return false;
    }
}
