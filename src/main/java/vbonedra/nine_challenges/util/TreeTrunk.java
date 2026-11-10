package vbonedra.nine_challenges.util;

public final class TreeTrunk {
    public static final ThreadLocal<int[]> TRUNK = ThreadLocal.withInitial(() -> new int[3]);
}