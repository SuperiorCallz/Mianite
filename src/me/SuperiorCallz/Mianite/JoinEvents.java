package me.SuperiorCallz.Mianite;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class JoinEvents implements Listener{

	public Mianite plugin;

	public JoinEvents(Mianite plugin) {
	    this.plugin = plugin;
	}
	
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        if(!event.getPlayer().hasPlayedBefore()) {
        	Player player = (Player) event.getPlayer();
        	ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        	BookMeta bm = (BookMeta) book.getItemMeta();
        	
        	bm.setPages(Arrays.asList(ChatColor.RED + "Mianite " + ChatColor.BLACK + "welcomes you to his realm. \n\nPrayers are key in this world. The types of prayers you can use are on the next page."
        			+ ChatColor.DARK_PURPLE + "\n\nMianite " + ChatColor.BLACK + "The God who started it all."
        			+ ChatColor.DARK_PURPLE + "\nDianite " + ChatColor.BLACK + "Evil and power sits within."
        			+ ChatColor.DARK_PURPLE + "\nIanite " + ChatColor.BLACK + "The God of balance."
        			+ ChatColor.DARK_PURPLE + "\nThe Shadows" + ChatColor.BLACK + "..",
        			ChatColor.AQUA + "Prayer Types:" 
        			+ ChatColor.DARK_PURPLE + "\n/pray kill: " + ChatColor.BLACK + "Your God gives you a chance of killing a player of your choice."
        			+ ChatColor.DARK_PURPLE + "\n/pray location: " + ChatColor.BLACK + "For 3 prayer points, you can discover the location of your foes."
        			+ ChatColor.DARK_PURPLE + "\n/pray spawn: " + ChatColor.BLACK + "Spawn the item you've been searching for. 10 prayer points.",
        			ChatColor.DARK_PURPLE + "\n/pray tpa: " + ChatColor.BLACK + "Send a teleport request to a player for a prayer point."
        			+ ChatColor.DARK_PURPLE + "\n/pray bonus " + ChatColor.BLACK + "A surprise!",
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
    
    @EventHandler
    public void move(PlayerMoveEvent move)
    {
        //Check if player is in the cantMove list, and if so, don't allow them to move.
        if(!move.getPlayer().hasPermission("mianite.canMove")){
            if(plugin.cantMove.contains(move.getPlayer().getUniqueId())){
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
        event.getPlayer().sendMessage(ChatColor.DARK_PURPLE + "Welcome to the world of Mianite. " + ChatColor.AQUA + "\nPick your team. " + ChatColor.GREEN + "/mianite," + ChatColor.DARK_RED + " /dianite, " + ChatColor.YELLOW + "/ianite");
        plugin.cantMove.add(event.getPlayer().getUniqueId());
      }
    }
	
}
