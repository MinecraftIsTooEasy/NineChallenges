package vbonedra.nine_challenges.mixin.gui;

import net.minecraft.GuiButton;
import net.minecraft.GuiCreateWorld;
import net.minecraft.GuiScreen;
import net.minecraft.Minecraft;
import net.minecraft.WorldInfo;
import net.minecraft.WorldSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vbonedra.nine_challenges.challenge.ChallengeHolder;
import vbonedra.nine_challenges.challenge.Challenges;
import vbonedra.nine_challenges.gui.ChallengesGui;
import vbonedra.nine_challenges.gui.GrailButton;

@Mixin(GuiCreateWorld.class)
public abstract class GuiCreateWorldMixin extends GuiScreen implements ChallengeHolder {

    @Unique private static final int BUTTON_OPEN_CHALLENGE_GUI_ID = 6847; // TODO: find a better way of setting id, hardcoded value is too hostile
    @Unique private GuiButton buttonOpenChallengeGui;
    @Unique private int challengeMask;

    @Override public int nineChallenges$getChallengeMask() {
        return challengeMask;
    }
    @Override public void nineChallenges$setChallengeMask(int mask) {
        this.challengeMask = mask;
    }

    @Inject(at = @At("TAIL"), method = "initGui")
    private void initGui_AddButtonAndCheckIfItMustDraw(CallbackInfo ci) {
        this.buttonOpenChallengeGui = new GrailButton(BUTTON_OPEN_CHALLENGE_GUI_ID, this.width / 2 + 110, 60, this);
        this.buttonList.add(this.buttonOpenChallengeGui);
        this.buttonOpenChallengeGui.drawButton = !((GuiCreateWorldAccessor) this).getMoreOptions();
    }

    @Inject(at = @At("TAIL"), method = "func_82288_a")
    private void func_82288_a_UpdateButtonDraw(boolean moreOptions, CallbackInfo ci) {
        if (this.buttonOpenChallengeGui != null) {
            this.buttonOpenChallengeGui.drawButton = !moreOptions;
        }
    }

    @Inject(at = @At("HEAD"), method = "actionPerformed", cancellable = true)
    private void actionPerformed_OpenChallengeGui(GuiButton guiButton, CallbackInfo ci) {
        if (guiButton.id == BUTTON_OPEN_CHALLENGE_GUI_ID && guiButton.enabled) {
            this.mc.displayGuiScreen(new ChallengesGui(this));
            ci.cancel();
        }
    }

    // Stamp the chosen mask onto the WorldSettings right before the world launches
    @Redirect(
            method = "actionPerformed",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/Minecraft;launchIntegratedServer(Ljava/lang/String;Ljava/lang/String;Lnet/minecraft/WorldSettings;)V")
    )
    private void actionPerformed_in_launchIntegratedServer_SetChallengeMask(Minecraft mc, String folder, String name, WorldSettings worldSettings) {
        Challenges.holder(worldSettings).nineChallenges$setChallengeMask(this.challengeMask);
        mc.launchIntegratedServer(folder, name, worldSettings);
    }

    // "Recreate" keeps the old world's challenges
    @Inject(at = @At("TAIL"), method = "func_82286_a")
    private void func_82286_a_CopyMaskFromRecreatedWorld(WorldInfo worldInfo, CallbackInfo ci) {
        this.challengeMask = Challenges.holder(worldInfo).nineChallenges$getChallengeMask();
    }
}