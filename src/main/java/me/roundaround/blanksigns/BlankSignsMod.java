package me.roundaround.blanksigns;

import me.roundaround.blanksigns.config.BlankSignsConfig;
import me.roundaround.blanksigns.network.Networking;
import me.roundaround.blanksigns.server.PlayerPreferenceTracker;
import me.roundaround.blanksigns.server.command.BlankSignsCommand;
import me.roundaround.blanksigns.server.network.ServerNetworking;
import me.roundaround.roundalib.gradle.api.annotation.Entrypoint;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

@Entrypoint(Entrypoint.MAIN)
public class BlankSignsMod implements ModInitializer {
  @Override
  public void onInitialize() {
    Networking.registerC2SPayloads();
    ServerNetworking.registerReceivers();
    BlankSignsConfig.getInstance().init();

    ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
      PlayerPreferenceTracker.getInstance().remove(handler.getPlayer());
    });

    CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
      BlankSignsCommand.register(dispatcher);
    });
  }
}
