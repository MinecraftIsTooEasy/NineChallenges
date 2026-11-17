package vbonedra.nine_challenges.manylib;

import fi.dy.masa.malilib.config.ConfigTab;
import fi.dy.masa.malilib.config.SimpleConfigs;
import fi.dy.masa.malilib.config.options.*;
import net.minecraft.*;

import java.util.ArrayList;
import java.util.List;

public class NineChallengesConfigs extends SimpleConfigs {
    public static final ConfigBoolean EnableChallengeBarrenLandsDeadTint = new ConfigBoolean("Barren Lands Dead Tint", true, "Makes foliage look darker and desaturated. Reload render after change, by defaults \"R\" key.");

    public static final ConfigDouble ValueChallengeHostileChampionsOverlayScale = new ConfigDouble("Champion Overlay Scale", 1.0F, 1F, 2F, "How far champion overlay would go from entity model. ");
    public static final ConfigDouble ValueChallengeHostileChampionsOverlaySpeed = new ConfigDouble("Champion Overlay Speed", 0.0044F, 0F, 2F, "How fast champion overlay would spin.");
    public static final ConfigDouble ValueChallengeHostileChampionsOverlayBrightness = new ConfigDouble("Champion Overlay Brightness", 0.5F, 0F, 1F, "How bright champion overlay would be.");







    private static final NineChallengesConfigs Instance;
    public static final List<ConfigBase<?>> CosmeticOptions;
    public static final List<ConfigBase<?>> BlocksListOptions = new ArrayList<>();
    public static final List<ConfigBase<?>> Total = new ArrayList<>();
    public static final List<ConfigTab> tabs = new ArrayList<>();

    public NineChallengesConfigs(String name, List<ConfigHotkey> hotkeys, List<ConfigBase<?>> values) {
        super(name, hotkeys, values);
    }

    @Override
    public List<ConfigTab> getConfigTabs() { return tabs; }
    public static NineChallengesConfigs getInstance() { return Instance; }

    static {
        CosmeticOptions = List.of(
                EnableChallengeBarrenLandsDeadTint,

                ValueChallengeHostileChampionsOverlayScale,
                ValueChallengeHostileChampionsOverlaySpeed,
                ValueChallengeHostileChampionsOverlayBrightness
        );

        Total.addAll(CosmeticOptions);
        Total.addAll(BlocksListOptions);

        tabs.add(new ConfigTab("General", CosmeticOptions));

        Instance = new NineChallengesConfigs("NineChallenges", null, Total);
    }
}
