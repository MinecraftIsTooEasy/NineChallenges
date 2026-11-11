package vbonedra.nine_challenges.network;

import moddedmite.rustedironcore.network.PacketReader;
import net.minecraft.Packet250CustomPayload;
import net.minecraft.ResourceLocation;
import net.minecraft.ServerPlayer;
import net.xiaoyu233.fml.FishModLoader;
import vbonedra.nine_challenges.challenge.Challenges;

import java.nio.ByteBuffer;

import static vbonedra.nine_challenges.NineChallengesMod.NINE_CHALLENGES_MOD_ID;

public final class NineChallengesNetwork {
    private NineChallengesNetwork() {}

    public static final String CHANNEL = NINE_CHALLENGES_MOD_ID; // TODO: use ResourceLocation
    public static final ResourceLocation HOSTILE_CHAMPION_TYPE = new ResourceLocation(NINE_CHALLENGES_MOD_ID + ":hostileChampionType");

    public static void init() {
        if (!FishModLoader.isServer()) {
            PacketReader.registerClientPacketReader(HOSTILE_CHAMPION_TYPE, S2CSendHostileChampionType::new);
        }
    }

    // send world data to player
    public static void sendChallengeMask(ServerPlayer player, int mask) {
        byte[] data = ByteBuffer.allocate(4).putInt(mask).array();
        player.playerNetServerHandler.sendPacketToPlayer(new Packet250CustomPayload(CHANNEL, data));
    }

    // check if its right packet + save to client
    public static boolean receiveChallengeMask(Packet250CustomPayload packet) {
        if (!CHANNEL.equals(packet.channel) || packet.data == null || packet.data.length < 4) {
            return false;
        }
        Challenges.setClientMask(ByteBuffer.wrap(packet.data).getInt());
        return true;
    }
}