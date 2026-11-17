package vbonedra.nine_challenges.mixin.challenges.hostile_champions;

import net.minecraft.*;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.util.hostile_champion.HostileChampion;
import vbonedra.nine_challenges.util.hostile_champion.HostileChampionTypeGetter;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;
import static vbonedra.nine_challenges.manylib.NineChallengesConfigs.*;
import static vbonedra.nine_challenges.util.hostile_champion.HostileChampion.colorMap;
import static vbonedra.nine_challenges.util.hostile_champion.RenderChampionOverlayHelper.*;

@Mixin(RendererLivingEntity.class)
public abstract class RendererLivingEntityMixin {

    @Shadow protected ModelBase mainModel;

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

    // TODO: check all entities
    @Inject(method = "renderModelGlowing(Lnet/minecraft/EntityLivingBase;FFFFFF)V", at = @At("RETURN"))
    private void renderModelGlowing_ChampionOverlay(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch, float scale, CallbackInfo ci) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;
        if (entity.isInvisible()) return;

        byte type = HostileChampionTypeGetter.getHostileChampionType(entity);
        if (!HostileChampion.hasAnyHostileChampionType(type)) return;

        Integer colorObj = colorMap.get(type);
        if (colorObj == null) return;
        int color = colorObj;

        float r = ((color >> 16) & 255) / 255.0F * (float) ValueChallengeHostileChampionsOverlayBrightness.getDoubleValue();
        float g = ((color >> 8) & 255) / 255.0F * (float) ValueChallengeHostileChampionsOverlayBrightness.getDoubleValue();
        float b = (color & 255) / 255.0F * (float) ValueChallengeHostileChampionsOverlayBrightness.getDoubleValue();

        float centerY = (float)((entity.boundingBox.maxY - entity.boundingBox.minY) * 0.5);

        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);

        GL11.glMatrixMode(GL11.GL_TEXTURE);
        GL11.glPushMatrix();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPushMatrix();

        try {
            Minecraft.getMinecraft().getTextureManager().bindTexture(getChampionOverlay(
                    this.mainModel.textureWidth,
                    this.mainModel.textureHeight,
                    HostileChampionTypeGetter.getHostileChampionType(entity) == HostileChampion.Giant
            ));

            GL11.glMatrixMode(GL11.GL_TEXTURE);
            GL11.glLoadIdentity();
            float speed = entity.ticksExisted * (float) ValueChallengeHostileChampionsOverlaySpeed.getDoubleValue();
            GL11.glTranslatef(speed, speed, speed);
            GL11.glMatrixMode(GL11.GL_MODELVIEW);

            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthFunc(GL11.GL_LEQUAL);
            GL11.glDepthMask(false);

            GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
            GL11.glPolygonOffset(-1.0F, -1.0F);

            GL11.glColor4f(r, g, b, 1.0F);

            GL11.glTranslatef(0.0F, centerY, 0.0F);
            GL11.glScalef(
                    (float) ValueChallengeHostileChampionsOverlayScale.getDoubleValue(),
                    (float) ValueChallengeHostileChampionsOverlayScale.getDoubleValue(),
                    (float) ValueChallengeHostileChampionsOverlayScale.getDoubleValue()
            );
            GL11.glTranslatef(0.0F, -centerY, 0.0F);

            this.mainModel.render(entity, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch, scale);
        } finally {
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glPopMatrix();
            GL11.glMatrixMode(GL11.GL_TEXTURE);
            GL11.glPopMatrix();
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glPopAttrib();
        }
    }
}