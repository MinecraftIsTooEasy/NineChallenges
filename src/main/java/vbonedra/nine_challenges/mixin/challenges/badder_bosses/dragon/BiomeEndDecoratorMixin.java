package vbonedra.nine_challenges.mixin.challenges.badder_bosses.dragon;

import net.minecraft.BiomeDecorator;
import net.minecraft.BiomeEndDecorator;
import net.minecraft.BiomeGenBase;
import net.minecraft.WorldGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.util.EndPillarGenHelper;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;

@Mixin(BiomeEndDecorator.class)
public abstract class BiomeEndDecoratorMixin extends BiomeDecorator {

    @Shadow protected WorldGenerator spikeGen;

    public BiomeEndDecoratorMixin(BiomeGenBase par1BiomeGenBase) {
        super(par1BiomeGenBase);
    }

    @Inject(method = "decorate", at = @At("TAIL"))
    private void decorate_ExtraPillar(CallbackInfo ci) {
        if (!isChallengeEnabled(Challenge.BADDER_BOSSES)) return;

        if (this.randomGenerator.nextFloat() <= 0.3F) {
            int x = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            int z = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            int y = this.currentWorld.getTopSolidOrLiquidBlock(x, z);
            EndPillarGenHelper.generateSecondCrystal = true;
            this.spikeGen.generate(this.currentWorld, this.randomGenerator, x, y, z);
            EndPillarGenHelper.generateSecondCrystal = false;
        }
    }

}