package vbonedra.nine_challenges.mixin.challenges.badder_bosses.dragon;

import net.minecraft.AttributeInstance;
import net.minecraft.EntityDragon;
import net.minecraft.EntityLiving;
import net.minecraft.SharedMonsterAttributes;
import net.minecraft.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vbonedra.nine_challenges.challenge.Challenge;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;

@Mixin(EntityDragon.class)
public abstract class EntityDragonMixin extends EntityLiving {

    public EntityDragonMixin(World world) {
        super(world);
    }

    @Inject(method = "applyEntityAttributes", at = @At("RETURN"))
    private void applyEntityAttributes_BadderBossesHealthBoost(CallbackInfo ci) {
        if (!isChallengeEnabled(Challenge.BADDER_BOSSES)) return;

        AttributeInstance maxHealth = this.getEntityAttribute(SharedMonsterAttributes.maxHealth);
        if (maxHealth != null) {
            maxHealth.setAttribute(maxHealth.getAttributeValue() * 2.0D);
        }
    }

}