package vbonedra.nine_challenges.util;

import net.minecraft.Entity;
import vbonedra.nine_challenges.challenge.Challenge;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;

public final class ChampionEnchantContext {
    public static final ThreadLocal<Entity> VICTIM = new ThreadLocal<>();

    private ChampionEnchantContext() {}

    public static boolean isEnchantImmune(Entity entity) {
        return entity != null
                && isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)
                && entity instanceof IHostileChampionType iHostileChampionType
                && iHostileChampionType.NC$getHostileChampionType() == HostileChampion.Antimagic;
    }

    public static boolean isCurrentVictimEnchantImmune() {
        return isEnchantImmune(VICTIM.get());
    }
}