package archives.tater.unbalancedmusket.datagen;

import archives.tater.unbalancedmusket.entity.TotallyBalancedMusketEntities;
import archives.tater.unbalancedmusket.item.TotallyBalancedMusketItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class RussianLangGenerator extends FabricLanguageProvider {

    protected RussianLangGenerator(FabricDataOutput dataOutput) {
        super(dataOutput, "ru_ru");
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(TotallyBalancedMusketItems.MUSKET, "Мушкет");
        translationBuilder.add(TotallyBalancedMusketItems.BAYONET_MUSKET, "Штык-мушкет");
        translationBuilder.add(TotallyBalancedMusketItems.RAMROD, "Шомпол");
        translationBuilder.add(TotallyBalancedMusketItems.IRON_MUSKET_BALL, "Железная мушкетная пуля");
        translationBuilder.add("item.unbalancedmusket.musket.stage.powdered", "Порох помещён");
        translationBuilder.add("item.unbalancedmusket.musket.stage.rammed", "Порох утрамбован");
        translationBuilder.add("item.unbalancedmusket.musket.stage.loaded", "Готов к стрельбе");
        translationBuilder.add("item.unbalancedmusket.musket.projectile", "Снаряд:");
        translationBuilder.add(TotallyBalancedMusketEntities.IRON_MUSKET_BALL, "Железная мушкетная пуля");

        translationBuilder.add("death.attack.musket", "%s was blown away by %s");
        translationBuilder.add("death.attack.musket.item", "%s was blown away by %s using %s");
    }
}
