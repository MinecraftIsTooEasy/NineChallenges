package vbonedra.nine_challenges.mixin.challenges.hostile_champions.giant;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.util.hostile_champion.HostileChampion;
import vbonedra.nine_challenges.util.hostile_champion.IHostileChampionType;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;


@Mixin(World.class)
public abstract class WorldMixin {

    @ModifyVariable(method = "createExplosion", at = @At("HEAD"), ordinal=0, argsOnly = true)
    private float createExplosion_GiantBlocks(float explosion_size_vs_blocks, Entity exploder) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return explosion_size_vs_blocks;

        if (exploder instanceof IHostileChampionType iHostileChampionType && iHostileChampionType.NC$getHostileChampionType() == HostileChampion.Giant) {
            return explosion_size_vs_blocks * HostileChampion.championGiantExplosionMultiplier;
        }

        return explosion_size_vs_blocks;
    }

    @ModifyVariable(method = "createExplosion", at = @At("HEAD"), ordinal=1, argsOnly = true)
    private float createExplosion_GiantEntities(float explosion_size_vs_living_entities, Entity exploder) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return explosion_size_vs_living_entities;

        if (exploder instanceof IHostileChampionType iHostileChampionType && iHostileChampionType.NC$getHostileChampionType() == HostileChampion.Giant) {
            return explosion_size_vs_living_entities * HostileChampion.championGiantExplosionMultiplier;
        }

        return explosion_size_vs_living_entities;
    }



}
