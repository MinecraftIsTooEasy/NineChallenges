package vbonedra.nine_challenges.mixin.network;

import net.minecraft.INetworkManager;
import net.minecraft.ServerConfigurationManager;
import net.minecraft.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vbonedra.nine_challenges.challenge.Challenges;
import vbonedra.nine_challenges.network.ChallengeNetwork;

@Mixin(ServerConfigurationManager.class)
public abstract class ServerConfigurationManagerMixin {

    @Inject(method = "initializeConnectionToPlayer", at = @At("TAIL"))
    private void initializeConnectionToPlayer_SendMaskFromServerToPlayer(INetworkManager par1INetworkManager, ServerPlayer par2EntityPlayerMP, CallbackInfo ci) {
        int mask = Challenges.getMask(par2EntityPlayerMP.worldObj.getWorldInfo());
        ChallengeNetwork.sendTo(par2EntityPlayerMP, mask);
    }

}