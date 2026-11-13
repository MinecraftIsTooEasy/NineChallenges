package vbonedra.nine_challenges.mixin.challenges.hostile_champions.giant;

import net.minecraft.EntityCubic;
import net.minecraft.EntityLiving;
import net.minecraft.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.util.HostileChampion;
import vbonedra.nine_challenges.util.IHostileChampionType;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;

@Mixin(EntityCubic.class)
public abstract class EntityCubicMixin extends EntityLiving {

    public EntityCubicMixin(World par1World) {
        super(par1World);
    }

    @Shadow public abstract int getSize();

    @Inject(method = "updateSize()V", at = @At("RETURN"))
    private void updateSize_Giant(CallbackInfo ci) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;
        if (((IHostileChampionType) this).NC$getHostileChampionType() == HostileChampion.Giant) {
            float baseSize = 0.5F * (float) this.getSize();
            this.setSize(baseSize * HostileChampion.championGiantScale, baseSize * HostileChampion.championGiantScale);
        }
    }

    @Inject(method = "getRenderSizeModifier()F", at = @At("RETURN"), cancellable = true)
    private void getRenderSizeModifier_Giant(CallbackInfoReturnable<Float> cir) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;
        if (((IHostileChampionType) this).NC$getHostileChampionType() == HostileChampion.Giant) {
            cir.setReturnValue(cir.getReturnValueF() * HostileChampion.championGiantScale);
        }
    }

    @Inject(method = "getReachSquared()F", at = @At("RETURN"), cancellable = true)
    private void getReachSquared_Giant(CallbackInfoReturnable<Float> cir) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;
        if (((IHostileChampionType) this).NC$getHostileChampionType() == HostileChampion.Giant) {
            cir.setReturnValue(cir.getReturnValueF() * HostileChampion.championGiantReachMultiplier * HostileChampion.championGiantReachMultiplier);
        }
    }

}
