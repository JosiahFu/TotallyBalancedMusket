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
    public static final EntityType<IronMusketBallEntity> IRON_MUSKET_BALL = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(TotallyBalancedMusket.MOD_ID, "iron_musket_ball"),
            FabricEntityTypeBuilder.<IronMusketBallEntity>create(SpawnGroup.MISC, IronMusketBallEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25F, 0.25F))
                    .trackRangeChunks(4).trackedUpdateRate(10)
                    .build()
    );

    public static void register() {}
}
