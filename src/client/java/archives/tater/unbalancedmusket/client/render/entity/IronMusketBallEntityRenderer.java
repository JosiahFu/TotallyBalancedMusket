package archives.tater.unbalancedmusket.client.render.entity;

import archives.tater.unbalancedmusket.TotallyBalancedMusket;
import archives.tater.unbalancedmusket.TotallyBalancedMusketClient;
import archives.tater.unbalancedmusket.client.render.entity.model.MusketBallEntityModel;
import archives.tater.unbalancedmusket.entity.IronMusketBallEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(value= EnvType.CLIENT)
public class IronMusketBallEntityRenderer
        extends EntityRenderer<IronMusketBallEntity> {
    private static final Identifier TEXTURE = new Identifier(TotallyBalancedMusket.MOD_ID, "textures/entity/projectile/iron_musket_ball.png");
    private final MusketBallEntityModel<IronMusketBallEntity> model;

    public IronMusketBallEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new MusketBallEntityModel<>(context.getPart(TotallyBalancedMusketClient.MODEL_MUSKET_BALL_LAYER));
    }

    @Override
    public void render(IronMusketBallEntity musketBallEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
//        matrixStack.translate(0.0f, 0.15f, 0.0f);
//        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.lerp(g, musketBallEntity.prevYaw, musketBallEntity.getYaw()) - 90.0f));
//        matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(MathHelper.lerp(g, musketBallEntity.prevPitch, musketBallEntity.getPitch())));
//        this.model.setAngles(musketBallEntity, g, 0.0f, -0.1f, 0.0f, 0.0f);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.getLayer(TEXTURE));
        this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        matrixStack.pop();
        super.render(musketBallEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(IronMusketBallEntity musketBallEntity) {
        return TEXTURE;
    }
}


