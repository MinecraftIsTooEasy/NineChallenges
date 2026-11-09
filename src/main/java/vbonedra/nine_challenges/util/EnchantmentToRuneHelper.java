package vbonedra.nine_challenges.util;

import net.minecraft.EnchantmentNameParts;
import java.lang.reflect.Field;

public final class EnchantmentToRuneHelper {
    public static final String TAG = "\uF8FF\uE5A1\uF8FF";
    private static String[] cachedWordList;

    static {
        try {
            Field field = EnchantmentNameParts.class.getDeclaredField("wordList");
            field.setAccessible(true);
            cachedWordList = (String[]) field.get(EnchantmentNameParts.instance);
        } catch (Exception e) {
            cachedWordList = "the elder scrolls klaatu berata niktu xyzzy bless curse light darkness fire air earth water hot dry cold wet ignite snuff embiggen twist shorten stretch fiddle destroy imbue galvanize enchant free limited range of towards inside sphere cube self other ball mental physical grow shrink demon elemental spirit animal creature beast humanoid undead fresh stale ".split(" ");
        }
    }

    public static String enchantmentToRune(String key) {
        if (key == null) {
            return TAG + cachedWordList[0];
        }

        long hash = key.hashCode();
        hash = hash * 31 + 11;

        int wordCount = (int) (Math.abs(hash) % 2) + 3;
        hash = hash * 31 + 13;

        StringBuilder builder = new StringBuilder(TAG);
        for (int i = 0; i < wordCount; i++) {
            if (i > 0) {
                builder.append(" ");
            }
            int index = (int) (Math.abs(hash) % cachedWordList.length);
            builder.append(cachedWordList[index]);
            hash = hash * 31 + 17;
        }

        return builder.toString();
    }

    public static String enchantmentToRuneDeterministic(String key, int wordCount) {
        if (key == null) {
            return TAG + cachedWordList[0];
        }

        long hash = key.hashCode();
        hash = hash * 31 + 22;

        StringBuilder builder = new StringBuilder(TAG);
        for (int i = 0; i < wordCount; i++) {
            if (i > 0) {
                builder.append(" ");
            }
            int index = (int) (Math.abs(hash) % cachedWordList.length);
            builder.append(cachedWordList[index]);
            hash = hash * 31 + 17;
        }

        return builder.toString();
    }
}
