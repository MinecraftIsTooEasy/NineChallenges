package vbonedra.nine_challenges.mixin.network;

import net.minecraft.INetworkManager;
import net.minecraft.ServerConfigurationManager;
import net.minecraft.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vbonedra.nine_challenges.challenge.Challenges;
import vbonedra.nine_challenges.network.NineChallengesNetwork;

@Mixin(ServerConfigurationManager.class)
public abstract class ServerConfigurationManagerMixin {

    @Inject(method = "initializeConnectionToPlayer", at = @At("TAIL"))
    private void initializeConnectionToPlayer_SendMaskFromServerToPlayer(INetworkManager par1INetworkManager, ServerPlayer par2EntityPlayerMP, CallbackInfo ci) {
        int mask = Challenges.getServerMask();
        vbonedra.nine_challenges.NineChallengesMod.NINE_CHALLENGES_LOGGER.info(
                "login mask={} dimWorldInfo={}", mask,
                par2EntityPlayerMP.worldObj.getWorldInfo().getClass().getSimpleName());
//        int mask = Challenges.getServerMask();
        NineChallengesNetwork.sendChallengeMask(par2EntityPlayerMP, mask);
    }

}