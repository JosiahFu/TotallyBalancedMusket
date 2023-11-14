package archives.tater.unbalancedmusket;

import archives.tater.unbalancedmusket.client.render.entity.MusketBallEntityRenderer;
import archives.tater.unbalancedmusket.client.render.entity.model.MusketBallEntityModel;
import archives.tater.unbalancedmusket.item.MusketItem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class TotallyBalancedMusketClient implements ClientModInitializer {
	public static final EntityModelLayer MODEL_MUSKET_BALL_LAYER = new EntityModelLayer(new Identifier(TotallyBalancedMusket.MOD_ID, "musket_ball"), "main");

	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		EntityRendererRegistry.register(TotallyBalancedMusket.MusketBallEntityType, MusketBallEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(MODEL_MUSKET_BALL_LAYER, MusketBallEntityModel::getTexturedModelData);

		ModelPredicateProviderRegistry.register(TotallyBalancedMusket.MUSKET, new Identifier("charged"), (stack, world, entity, seed) -> MusketItem.getLoadingStage(stack) == MusketItem.Stage.LOADED ? 1.0f : 0.0f);
	}
}
