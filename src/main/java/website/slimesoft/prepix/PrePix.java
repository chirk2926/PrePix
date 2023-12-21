package website.slimesoft.prepix;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class PrePix extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("§aPrePixが正常に起動しました。");
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        // Check if the player is holding a silk touch 2 tool
        if (itemInHand.getEnchantmentLevel(Enchantment.SILK_TOUCH) >= 2) {
            Block block = event.getBlock();
            Material blockType = block.getType();

            // Check if the block is not air
            if (blockType != Material.AIR) {
                // Decrease durability by 3
                short durability = itemInHand.getDurability();
                itemInHand.setDurability((short) (durability + 3));

                // Remove the block and the blocks around it in a 3x3x3 area
                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        for (int z = -1; z <= 1; z++) {
                            Block relativeBlock = block.getRelative(x, y, z);
                            if (relativeBlock.getType() != Material.AIR) {
                                relativeBlock.breakNaturally(itemInHand);
                            }
                        }
                    }
                }
            }
        }
    }
}

