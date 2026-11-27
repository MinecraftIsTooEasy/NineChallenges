package vbonedra.nine_challenges.util.hostile_champion;

import net.minecraft.Entity;
import net.minecraft.EntityLivingBase;
import net.minecraft.StatCollector;
import vbonedra.nine_challenges.challenge.Challenge;

import java.util.*;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;
import static vbonedra.nine_challenges.manylib.NineChallengesConfigs.UseChallengeHostileChampionsNameColors;

public class HostileChampion {
    public static final Map<Byte, String> langKeyMap = new HashMap<>();
    public static final Map<Byte, Integer> colorMap = new HashMap<>();
    private static final Map<Byte, String> prefixColorMap = new HashMap<>();

    private static byte initHostileChampionType(int id, String langKey, int color, String prefixColor) {
        return initHostileChampionType((byte) id, langKey, color, prefixColor);
    }
    private static byte initHostileChampionType(byte id, String langKey, int color, String prefixColor) {
        langKeyMap.put(id, langKey);
        colorMap.put(id, color);
        prefixColorMap.put(id, prefixColor);
        return id;
    }

    public static final float spawnChance = 0.1F;

    public static final byte Unset = initHostileChampionType(-1, "", 0, "");
    public static final byte Vanilla = initHostileChampionType(0, "", 0, "");

    public static final byte Blazing = initHostileChampionType(1, "entity.challenge.hostile_champions.blazing.name", 0xff8800, "§6");
    public static final float championBlazingDamageMultiplier = 1.25F;
    public static final int championBlazingFireAspectSeconds = 4;
    public static final float championBlazingExplosionSize = 2.0F;
    public static final float championBlazingExplosionFireChance = 0.6847F * 2F / 3F;

    public static final byte Projecting = initHostileChampionType(2, "entity.challenge.hostile_champions.projecting.name", 0x8800ff, "§5");
    public static final float championProjectingReachMultiplier = 1.5F;
    public static final float championProjectingDamageMultiplierMelee = 1.25F;
    public static final float championProjectingDamageMultiplierRanged = 1.25F;

    public static final byte Antimagic = initHostileChampionType(3, "entity.challenge.hostile_champions.antimagic.name", 0x00ff00, "§a");
    public static final float championAntimagicHealthMultiplier = 1.5F;

    public static final byte Giant = initHostileChampionType(4, "entity.challenge.hostile_champions.giant.name", 0x0088ff, "§b");
    public static final float championGiantScale = 1.9F;
    public static final float championGiantReachMultiplier = 1.25F;
    public static final float championGiantHealthMultiplier = 5F;
    public static final float championGiantExplosionMultiplier = 2.0F;
    public static final int championGiantAdditionalDropAmount = 3;

    public static final byte Blessed = initHostileChampionType(5, "entity.challenge.hostile_champions.blessed.name", 0xffff00, "§e");
    public static final float championBlessedRegenerationMultiplier = 4F;

    public static final byte Growing = initHostileChampionType(6, "entity.challenge.hostile_champions.growing.name", 0xff0000, "§c");
    public static final int championGrowingMinGrowTicks = 20 * 20;
    public static final int championGrowingMaxGrowTicks = championGrowingMinGrowTicks + 5 * 60 * 20;
    public static final float championGrowingAdditionalDamagePerTick = 0.01F / 20;
    public static final float championGrowingAdditionalEffectiveHealthPerTick = 0.01F / 20;


    public static Set<Byte> getAllChampionTypes() {
        return new LinkedHashSet<>(Set.of(Blazing, Projecting, Antimagic, Giant, Blessed, Growing));
    }

    public static boolean hasAnyHostileChampionType(int hostileChampionType) {
        return hostileChampionType != Unset && hostileChampionType != Vanilla;
    }


    public static String getPrefix(byte type) {
        if (!hasAnyHostileChampionType(type)) return "";
        String key = langKeyMap.get(type);
        if (key == null || key.isEmpty()) return "";

        if (UseChallengeHostileChampionsNameColors.getBooleanValue()) {
            String color = prefixColorMap.getOrDefault(type, "");
            return color + StatCollector.translateToLocal(key) + "§r ";
        } else {
            return StatCollector.translateToLocal(key);
        }
    }

    public static String getNameWithPrefix(Entity entity, String name) {
        if (name == null || !(entity instanceof EntityLivingBase)) return name;
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return name;

        byte hostileChampionType = HostileChampionTypeGetter.getHostileChampionType(entity);
        if (!HostileChampion.hasAnyHostileChampionType(hostileChampionType)) return name;

        String prefix = HostileChampion.getPrefix(hostileChampionType);
        if (prefix.isEmpty() || name.startsWith(prefix)) return name;
        return prefix + name;
    }

}
