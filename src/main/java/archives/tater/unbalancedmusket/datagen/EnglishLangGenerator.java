package archives.tater.unbalancedmusket.datagen;

import archives.tater.unbalancedmusket.item.TotallyBalancedMusketItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class EnglishLangGenerator extends FabricLanguageProvider {

    protected EnglishLangGenerator(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(TotallyBalancedMusketItems.MUSKET, "Musket");
        translationBuilder.add(TotallyBalancedMusketItems.RAMROD, "Ramrod");
    }
}
