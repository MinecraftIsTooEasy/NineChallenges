package vbonedra.nine_challenges.mixin.gui;

import net.minecraft.NBTTagCompound;
import net.minecraft.WorldInfo;
import net.minecraft.WorldSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vbonedra.nine_challenges.challenge.ChallengeHolder;
import vbonedra.nine_challenges.challenge.Challenges;

@Mixin(WorldInfo.class)
public class WorldInfoMixin implements ChallengeHolder {
    @Unique private static final String KEY = "NineChallenges";
    @Unique private int challengeMask;

    @Override public int nineChallenges$getChallengeMask() {
        return this.challengeMask;
    }
    @Override public void nineChallenges$setChallengeMask(int mask) {
        this.challengeMask = mask;
    }

    // loading level.dat
    @Inject(method = "<init>(Lnet/minecraft/NBTTagCompound;)V", at = @At("TAIL"))
    private void init_ReadChallengesNbt(NBTTagCompound nbt, CallbackInfo ci) {
        this.challengeMask = nbt.hasKey(KEY) ? nbt.getInteger(KEY) : 0;
    }

    // new world
    @Inject(method = "<init>(Lnet/minecraft/WorldSettings;Ljava/lang/String;)V", at = @At("TAIL"))
    private void init(WorldSettings worldSettings, String name, CallbackInfo ci) {
        this.challengeMask = Challenges.holder(worldSettings).nineChallenges$getChallengeMask();
    }

    // saving level.dat
    @Inject(method = "updateTagCompound", at = @At("TAIL"))
    private void updateTagCompound_WriteChallengesNbt(NBTTagCompound tag, NBTTagCompound playerTag, CallbackInfo ci) {
        tag.setInteger(KEY, this.challengeMask);
    }
}