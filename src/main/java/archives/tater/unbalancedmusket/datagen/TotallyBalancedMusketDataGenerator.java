package archives.tater.unbalancedmusket.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class TotallyBalancedMusketDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		final FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ModelGenerator::new);
		pack.addProvider(EnglishLangGenerator::new);
		pack.addProvider(RussianLangGenerator::new);
		pack.addProvider(RecipeGenerator::new);
	}
}
