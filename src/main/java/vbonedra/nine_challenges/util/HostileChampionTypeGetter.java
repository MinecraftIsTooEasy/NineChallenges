package vbonedra.nine_challenges.util;

import net.minecraft.Entity;

public class HostileChampionTypeGetter {

    public static byte getHostileChampionType(Entity entity) {
        if (entity == null || entity.worldObj == null) {
            return HostileChampionType.Vanilla;
        }

        if (!entity.worldObj.isRemote) {
            if (entity instanceof IHostileChampionType) {
                byte type = ((IHostileChampionType) entity).NC$getHostileChampionType();
                return type == HostileChampionType.Unset ? HostileChampionType.Vanilla : type;
            }
            return HostileChampionType.Vanilla;
        }

        Byte cachedType = HostileChampionTypeCache.clientHostileChampionTypeMap.get(entity.entityId);
        return cachedType != null ? cachedType : HostileChampionType.Vanilla;
    }

}