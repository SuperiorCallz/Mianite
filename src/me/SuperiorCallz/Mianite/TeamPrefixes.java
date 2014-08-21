package me.SuperiorCallz.Mianite;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class TeamPrefixes implements Listener{
	
	public Mianite plugin; 
	
	public TeamPrefixes(Mianite plugin) {
	    this.plugin = plugin;
	}

    @EventHandler
    public void onPlayerMianite(AsyncPlayerChatEvent e){
        Player player = (Player) e.getPlayer();
        if(plugin.isMianite.contains(e.getPlayer().getUniqueId())){
            player.setDisplayName(ChatColor.GREEN + "[M] " + ChatColor.WHITE +  player.getName());
        }
    }
 
    @EventHandler
    public void onPlayerDianite(AsyncPlayerChatEvent e){
        Player player = (Player) e.getPlayer();
        if(plugin.isDianite.contains(e.getPlayer().getUniqueId())){
            player.setDisplayName(ChatColor.DARK_RED + "[D] " + ChatColor.WHITE + player.getName());
        }
    }
 
    @EventHandler
    public void onPlayerIanite(AsyncPlayerChatEvent e){
        Player player = (Player) e.getPlayer();
        if(plugin.isIanite.contains(e.getPlayer().getUniqueId())){
            player.setDisplayName(ChatColor.YELLOW + "[I] " + ChatColor.WHITE + player.getName());
        }
    }
	
}
