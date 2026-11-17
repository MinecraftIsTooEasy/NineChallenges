package vbonedra.nine_challenges.mixin.challenges.hostile_champions.giant;

import net.minecraft.*;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.util.hostile_champion.HostileChampion;
import vbonedra.nine_challenges.util.hostile_champion.HostileChampionTypeGetter;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;

@Mixin(RenderHorse.class)
public abstract class RenderHorseMixin {

    @Inject(method = "renderModel(Lnet/minecraft/EntityLivingBase;FFFFFF)V", at = @At("HEAD"))
    private void renderModel_GiantScale(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch, float scale, CallbackInfo ci) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;

        byte hostileChampionType = HostileChampionTypeGetter.getHostileChampionType(entity);
        if (hostileChampionType == HostileChampion.Giant) {
            float giantScale = HostileChampion.championGiantScale;
            GL11.glTranslatef(0.0F, 24.0F * scale * (1.0F - giantScale), 0.0F);
            GL11.glScalef(giantScale, giantScale, giantScale);
        }
    }

}