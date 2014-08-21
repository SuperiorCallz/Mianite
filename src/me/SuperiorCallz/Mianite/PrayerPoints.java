package me.SuperiorCallz.Mianite;

import org.bukkit.ChatColor;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Monster;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class PrayerPoints implements Listener{

	public Mianite plugin; 
	
	public PrayerPoints(Mianite plugin) {
	    this.plugin = plugin;
	}
	
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event){
        Player killer = (event.getEntity().getKiller());
        if(event.getEntity() instanceof Monster){
            if(event.getEntity().getKiller() instanceof Player){
                if(plugin.mobKillAmount.containsKey(killer.getUniqueId())){
                    if(plugin.mobKillAmount.get(killer.getUniqueId()) >= 100){
                        plugin.mobKillAmount.put(killer.getUniqueId(), plugin.mobKillAmount.get(killer.getUniqueId()) - 100);
                        if(plugin.prayPoints.containsKey(killer.getUniqueId())){
                            plugin.prayPoints.put(killer.getUniqueId(), plugin.prayPoints.get(killer.getUniqueId()) + 1);
                            killer.sendMessage(ChatColor.AQUA + "You have received a pray point.");
                        }else{
                            plugin.prayPoints.put(killer.getUniqueId(), 1);
                        }
                    }
                }
                if(plugin.mobKillAmount.containsKey(killer.getUniqueId())){
                    plugin.mobKillAmount.put(killer.getUniqueId(), plugin.mobKillAmount.get(killer.getUniqueId()) + 1);
                }else{
                    plugin.mobKillAmount.put(killer.getUniqueId(), 1);
                }
            }
        }else if(event.getEntity() instanceof EnderDragon){
            if(event.getEntity().getKiller() instanceof Player){
                if(plugin.prayPoints.containsKey(killer.getUniqueId())){
                    plugin.prayPoints.put(killer.getUniqueId(), plugin.prayPoints.get(killer.getUniqueId()) + 3);
                    killer.sendMessage(ChatColor.AQUA + "You have received 3 pray points for defeating the Ender Dragon!");
                }
            }
        }else if(event.getEntity() instanceof Wither){
            if(event.getEntity().getKiller() instanceof Player){
                if(plugin.prayPoints.containsKey(killer.getUniqueId())){
                    plugin.prayPoints.put(killer.getUniqueId(), plugin.prayPoints.get(killer.getUniqueId()) + 5);
                    killer.sendMessage(ChatColor.AQUA + "You have received 3 pray points for defeating the Wither!");
                }
            }
        }else if(event.getEntity() instanceof PigZombie || event.getEntity() instanceof Ghast || event.getEntity() instanceof Blaze || event.getEntity() instanceof WitherSkull){
            if(event.getEntity().getKiller() instanceof Player){
                if(plugin.netherKillAmount.get(killer.getUniqueId()) >= 50){
                    plugin.netherKillAmount.put(killer.getUniqueId(), plugin.netherKillAmount.get(killer.getUniqueId()) - 50);
                    if(plugin.prayPoints.containsKey(killer.getUniqueId())){
                        plugin.prayPoints.put(killer.getUniqueId(), plugin.prayPoints.get(killer.getUniqueId()) + 1);
                        killer.sendMessage(ChatColor.AQUA + "You have received a pray point.");
                    }else{
                        plugin.prayPoints.put(killer.getUniqueId(), 1);
                    }
                }
            }
            if(plugin.netherKillAmount.containsKey(killer.getUniqueId())){
                plugin.netherKillAmount.put(killer.getUniqueId(), plugin.netherKillAmount.get(killer.getUniqueId()) + 1);
            }else{
                plugin.netherKillAmount.put(killer.getUniqueId(), 1);
            }
        }
    }
	
}
