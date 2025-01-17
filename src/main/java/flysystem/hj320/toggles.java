package flysystem.hj320;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Date;
import java.util.UUID;

public class toggles implements CommandExecutor {
    @Override
    public boolean onCommand (CommandSender sender, Command cmd, String label, String[]args){
        UUID uuid = Bukkit.getPlayer(sender.getName()).getUniqueId();

        if (cmd.getName().equalsIgnoreCase("fly") || cmd.getName().equalsIgnoreCase("reqtofly")) {
            if (!sender.hasPermission("hj320.fly") || !sender.hasPermission("hj320.fly.req")) {
                sender.sendMessage("You don't have permission to do this.");
                return true;
            } else {
                String fly = cache.get_user_data(uuid, "fly_enabled");
                if(fly != null){
                    if(fly.equals("1")){
                        cache.save_user_data(uuid, "fly_enabled", "0");
                        Bukkit.getPlayer(sender.getName()).setAllowFlight(false);
                        sender.sendMessage(ChatColor.RED +"Fly Disabled");
                        fly_system.isflyshitoncache.remove(uuid);
                    }else{
                        Bukkit.getPlayer(sender.getName()).setAllowFlight(true);
                        cache.save_user_data(uuid, "fly_enabled", "1");
                        sender.sendMessage(ChatColor.GREEN +"Fly Enabled");
                        fly_system.isflyshitoncache.put(uuid, "1");
                    }
                }
            }
        }
        if (cmd.getName().equalsIgnoreCase("flyparticles")||cmd.getName().equalsIgnoreCase("reqtoflyparticles")) {
            if (!sender.hasPermission("hj320.particles")) {
                sender.sendMessage("You don't have permission to do this.");
                return true;
            } else {
                String particles = cache.get_user_data(uuid, "particles_enabled");
                if(particles != null){
                    if(particles.equals("1")){
                        cache.save_user_data(uuid, "particles_enabled", "0");
                        sender.sendMessage(ChatColor.RED +"Particles Disabled");
                        fly_system.isparticlesshitoncache.remove(uuid);
                    }else{
                        cache.save_user_data(uuid, "particles_enabled", "1");
                        sender.sendMessage(ChatColor.GREEN +"Particles Enabled");
                        fly_system.isparticlesshitoncache.put(uuid, "1");
                    }
                }
            }
        }
        if (cmd.getName().equalsIgnoreCase("timeOnServer")||cmd.getName().equalsIgnoreCase("reqToFlyTimeOnServer")) {
            if (!sender.hasPermission("hj320.timeOnServer")) {
                sender.sendMessage("You don't have permission to do this.");
                return true;
            } else {
                Date firstJoinDate  = new Date(Long.parseLong(cache.get_user_data(uuid, "user_first_join_time")));
                Date currentDate    = new Date();

                long currentPlayTime = currentDate.getTime() - firstJoinDate.getTime();

                long diffSeconds    = currentPlayTime / 1000 % 60;
                long diffMinutes    = currentPlayTime / (60 * 1000) % 60;
                long diffHours      = currentPlayTime / (60 * 60 * 1000);

                sender.sendMessage("You have joined for " + diffHours + " hours, " + diffMinutes + " minutes, " + diffSeconds + " seconds. ");
            }
        }
        return false;
    }
}
