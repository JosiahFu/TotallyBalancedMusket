package archives.tater.unbalancedmusket.mixin.client;

import archives.tater.unbalancedmusket.item.MusketItem;
import archives.tater.unbalancedmusket.item.TotallyBalancedMusketItems;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin {
	@Redirect(
			method = "getArmPose",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"
			)
	)
	private static boolean allowMusket(ItemStack itemStack, Item item) {
		return itemStack.isOf(item) || itemStack.isOf(TotallyBalancedMusketItems.MUSKET);
	}

	@Redirect(
			method = "getArmPose",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/item/CrossbowItem;isCharged(Lnet/minecraft/item/ItemStack;)Z"
			)
	)
	private static boolean checkMusketCharged(ItemStack stack) {
		return CrossbowItem.isCharged(stack) || MusketItem.getLoadingStage(stack) == MusketItem.Stage.LOADED;
	}
}
