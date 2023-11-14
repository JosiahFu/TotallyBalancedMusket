package archives.tater.unbalancedmusket.entity;

import archives.tater.unbalancedmusket.TotallyBalancedMusket;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class TotallyBalancedMusketEntities {
    public static final EntityType<MusketBallEntity> MusketBallEntityType = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(TotallyBalancedMusket.MOD_ID, "musket_ball"),
            FabricEntityTypeBuilder.<MusketBallEntity>create(SpawnGroup.MISC, MusketBallEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25F, 0.25F))
                    .trackRangeBlocks(4).trackedUpdateRate(10)
                    .build()
    );

    public static void register() {}
}
