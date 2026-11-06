package vbonedra.nine_challenges.challenge;

import net.minecraft.server.MinecraftServer;
import net.minecraft.World;
import net.minecraft.WorldInfo;

public final class Challenges {
    private Challenges() {}

    public static ChallengeHolder holder(Object o) {
        return (ChallengeHolder) o;
    }

    public static int getMask(WorldInfo worldInfo) {
        return worldInfo == null ? 0 : holder(worldInfo).nineChallenges$getChallengeMask();
    }

    public static boolean isChallengeEnabled(WorldInfo worldInfo, Challenge challenge) {
        return challenge.isIn(getMask(worldInfo));
    }

    public static boolean isChallengeEnabled(World world, Challenge challenge) {
        WorldInfo worldInfo = world.getWorldInfo();
        if (world.isRemote) {
            MinecraftServer server = MinecraftServer.getServer();
            if (server != null) worldInfo = server.worldServers[0].getWorldInfo();
        }
        return isChallengeEnabled(worldInfo, challenge);
    }
}