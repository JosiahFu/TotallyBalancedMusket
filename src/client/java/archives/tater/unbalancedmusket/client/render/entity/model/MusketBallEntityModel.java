package archives.tater.unbalancedmusket.client.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.Entity;

@Environment(value= EnvType.CLIENT)
public class MusketBallEntityModel<T extends Entity>
        extends SinglePartEntityModel<T> {
    /**
     * The key of the main model part, whose value is {@value}.
     */
    private static final String MAIN = "main";
    private final ModelPart root;

    public MusketBallEntityModel(ModelPart root) {
        this.root = root;
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(MAIN, ModelPartBuilder.create().uv(0, 0).cuboid(-1.5f, -1.5f, -1.5f, 3.0f, 3.0f, 3.0f), ModelTransform.NONE);
        return TexturedModelData.of(modelData, 12, 6);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }
}


