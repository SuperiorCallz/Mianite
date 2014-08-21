package me.SuperiorCallz.Mianite;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class TeamPVP implements Listener{

	public Mianite plugin; 
	
	public TeamPVP(Mianite plugin) {
	    this.plugin = plugin;
	}
	
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        Entity player = event.getEntity();
        Entity attacker = event.getDamager();
        if(event.getDamager() instanceof Player && event.getEntity() instanceof Player){
            if(plugin.isMianite.contains(player.getUniqueId()) && plugin.isMianite.contains(attacker.getUniqueId()) || plugin.isDianite.contains(player.getUniqueId()) && plugin.isDianite.contains(attacker.getUniqueId()) || plugin.isIanite.contains(player.getUniqueId()) && plugin.isIanite.contains(attacker.getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }
    
    @SuppressWarnings("deprecation")
	@EventHandler
    public void onBowDamage(EntityDamageByEntityEvent event) {
        Entity player = event.getEntity();
        Entity attacker = event.getDamager();
        if(event.getDamager() instanceof Projectile && event.getEntity() instanceof Player){
            Projectile proj = (Projectile) event.getEntity();
            if(plugin.isMianite.contains(player.getUniqueId()) && plugin.isMianite.contains(attacker.getUniqueId()) || plugin.isDianite.contains(player.getUniqueId()) && plugin.isDianite.contains(attacker.getUniqueId()) || plugin.isIanite.contains(player.getUniqueId()) && plugin.isIanite.contains(attacker.getUniqueId())) {
            	if(proj.getShooter() instanceof Player){
                    event.setCancelled(true);
            	}
            }
        }
    }
	
}
