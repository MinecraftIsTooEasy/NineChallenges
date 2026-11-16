package vbonedra.nine_challenges.mixin.challenges.hostile_champions.giant;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.util.HostileChampion;
import vbonedra.nine_challenges.util.HostileChampionTypeGetter;
import vbonedra.nine_challenges.util.IGiantHitboxHelper;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;

@Mixin(Entity.class)
public abstract class EntityMixin implements IGiantHitboxHelper {

    @Shadow public float width;
    @Shadow public float height;
    @Shadow public double posX;
    @Shadow public double posY;
    @Shadow public double posZ;
    @Final @Shadow public AxisAlignedBB boundingBox;

    @Unique private float naturalWidth = -1.0F;
    @Unique private float naturalHeight = -1.0F;

    @Override public float NC$getNaturalWidth()  {
        return this.naturalWidth;
    }
    @Override public float NC$getNaturalHeight() {
        return this.naturalHeight;
    }

    @Override
    public void NC$refreshGiantHitbox() {
        Entity entity = (Entity)(Object)this;

        if (entity instanceof EntityAgeable entityAgeable) {
            entityAgeable.setScaleForAge(entityAgeable.isChild());
            return;
        }

        if (this.naturalWidth <= 0) {
            if (entity.width > 0.0F) {
                entity.setSize(entity.width, entity.height);
            }
            return;
        }

        entity.setSize(this.naturalWidth, this.naturalHeight);
    }

    @Inject(method = "setSize", at = @At("HEAD"), cancellable = true)
    private void setSize_Giant(float width, float height, CallbackInfo ci) {
        Entity entity = (Entity)(Object)this;

        boolean giant = isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)
                && entity instanceof EntityLivingBase living
                && HostileChampionTypeGetter.getHostileChampionType(living) == HostileChampion.Giant;

        if (!giant) {
            this.naturalWidth  = width;
            this.naturalHeight = height;
            return;
        }

        float naturalW, naturalH;
        if (entity instanceof EntityAgeable) {
            naturalW = width;
            naturalH = height;
        } else {
            float s = HostileChampion.championGiantScale;
            boolean isRescale = this.naturalWidth > 0
                    && Math.abs(width  - this.naturalWidth  * s) < 1.0E-4F
                    && Math.abs(height - this.naturalHeight * s) < 1.0E-4F;
            naturalW = isRescale ? this.naturalWidth  : width;
            naturalH = isRescale ? this.naturalHeight : height;
        }
        this.naturalWidth  = naturalW;
        this.naturalHeight = naturalH;

        float scale   = HostileChampion.championGiantScale;
        float scaledW = naturalW * scale;
        float scaledH = naturalH * scale;

        this.width  = scaledW;
        this.height = scaledH;
        this.boundingBox.setBounds(
                this.posX - scaledW * 0.5,
                this.posY,
                this.posZ - scaledW * 0.5,
                this.posX + scaledW * 0.5,
                this.posY + scaledH,
                this.posZ + scaledW * 0.5
        );
        ci.cancel();
    }

}