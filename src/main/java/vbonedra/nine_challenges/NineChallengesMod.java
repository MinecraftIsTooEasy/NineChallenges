package vbonedra.nine_challenges;

import vbonedra.nine_challenges.event.ExampleEvent;
import net.fabricmc.api.ModInitializer;

import net.xiaoyu233.fml.ModResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NineChallengesMod implements ModInitializer {
    public static final String NINE_CHALLENGES_MOD_ID = "nine_challenges";

    public static final Logger LOGGER = LogManager.getLogger(NINE_CHALLENGES_MOD_ID);

    @Override
    public void onInitialize() {

        LOGGER.info("Hello MITE Dev world!");

        ModResourceManager.addResourcePackDomain(NINE_CHALLENGES_MOD_ID);

        ExampleEvent.register();
    }
}
