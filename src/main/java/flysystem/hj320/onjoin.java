package flysystem.hj320;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

import static flysystem.hj320.cache.get_user_data_exists;

public class onjoin implements Listener {
    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        Player player       = event.getPlayer();
        OfflinePlayer op    = Bukkit.getServer().getOfflinePlayer(player.getUniqueId());

        if(!get_user_data_exists(player.getUniqueId())){
            cache.save_user_data(player.getUniqueId(), "user_first_join_time", String.valueOf(op.getFirstPlayed()));
            cache.save_user_data(player.getUniqueId(), "fly_enabled","1");
            cache.save_user_data(player.getUniqueId(), "particles_enabled","1");
        }
        fly_system.isparticlesshitoncache.put(player.getUniqueId(), cache.get_user_data(player.getUniqueId(), cache.get_user_data(player.getUniqueId(), "particles_enabled")));
        fly_system.isflyshitoncache.put(player.getUniqueId(), cache.get_user_data(player.getUniqueId(),  cache.get_user_data(player.getUniqueId(), "fly_enabled")));
        fly_system.storeplayerhours.put(player.getUniqueId(), cache.get_user_data(player.getUniqueId(), "user_first_join_time"));
    }
    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        fly_system.storeplayerhours.remove(player.getUniqueId());
        fly_system.isflyshitoncache.remove(player.getUniqueId());
        fly_system.isparticlesshitoncache.remove(player.getUniqueId());
    }
}
