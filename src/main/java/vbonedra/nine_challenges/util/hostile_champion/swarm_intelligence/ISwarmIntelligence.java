package vbonedra.nine_challenges.util.hostile_champion.swarm_intelligence;

public interface ISwarmIntelligence {
    boolean NC$hasSwarmTarget();
    double NC$getSwarmX();
    double NC$getSwarmY();
    double NC$getSwarmZ();
    long NC$getSwarmSetTime();
    void NC$setSwarmTarget(double x, double y, double z);
    void NC$clearSwarmTarget();
}