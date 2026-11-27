package vbonedra.nine_challenges.challenge;

import net.minecraft.server.MinecraftServer;
import net.minecraft.WorldInfo;

public final class Challenges {

    private static volatile int clientMask = 0;

    public static ChallengeHolder holder(Object object) {
        return (ChallengeHolder) object;
    }

    public static boolean isChallengeEnabled(Challenge challenge) {
        return challenge.isIn(getActiveMask());
    }

    public static boolean hasAny() {
        return getActiveMask() != 0;
    }

    public static int getActiveMask() {
        MinecraftServer server = MinecraftServer.getServer();
        if (server != null && server.worldServers != null && server.worldServers[0] != null) {
            return getMask(server.worldServers[0].getWorldInfo());
        }
        return clientMask;
    }

    public static int getMask(WorldInfo worldInfo) {
        return worldInfo == null ? 0 : holder(worldInfo).nineChallenges$getChallengeMask();
    }

    public static int getServerMask() {
        MinecraftServer server = MinecraftServer.getServer();
        if (server != null && server.worldServers != null && server.worldServers[0] != null) {
            return getMask(server.worldServers[0].getWorldInfo());
        }
        return 0;
    }

    public static boolean isChallengeEnabled(WorldInfo worldInfo, Challenge challenge) {
        return challenge.isIn(getMask(worldInfo));
    }

    public static void setClientMask(int mask) {
        clientMask = mask;
    }
    public static void resetClientMask() {
        clientMask = 0;
    }
}