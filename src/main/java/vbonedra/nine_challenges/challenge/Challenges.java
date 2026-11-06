package vbonedra.nine_challenges.challenge;

import net.minecraft.server.MinecraftServer;
import net.minecraft.World;
import net.minecraft.WorldInfo;

public final class Challenges {
    private Challenges() {}

    /** Safe cast for mixin-implemented interfaces (works even on final classes). */
    public static ChallengeHolder holder(Object o) {
        return (ChallengeHolder) o;
    }

    public static int getMask(WorldInfo worldInfo) {
        return worldInfo == null ? 0 : holder(worldInfo).nineChallenges$getChallengeMask();
    }

    public static boolean isEnabled(WorldInfo worldInfo, Challenge c) {
        return c.isIn(getMask(worldInfo));
    }

    public static boolean hasAny(WorldInfo worldInfo) {
        return getMask(worldInfo) != 0;
    }

    /** Works on both logical sides in singleplayer (client world worldInfo doesn't carry the mask). */
    public static boolean isEnabled(World world, Challenge c) {
        WorldInfo worldInfo = world.getWorldInfo();
        if (world.isRemote) {
            MinecraftServer server = MinecraftServer.getServer();
            if (server != null) worldInfo = server.worldServers[0].getWorldInfo();
        }
        return isEnabled(worldInfo, c);
    }
}