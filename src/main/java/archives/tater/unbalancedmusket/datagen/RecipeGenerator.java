package archives.tater.unbalancedmusket.datagen;

import archives.tater.unbalancedmusket.item.TotallyBalancedMusketItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;

public class RecipeGenerator extends FabricRecipeProvider {
    public RecipeGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.COMBAT, TotallyBalancedMusketItems.BAYONET_MUSKET)
                .input(TotallyBalancedMusketItems.MUSKET)
                .input(Items.IRON_SWORD)
                .criterion(hasItem(TotallyBalancedMusketItems.MUSKET), conditionsFromItem(TotallyBalancedMusketItems.BAYONET_MUSKET))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, TotallyBalancedMusketItems.RAMROD)
                .input('N', Items.IRON_NUGGET)
                .pattern("N")
                .pattern("N")
                .pattern("N")
                .criterion(hasItem(TotallyBalancedMusketItems.MUSKET), conditionsFromItem(TotallyBalancedMusketItems.RAMROD))
                .offerTo(exporter);
    }
}