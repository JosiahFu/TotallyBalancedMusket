package archives.tater.unbalancedmusket.datagen;

import archives.tater.unbalancedmusket.item.TotallyBalancedMusketItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.ItemTags;

import java.util.function.Consumer;

public class RecipeGenerator extends FabricRecipeProvider {
    public RecipeGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.COMBAT, TotallyBalancedMusketItems.BAYONET_MUSKET)
                .input(TotallyBalancedMusketItems.MUSKET)
                .input(Items.IRON_SWORD)
                .criterion(hasItem(TotallyBalancedMusketItems.MUSKET), conditionsFromItem(TotallyBalancedMusketItems.MUSKET))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, TotallyBalancedMusketItems.RAMROD)
                .input('N', Items.IRON_NUGGET)
                .pattern("N")
                .pattern("N")
                .pattern("N")
                .criterion(hasItem(TotallyBalancedMusketItems.MUSKET), conditionsFromItem(TotallyBalancedMusketItems.MUSKET))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, TotallyBalancedMusketItems.IRON_MUSKET_BALL)
                .input('N', Items.IRON_NUGGET)
                .pattern(" N ")
                .pattern("NNN")
                .pattern(" N ")
                .criterion(hasItem(TotallyBalancedMusketItems.MUSKET), conditionsFromItem(TotallyBalancedMusketItems.MUSKET))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, TotallyBalancedMusketItems.MUSKET)
            .input('I', Items.IRON_INGOT)
            .input('P', ItemTags.PLANKS)
            .input('R', Items.REDSTONE)
            .input('F', Items.FLINT_AND_STEEL)
            .input('T', Items.TRIPWIRE_HOOK)
            .pattern("T I")
            .pattern("FIP")
            .pattern("PR ")
            .criterion(hasItem(Items.REDSTONE), conditionsFromItem(Items.REDSTONE))
            .offerTo(exporter);
    }
}
