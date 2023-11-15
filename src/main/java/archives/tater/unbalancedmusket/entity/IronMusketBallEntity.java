package archives.tater.unbalancedmusket.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class IronMusketBallEntity
        extends ProjectileEntity {
    private final Random random = this.getWorld().getRandom();

    public IronMusketBallEntity(EntityType<? extends IronMusketBallEntity> entityType, World world) {
        super(entityType, world);
    }

    public IronMusketBallEntity(World world, LivingEntity owner) {
        this(world,
                owner.getX() - (double)(owner.getWidth() + 1.0f) * 0.5 * (double) MathHelper.sin(owner.bodyYaw * ((float)Math.PI / 180)),
                owner.getEyeY() - (double)0.1f,
                owner.getZ() + (double)(owner.getWidth() + 1.0f) * 0.5 * (double) MathHelper.cos(owner.bodyYaw * ((float)Math.PI / 180)));
        this.setOwner(owner);
    }

    public IronMusketBallEntity(World world, double x, double y, double z) {
        this(TotallyBalancedMusketEntities.IRON_MUSKET_BALL, world);
        this.setPosition(x, y, z);
    }

    @Override
    public void tick() {
        super.tick();
        Vec3d vec3d = this.getVelocity();
        HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
        this.onCollision(hitResult);
        double x = this.getX() + vec3d.x;
        double y = this.getY() + vec3d.y;
        double z = this.getZ() + vec3d.z;
        this.updateRotation();
        float velocityCoefficient = 0.99f;
        float gravity = -0.06f;
        this.setVelocity(vec3d.multiply(velocityCoefficient));
        if (!this.hasNoGravity()) {
            this.setVelocity(this.getVelocity().add(0.0, gravity, 0.0));
        }
        this.setPosition(x, y, z);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = this.getOwner();
        if (entity instanceof LivingEntity livingEntity) {
            entityHitResult.getEntity().damage(this.getDamageSources().mobProjectile(this, livingEntity), 40.0f);
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!this.getWorld().isClient) {
            this.discard();
        }
    }

    @Override
    protected void initDataTracker() {
    }

    @Override
    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        super.onSpawnPacket(packet);
        double vx = packet.getVelocityX();
        double vy = packet.getVelocityY();
        double vz = packet.getVelocityZ();
        for (int i = 0; i < 7; ++i) {
            this.getWorld().addParticle(ParticleTypes.CLOUD, this.getX() + rand(0.02F), this.getY() + rand(0.02F), this.getZ() + rand(0.02F), vx * 0.05 + rand(0.05F), (vy * 0.05) + rand(0.05F) + 0.05, vz * 0.05 + rand(0.05F));
        }
        this.setVelocity(vx, vy, vz);
    }

    private float rand(float size) {
        return 2 * size * random.nextFloat() - size;
    }
}


