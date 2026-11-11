package vbonedra.nine_challenges.util;

import net.minecraft.StatCollector;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class HostileChampionType {
    public static final Map<Byte, String> langKeyMap = new HashMap<>();
    public static final Map<Byte, Integer> colorMap = new HashMap<>();

    private static byte initHostileChampionType(int id, String langKey, int color) {
        return initHostileChampionType((byte) id, langKey, color);
    }
    private static byte initHostileChampionType(byte id, String langKey, int color) {
        langKeyMap.put(id, langKey);
        colorMap.put(id, color);
        return id;
    }
    public static final byte Unset = initHostileChampionType(-1, "", 0);
    public static final byte Vanilla = initHostileChampionType(0, "", 0);
    public static final byte Blazing = initHostileChampionType(1, "entity.challenge.hostile_champions.blazing.name", 0xff8800);
    public static final byte Projecting = initHostileChampionType(2, "entity.challenge.hostile_champions.projecting.name", 0x8800ff);
    public static final byte Antimagic = initHostileChampionType(3, "entity.challenge.hostile_champions.antimagic.name", 0x00ff00);
    public static final byte Giant = initHostileChampionType(4, "entity.challenge.hostile_champions.giant.name", 0x0088ff);
    public static final byte Blessed = initHostileChampionType(5, "entity.challenge.hostile_champions.blessed.name", 0xffff00);
    public static final byte Growing = initHostileChampionType(6, "entity.challenge.hostile_champions.growing.name", 0xff0000);


    public static boolean hasAnyHostileChampionType(int hostileChampionType) {
        return hostileChampionType != Unset && hostileChampionType != Vanilla;
    }

    public static byte getRandomChampionType(Random random) {
        return (byte) (random.nextInt(6) + 1);
    }

    public static byte getRandomChampionTypeWithoutSpawnCheck(Random random) {
        int n = random.nextInt(5) + 1;
        return (byte) (n >= Giant ? n + 1 : n);
    }

    public static String getPrefix(byte hostileChampionType) { // mixin to entity name
        if (hostileChampionType < 1) return "";
        String pre = StatCollector.translateToLocal("entity.prefix.pre");
        pre = Objects.equals(pre, "") ? "" : pre;
        String post = StatCollector.translateToLocal("entity.prefix.post");
        post = Objects.equals(post, "") ? "" : post;
        return pre + StatCollector.translateToLocal(langKeyMap.get(hostileChampionType)) + post;
    }

}
