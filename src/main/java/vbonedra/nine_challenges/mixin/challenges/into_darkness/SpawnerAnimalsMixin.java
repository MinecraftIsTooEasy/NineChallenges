package vbonedra.nine_challenges.mixin.challenges.into_darkness;

import net.minecraft.EntityPlayer;
import net.minecraft.SpawnerAnimals;
import net.minecraft.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.challenge.Challenges;

@Mixin(SpawnerAnimals.class)
public abstract class SpawnerAnimalsMixin {

    // TODO: playtest needed
    @Redirect(method = "trySpawningHostileMobs", at = @At(value = "INVOKE", target = "Lnet/minecraft/WorldServer;getClosestPlayer(DDDDZ)Lnet/minecraft/EntityPlayer;"))
    private EntityPlayer trySpawningHostileMobs_DarknessSpawn(WorldServer world, double x, double y, double z, double distance, boolean mustBeAlive) {
        if (Challenges.isChallengeEnabled(Challenge.INTO_DARKNESS) && distance == 8.0D && !mustBeAlive) {
            distance = 2.0D;
        }
        return world.getClosestPlayer(x, y, z, distance, mustBeAlive);
    }

}