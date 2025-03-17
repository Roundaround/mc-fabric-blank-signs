package me.roundaround.blanksigns.client.network;

import me.roundaround.blanksigns.network.Networking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public final class ClientNetworking {
  public static void sendPreference(boolean preference) {
    if (ClientPlayNetworking.canSend(Networking.PREFERENCE_C2S)) {
      ClientPlayNetworking.send(new Networking.PreferenceC2S(preference));
    }
  }

  private ClientNetworking() {
  }
}
