package vbonedra.nine_challenges.network;

import net.minecraft.Packet250CustomPayload;
import net.minecraft.ServerPlayer;
import vbonedra.nine_challenges.challenge.Challenges;

import java.nio.ByteBuffer;

import static vbonedra.nine_challenges.NineChallengesMod.NINE_CHALLENGES_MOD_ID;

public final class ChallengeNetwork {
    private ChallengeNetwork() {}

    public static final String CHANNEL = NINE_CHALLENGES_MOD_ID;

    // send world data to player
    public static void sendTo(ServerPlayer player, int mask) {
        byte[] data = ByteBuffer.allocate(4).putInt(mask).array();
        player.playerNetServerHandler.sendPacketToPlayer(new Packet250CustomPayload(CHANNEL, data));
    }

    // check if its right packet + save to client
    public static boolean handleClient(Packet250CustomPayload packet) {
        if (!CHANNEL.equals(packet.channel) || packet.data == null || packet.data.length < 4) {
            return false;
        }
        Challenges.setClientMask(ByteBuffer.wrap(packet.data).getInt());
        return true;
    }
}