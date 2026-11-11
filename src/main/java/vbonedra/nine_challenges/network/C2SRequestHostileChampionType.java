package vbonedra.nine_challenges.network;

import net.minecraft.*;
import moddedmite.rustedironcore.network.Packet;
import moddedmite.rustedironcore.network.PacketByteBuf;
import moddedmite.rustedironcore.network.Network;
import vbonedra.nine_challenges.util.IHostileChampionType;

public record C2SRequestHostileChampionType(byte entityId) implements Packet {

    public C2SRequestHostileChampionType(PacketByteBuf buf) {
        this(buf.readByte());
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeInt(this.entityId);
    }

    @Override
    public void apply(EntityPlayer player) {
        if (player instanceof ServerPlayer serverPlayer && player.worldObj instanceof WorldServer worldServer) {
            Entity entity = worldServer.getEntityByID(this.entityId);

            byte hostileChampionType = 0;
            if (entity instanceof IHostileChampionType celestialEntity) {
                hostileChampionType = celestialEntity.NC$getHostileChampionType();
            }

            Network.sendToClient(serverPlayer, new S2CResponseHostileChampionType(this.entityId, hostileChampionType));
        }
    }

    @Override
    public ResourceLocation getChannel() {
        return NineChallengesNetwork.REQUEST_HOSTILE_CHAMPION_TYPE;
    }
}
