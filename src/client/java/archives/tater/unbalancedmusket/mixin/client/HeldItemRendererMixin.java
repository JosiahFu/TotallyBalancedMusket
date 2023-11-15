package archives.tater.unbalancedmusket.mixin.client;

import archives.tater.unbalancedmusket.item.MusketItem;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
    @Redirect(
            method = "renderFirstPersonItem",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 1)
    )
    private boolean allowMusket(ItemStack stack, Item item) {
        return stack.isOf(item) || stack.getItem() instanceof MusketItem;
    }

    @Redirect(
            method = "renderFirstPersonItem",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/CrossbowItem;isCharged(Lnet/minecraft/item/ItemStack;)Z")
    )
    private boolean checkMusketCharged(ItemStack stack) {
        return CrossbowItem.isCharged(stack) || MusketItem.getLoadingStage(stack) == MusketItem.Stage.LOADED;
    }
}
