package vbonedra.nine_challenges.network;

import net.minecraft.*;
import moddedmite.rustedironcore.network.Packet;
import moddedmite.rustedironcore.network.PacketByteBuf;
import vbonedra.nine_challenges.util.HostileChampionTypeCache;

public class S2CResponseHostileChampionType implements Packet {
    private final int entityId;
    private final byte hostileChampionType;

    public S2CResponseHostileChampionType(int entityId, byte hostileChampionType) {
        this.entityId = entityId;
        this.hostileChampionType = hostileChampionType;
    }

    public S2CResponseHostileChampionType(PacketByteBuf buf) {
        this.entityId = buf.readInt();
        this.hostileChampionType = buf.readByte();
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeInt(this.entityId);
        buf.writeByte(this.hostileChampionType);
    }

    @Override
    public void apply(EntityPlayer player) {
        if (player != null && player.worldObj != null && player.worldObj.isRemote) {
            HostileChampionTypeCache.receiveHostileChampionTypeFromServer(this.entityId, this.hostileChampionType);
        }
    }

    @Override
    public ResourceLocation getChannel() {
        return NineChallengesNetwork.RESPONSE_HOSTILE_CHAMPION_TYPE;
    }
}
