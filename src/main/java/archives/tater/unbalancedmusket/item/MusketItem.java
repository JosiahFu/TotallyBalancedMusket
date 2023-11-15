package archives.tater.unbalancedmusket.item;

import archives.tater.unbalancedmusket.entity.IronMusketBallEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.List;
import java.util.function.Predicate;

public class MusketItem extends RangedWeaponItem implements Vanishable {

    public static final String LOADING_STAGE_KEY = "LoadingStage";
    public static final String PROJECTILE_KEY = "ProjectileItem";
    public static final float SOUND_PROGRESS_STEP = 0.2F;

    public static class Stage {
        public static final int UNLOADED = 0;
        public static final int POWDERED = 1;
        public static final int RAMMED   = 2;
        public static final int LOADED   = 3;
    }

    public MusketItem(Settings settings) {
        super(settings);
    }

    // <0 if no next sound
    private float nextSoundProgressTarget;


    @Override
    public Predicate<ItemStack> getProjectiles() {
        return itemStack -> itemStack.isOf(TotallyBalancedMusketItems.IRON_MUSKET_BALL);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, @NotNull PlayerEntity user, Hand hand) {
        ItemStack musket = user.getStackInHand(hand);

        int stage = getLoadingStage(musket);

        if (stage == Stage.LOADED) {
            shoot(world, user, hand, musket, getProjectile(musket));
            setLoadingStage(musket, 0);
            user.getItemCooldownManager().set(this, 40);
            return TypedActionResult.consume(musket);
        }

        ItemStack otherItem = user.getStackInHand(hand == Hand.MAIN_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND);

        if (!isNextItem(stage, otherItem)) return TypedActionResult.fail(musket);

        user.setCurrentHand(hand);
        nextSoundProgressTarget = 0;

        return TypedActionResult.consume(musket);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        int usedTicks = this.getMaxUseTime(stack) - remainingUseTicks;
        float progress = getPullProgress(usedTicks, stack);
        int stage = getLoadingStage(stack);

        if (stage != Stage.RAMMED && progress < 1.0F) return;

        boolean result = loadProjectile(user, stack, user.getOffHandStack(), !(user instanceof PlayerEntity player) || player.isCreative());
        if (!result || stage != Stage.RAMMED) return;

        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_CROSSBOW_LOADING_END, SoundCategory.PLAYERS, 1.0F, 1.0F / (world.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F);
    }

    private boolean loadProjectile(LivingEntity shooter, ItemStack musket, ItemStack projectile, boolean creative) {
        if (projectile.isEmpty()) return false;
        int loadingStage = getLoadingStage(musket);
        if (!isNextItem(loadingStage, projectile)) return false;

        ItemStack itemStack;
        if (creative || loadingStage == Stage.POWDERED) {
            itemStack = projectile;
        } else {
            itemStack = projectile.split(1);
            if (projectile.isEmpty() && shooter instanceof PlayerEntity) {
                ((PlayerEntity)shooter).getInventory().removeOne(projectile);
            }
        }

        switch(loadingStage) {
            case Stage.UNLOADED -> setLoadingStage(musket, Stage.POWDERED);
            case Stage.POWDERED -> setLoadingStage(musket, Stage.RAMMED);
            case Stage.RAMMED -> {
                setLoadingStage(musket, Stage.LOADED);
                setProjectile(musket, itemStack);
            }
        }

        return true;
    }

    public boolean isNextItem(int stage, ItemStack itemStack) {
        Item item = itemStack.getItem();

        return (
                stage == Stage.UNLOADED && item == Items.GUNPOWDER ||
                stage == Stage.POWDERED && item == TotallyBalancedMusketItems.RAMROD ||
                stage == Stage.RAMMED && getProjectiles().test(itemStack)
        );
    }

    public static ItemStack getProjectile(ItemStack itemStack) {
        NbtCompound nbtCompound = itemStack.getNbt();
        if (nbtCompound == null) return ItemStack.EMPTY;
        if (!nbtCompound.contains(PROJECTILE_KEY)) return ItemStack.EMPTY;
        return ItemStack.fromNbt(nbtCompound.getCompound(PROJECTILE_KEY));
    }

    private static void setProjectile(ItemStack musket, ItemStack projectile) {
        NbtCompound musketNbt = musket.getOrCreateNbt();
        NbtCompound itemNbt = new NbtCompound();
        projectile.writeNbt(itemNbt);
        musketNbt.put(PROJECTILE_KEY, itemNbt);
    }

