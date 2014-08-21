package me.SuperiorCallz.Mianite;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Developer implements CommandExecutor{
	
	public Mianite plugin; 
	
	public Developer(Mianite plugin) {
	    this.plugin = plugin;
	}
	
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        Player player = (Player) sender;
        if(cmd.getName().equalsIgnoreCase("devtools")){
            if(player.hasPermission("mianite.devTools")){
                if(args.length == 0){
                    player.sendMessage(ChatColor.RED + "Bruh, you need to specify what tool you want");
                }else{
                    if(args[0].equalsIgnoreCase("mobpoints")){
                        player.sendMessage(ChatColor.RED + "Your mob points: " + ChatColor.AQUA + plugin.mobKillAmount.get(player.getUniqueId()));
                    }else if(args[0].equalsIgnoreCase("praygive")){
                    	if(plugin.prayPoints.containsKey(player.getUniqueId())){
                            plugin.prayPoints.put(player.getUniqueId(), plugin.prayPoints.get(player.getUniqueId()) + 100);
                            player.sendMessage(ChatColor.RED + "You have received 100 pray points.");
                    	}else{
                            plugin.prayPoints.put(player.getUniqueId(), 100);
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

}
