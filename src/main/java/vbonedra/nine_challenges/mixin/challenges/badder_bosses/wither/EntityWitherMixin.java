package vbonedra.nine_challenges.mixin.challenges.badder_bosses.wither;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vbonedra.nine_challenges.challenge.Challenge;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;

@Mixin(EntityWither.class)
public abstract class EntityWitherMixin extends EntityLiving {

    @Unique private EntityWitherSkull lastSkull;

    public EntityWitherMixin(World world) {
        super(world);
    }

    @Inject(method = "applyEntityAttributes", at = @At("RETURN"))
    private void applyEntityAttributes_Health(CallbackInfo ci) {
        if (!isChallengeEnabled(Challenge.BADDER_BOSSES)) return;
        AttributeInstance maxHealth = this.getEntityAttribute(SharedMonsterAttributes.maxHealth);
        if (maxHealth != null) {
            maxHealth.setAttribute(maxHealth.getAttributeValue() * 1.35D);
        }
    }

    @Redirect(method = "func_82209_a", at = @At(value = "NEW", target = "net/minecraft/EntityWitherSkull"))
    private EntityWitherSkull func_82209_a_SaveSkull(World par1World, EntityLivingBase par2EntityLivingBase, double par3, double par5, double par7) {
        EntityWitherSkull skull = new EntityWitherSkull(par1World, par2EntityLivingBase, par3, par5, par7);
        this.lastSkull = skull;
        return skull;
    }

    @Inject(method = "func_82209_a", at = @At("RETURN"))
    private void func_82209_a_BlueChance(int par1, double par2, double par4, double par6, boolean isBlue, CallbackInfo ci) {
        if (!isChallengeEnabled(Challenge.BADDER_BOSSES)) return;
        if (isBlue) return;
        EntityWitherSkull skull = this.lastSkull;
        this.lastSkull = null;
        if (skull != null) {
            if (this.rand.nextFloat() >= 0.15F) return;
            skull.setInvulnerable(true);
        }
    }

}