    private static void clearProjectile(ItemStack musket) {
        NbtCompound nbtCompound = musket.getNbt();
        if (nbtCompound == null) return;
        nbtCompound.remove(PROJECTILE_KEY);
    }

    public static int getLoadingStage(ItemStack stack) {
        NbtCompound nbtCompound = stack.getNbt();
        return nbtCompound == null ? 0 : nbtCompound.getInt(LOADING_STAGE_KEY);
    }

    public static void setLoadingStage(ItemStack stack, int stage) {
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        nbtCompound.putInt(LOADING_STAGE_KEY, stage);
    }

    private static void shoot(World world, LivingEntity shooter, Hand hand, ItemStack musket, ItemStack projectile) {
        if (world.isClient) return;

        createProjectile(world, shooter, projectile);
        musket.damage(1, shooter, (e) -> e.sendToolBreakStatus(hand));
        world.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 0.5F, 1.8F);

        if (shooter instanceof ServerPlayerEntity serverPlayerEntity) {
            serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(musket.getItem()));
        }

        clearProjectile(musket);
    }

    private static void createProjectile(World world, LivingEntity shooter, ItemStack projectile) {
        // TODO check for gold musket ball
        final ProjectileEntity projectileEntity = new IronMusketBallEntity(world, shooter);
        Vec3d shooterVector = shooter.getRotationVec(1.0F);
        Vector3f velocity = shooterVector.toVector3f();
        Vec3d position = shooter.getEyePos().add(shooterVector.multiply(0.5F));
        projectileEntity.setVelocity(velocity.x(), velocity.y(), velocity.z(), (float) 10.0, (float) 0.2);
        projectileEntity.setPosition(position);
        world.spawnEntity(projectileEntity);
    }

    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (world.isClient) return;
        int loadingStage = getLoadingStage(stack);
        if (loadingStage == Stage.RAMMED || loadingStage == Stage.LOADED) return;

        float progress = (float)(stack.getMaxUseTime() - remainingUseTicks) / (float)getPullTime(stack);
        if (nextSoundProgressTarget < 0 || progress <= nextSoundProgressTarget) return;
        SoundEvent soundEvent = progress > 1F ?
                SoundEvents.ITEM_CROSSBOW_LOADING_END : (
                        loadingStage == Stage.UNLOADED ? SoundEvents.BLOCK_SAND_PLACE :
                        SoundEvents.BLOCK_SAND_STEP
                );
        world.playSound(null, user.getX(), user.getY(), user.getZ(), soundEvent, SoundCategory.PLAYERS, 0.5F, 1.0F);
        if (progress > 1F) {
            nextSoundProgressTarget = -1;
            return;
        }
        nextSoundProgressTarget += SOUND_PROGRESS_STEP;
    }

    public int getMaxUseTime(ItemStack stack) {
        return getPullTime(stack) + 3;
    }

    public static int getPullTime(ItemStack stack) {
        int quickChargeLevel = EnchantmentHelper.getLevel(Enchantments.QUICK_CHARGE, stack);
        return quickChargeLevel == 0 ? 100 : 100 - 20 * quickChargeLevel;
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.CROSSBOW;
    }

    private static float getPullProgress(int useTicks, ItemStack stack) {
        float f = (float)useTicks / (float)getPullTime(stack);
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        final int stage = getLoadingStage(stack);
        switch(stage) {
            case Stage.POWDERED -> tooltip.add(Text.translatable("item.unbalancedmusket.musket.stage.powdered").formatted(Formatting.GRAY));
            case Stage.RAMMED -> tooltip.add(Text.translatable("item.unbalancedmusket.musket.stage.rammed").formatted(Formatting.GRAY));
            case Stage.LOADED -> {
                if (context.isAdvanced()) {
                    final ItemStack projectile = getProjectile(stack);
                    tooltip.add(Text.translatable("item.unbalancedmusket.musket.projectile").append(" ").append(projectile.toHoverableText()));
                }
                tooltip.add(Text.translatable("item.unbalancedmusket.musket.stage.loaded").formatted(Formatting.GRAY));
            }
        }
    }

    public boolean isUsedOnRelease(ItemStack stack) {
        return stack.isOf(this);
    }

    public int getRange() {
        return 8;
    }
}

