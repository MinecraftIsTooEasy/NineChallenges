package vbonedra.nine_challenges.mixin.challenges.hostile_champions;

import net.minecraft.*;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vbonedra.nine_challenges.util.HostileChampion;
import vbonedra.nine_challenges.util.HostileChampionTypeGetter;

import static vbonedra.nine_challenges.NineChallengesMod.NINE_CHALLENGES_MOD_ID;
import static vbonedra.nine_challenges.util.HostileChampion.colorMap;

@Mixin(RendererLivingEntity.class)
public abstract class RendererLivingEntityMixin {

    // TODO: breaks for
    //  slime - breaks core-outline render
    //  donkey and horse - no overlay
    //  chicken - static overlay with no scaling
    //  sheep - static overlay under wool

    @Unique private static final float overlayScale = 1.01F;
    @Unique private static final float modelCenterY = 12.0F * 0.0625F;
    @Unique private static final float overlaySpeed = 0.0044F;
    @Unique private static final float overlayBrightness = 1F;
    @Unique private static final ResourceLocation championOverlay = new ResourceLocation(NINE_CHALLENGES_MOD_ID, "textures/entity/champion_overlay.png");

    @Shadow protected ModelBase mainModel;

    @Inject(method = "renderModel(Lnet/minecraft/EntityLivingBase;FFFFFF)V", at = @At("RETURN"))
    private void renderModel_ChampionOverlay(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch, float scale, CallbackInfo ci) {
        if (entity.isInvisible()) return;

        byte hostileChampionType = HostileChampionTypeGetter.getHostileChampionType(entity);
        if (!HostileChampion.hasAnyHostileChampionType(hostileChampionType)) return;

        int color = colorMap.get(hostileChampionType);
        float r = ((color >> 16) & 255) / 255.0F * overlayBrightness;
        float g = ((color >> 8) & 255) / 255.0F * overlayBrightness;
        float b = (color & 255) / 255.0F * overlayBrightness;

        Minecraft.getMinecraft().getTextureManager().bindTexture(championOverlay);

        GL11.glMatrixMode(GL11.GL_TEXTURE);
        GL11.glLoadIdentity();
        GL11.glTranslatef(ageInTicks * overlaySpeed, ageInTicks * overlaySpeed, 0.0F);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
        GL11.glDepthMask(false);
        GL11.glColor4f(r, g, b, 1.0F);

        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);

        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, modelCenterY, 0.0F);
        GL11.glScalef(overlayScale, overlayScale, overlayScale);
        GL11.glTranslatef(0.0F, -modelCenterY, 0.0F);

        this.mainModel.render(entity, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch, scale);

        GL11.glPopMatrix();

        OpenGlHelper.restorePreviousLightmapTextureCoords();

        GL11.glDepthMask(true);
        GL11.glMatrixMode(GL11.GL_TEXTURE);
        GL11.glLoadIdentity();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
}