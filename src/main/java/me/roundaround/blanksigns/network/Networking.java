package me.roundaround.blanksigns.network;

import me.roundaround.blanksigns.generated.Constants;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public final class Networking {
  public static final Identifier PREFERENCE_C2S = Identifier.of(Constants.MOD_ID, "preference_c2s");

  public static void registerC2SPayloads() {
    PayloadTypeRegistry.playC2S().register(PreferenceC2S.ID, PreferenceC2S.CODEC);
  }

  public record PreferenceC2S(boolean preference) implements CustomPayload {
    public static final CustomPayload.Id<PreferenceC2S> ID = new CustomPayload.Id<>(PREFERENCE_C2S);
    public static final PacketCodec<RegistryByteBuf, PreferenceC2S> CODEC = PacketCodec.tuple(
        PacketCodecs.BOOLEAN,
        PreferenceC2S::preference,
        PreferenceC2S::new
    );

    @Override
    public Id<PreferenceC2S> getId() {
      return ID;
    }
  }

  private Networking() {
  }
}
