package vbonedra.nine_challenges;

import fi.dy.masa.malilib.config.ConfigManager;
import net.fabricmc.api.ModInitializer;

import net.xiaoyu233.fml.ModResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vbonedra.nine_challenges.manylib.NineChallengesConfigs;
import vbonedra.nine_challenges.network.NineChallengesNetwork;

public class NineChallengesMod implements ModInitializer {
    public static final String NINE_CHALLENGES_MOD_ID = "nine_challenges";

    public static final Logger NINE_CHALLENGES_LOGGER = LogManager.getLogger(NINE_CHALLENGES_MOD_ID);

    @Override
    public void onInitialize() {
        ModResourceManager.addResourcePackDomain(NINE_CHALLENGES_MOD_ID);

        NineChallengesNetwork.init();

        NineChallengesConfigs.getInstance().load();
        ConfigManager.getInstance().registerConfig(NineChallengesConfigs.getInstance());

    }
}
