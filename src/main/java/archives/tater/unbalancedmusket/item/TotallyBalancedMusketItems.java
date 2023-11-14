package archives.tater.unbalancedmusket.item;

import archives.tater.unbalancedmusket.TotallyBalancedMusket;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class TotallyBalancedMusketItems {
    public static final Item MUSKET = Registry.register(Registries.ITEM, new Identifier(TotallyBalancedMusket.MOD_ID, "musket"), new MusketItem(new FabricItemSettings().maxCount(1).maxDamage(200)));
    public static final Item RAMROD = Registry.register(Registries.ITEM, new Identifier(TotallyBalancedMusket.MOD_ID, "ramrod"), new Item(new FabricItemSettings().maxCount(1)));

    public static void register() {

    }

}
