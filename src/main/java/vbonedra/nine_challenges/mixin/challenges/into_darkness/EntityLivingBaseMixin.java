package vbonedra.nine_challenges.mixin.challenges.into_darkness;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.challenge.Challenges;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static vbonedra.nine_challenges.NineChallengesMod.NINE_CHALLENGES_MOD_ID;

@Mixin(EntityLivingBase.class)
public abstract class EntityLivingBaseMixin extends Entity {

    @Unique private static final UUID darknessSlownessUUID = UUID.nameUUIDFromBytes((NINE_CHALLENGES_MOD_ID+":darkness_slow").getBytes(StandardCharsets.UTF_8));
    @Unique private static final double darknessSlownessAdd = -0.2D;

    @Shadow public abstract BaseAttributeMap getAttributeMap();
    @Shadow public abstract boolean isPotionActive(Potion par1Potion);

    public EntityLivingBaseMixin(World par1World) {
        super(par1World);
    }

    @Inject(method = "onLivingUpdate", at = @At("HEAD"))
    private void onLivingUpdate_SlownessInDarkness(CallbackInfo ci) {
        if (Challenges.isChallengeEnabled(Challenge.INTO_DARKNESS)) {
            if (this.worldObj == null || !this.isEntityPlayer()) return;

            BaseAttributeMap attributeMap = this.getAttributeMap();
            if (attributeMap == null) return;

            AttributeInstance movementSpeedAttribute = attributeMap.getAttributeInstance(SharedMonsterAttributes.movementSpeed);
            if (movementSpeedAttribute == null) return;

            AttributeModifier activeModifier = movementSpeedAttribute.getModifier(darknessSlownessUUID);

            if (this.worldObj.getBlockLightValue(
                    MathHelper.floor_double(this.posX),
                    MathHelper.floor_double(this.boundingBox.minY),
                    MathHelper.floor_double(this.posZ)
            ) <= 0 && !this.isPotionActive(Potion.nightVision)) {
                if (activeModifier == null) {
                    AttributeModifier newModifier = new AttributeModifier(
                            darknessSlownessUUID,
                            "Darkness Slowness Modifier",
                            darknessSlownessAdd,
                            2
                    ).setSaved(false);
                    movementSpeedAttribute.applyModifier(newModifier);
                }
            } else if (activeModifier != null) {
                movementSpeedAttribute.removeModifier(activeModifier);
            }
        }
    }

}