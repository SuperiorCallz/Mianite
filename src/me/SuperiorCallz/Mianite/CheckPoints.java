package me.SuperiorCallz.Mianite;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CheckPoints implements CommandExecutor{

	public Mianite plugin; 
	
	public CheckPoints(Mianite plugin) {
	    this.plugin = plugin;
	}
	
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
    	Player player = (Player) sender;
    	if(plugin.prayPoints.containsValue(player.getUniqueId())){
            if(plugin.prayPoints.size() >= 1){
                player.sendMessage(ChatColor.AQUA + "Prayer Points: "+ ChatColor.RED + plugin.prayPoints.get(player.getUniqueId()));
            }else{
                player.sendMessage(ChatColor.RED + "You do not yet have any prayer points.");
            }
        }
		return false;
    }
	
}