package me.SuperiorCallz.Mianite;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Prayers implements CommandExecutor{
	
	public Mianite plugin; 
	
	public Prayers(Mianite plugin) {
	    this.plugin = plugin;
	}
	
    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        Player player = (Player) sender;
        if(cmd.getName().equalsIgnoreCase("pray") || cmd.getName().equalsIgnoreCase("prayer")){
            if(args.length == 0){
                player.sendMessage(ChatColor.DARK_PURPLE + "You must specify a prayer type. These are found in the book of Mianite.");
            }else{
                if(args[0].equalsIgnoreCase("kill")){
                    if(plugin.isMianite.contains(player.getUniqueId()) || plugin.isDianite.contains(player.getUniqueId()) || plugin.isIanite.contains(player.getUniqueId())){
                        if(args.length == 1){
                            player.sendMessage(ChatColor.RED + "You must specify a player.");
                        }else{
                            int coolDownTime = 259200;
                            if(plugin.coolDown.containsKey(player.getUniqueId())){
                                long secondsLeft = ((plugin.coolDown.get(player.getUniqueId())/1000)+coolDownTime) - (System.currentTimeMillis()/1000);
                                
                                if(secondsLeft > 0){
                                    sender.sendMessage(ChatColor.RED + "You may not pray for kills to the Gods for another " + ChatColor.AQUA + secondsLeft + " seconds.");
                                    return true;
                                }
                            }
                            plugin.coolDown.put(player.getUniqueId(), System.currentTimeMillis());
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
                    if(plugin.isMianite.contains(player.getUniqueId()) || plugin.isDianite.contains(player.getUniqueId()) || plugin.isIanite.contains(player.getUniqueId())){
                        if(args.length == 1){
                            player.sendMessage(ChatColor.RED + "You must specify a player.");
                        }else{
                            if(plugin.prayPoints.containsKey(player.getUniqueId())){
                                if(plugin.prayPoints.get(player.getUniqueId()) >= 3){
                                    plugin.prayPoints.put(player.getUniqueId(), plugin.prayPoints.get(player.getUniqueId()) - 3);
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
                        }else{
                        	player.sendMessage(ChatColor.RED + "You must have a God to pray.");
                        }
                    }else if(args[0].equalsIgnoreCase("item") || args[0].equalsIgnoreCase("spawn") || args[0].equalsIgnoreCase("give")){
                        if(plugin.isMianite.contains(player.getUniqueId()) || plugin.isDianite.contains(player.getUniqueId()) || plugin.isIanite.contains(player.getUniqueId())){
                            if(args.length == 1){
                                player.sendMessage(ChatColor.RED + "You must specify an item.");
                            }else{
                                if(plugin.prayPoints.containsKey(player.getUniqueId())){
                                    if(plugin.prayPoints.get(player.getUniqueId()) >= 10){
                                        Material material = Material.matchMaterial(args[1]);
                                        if(material != null){
                                            plugin.prayPoints.put(player.getUniqueId(), plugin.prayPoints.get(player.getUniqueId()) - 10);
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
                        if(plugin.isMianite.contains(player.getUniqueId()) || plugin.isDianite.contains(player.getUniqueId()) || plugin.isIanite.contains(player.getUniqueId())){
                            if(args.length == 1){
                                player.sendMessage(ChatColor.RED + "You must specify a player you would like to request a teleport to.");
                            }else{
                                if(plugin.prayPoints.containsKey(player.getUniqueId())){
                                    if(plugin.prayPoints.get(player.getUniqueId()) >= 1){
                                        if(Bukkit.getServer().getPlayer(args[1]).isOnline() && Bukkit.getServer().getPlayer(args[1]) != null){
                                            plugin.prayPoints.put(player.getUniqueId(), plugin.prayPoints.get(player.getUniqueId()) - 1);
                                            Player target = (Bukkit.getServer().getPlayer(args[1]));
                                            plugin.teleport.put(target.getUniqueId(), player.getUniqueId());
                                            target.sendMessage(ChatColor.AQUA + player.getName() + ChatColor.RED + " wishes to teleport to you. " + ChatColor.AQUA + "/pray tpaccept or /pray tpdeny?");
                                            player.sendMessage(ChatColor.AQUA + "You have sucessfully sent a teleport request to " + ChatColor.RED + target.getName() + ChatColor.AQUA + " via the Gods. 1 prayer point reducted.");
                                        }else{
                                        	player.sendMessage("The player you specified is not online.");
                                        }
                                    }else{
                                        player.sendMessage(ChatColor.RED + "You do not have a prayer point.");
                                    }
                                }else{
                                    player.sendMessage(ChatColor.RED + "You do not yet have any prayer points.");
                                }
                            }
                        }else{
                        	player.sendMessage(ChatColor.RED + "You must have a God to pray.");
                        }
                    }else if(args[0].equalsIgnoreCase("tpaccept")){
                        if(plugin.teleport.containsKey(player.getUniqueId())){
                            Player requester = Bukkit.getServer().getPlayer(plugin.teleport.get(player.getUniqueId()));
                            requester.teleport(player.getLocation());
                            player.sendMessage(ChatColor.AQUA + "You sucessfully accepted the plugin.teleportation request.");
                            requester.sendMessage(ChatColor.AQUA + "Your request has been accepted.");
                            plugin.teleport.remove(player.getUniqueId());
                            plugin.teleport.remove(requester.getUniqueId());
                            return true;
                        }else{
                        	player.sendMessage(ChatColor.RED + "You do not have any plugin.teleport requests.");
                        }
                    }else if(args[0].equalsIgnoreCase("tpdeny")){
                        if(plugin.teleport.containsValue(player.getUniqueId())){
                            Player requester = Bukkit.getServer().getPlayer(plugin.teleport.get(player.getUniqueId()));
                            player.sendMessage(ChatColor.AQUA + "You sucessfully denied the plugin.teleportation request.");
                            requester.sendMessage(ChatColor.AQUA + "Your request has been denied.");
                            plugin.teleport.remove(player.getUniqueId());
                            plugin.teleport.remove(requester.getUniqueId());
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
		return false;
    }
}
