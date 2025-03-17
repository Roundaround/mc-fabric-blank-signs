package me.roundaround.blanksigns.server;

import net.minecraft.entity.player.PlayerEntity;

import java.util.HashSet;

public final class PlayerPreferenceTracker {
  private static PlayerPreferenceTracker instance;

  private final HashSet<String> forcedOff = new HashSet<>();

  public void track(PlayerEntity player, boolean preference) {
    String id = player.getUuidAsString();
    if (preference) {
      this.forcedOff.remove(id);
    } else {
      this.forcedOff.add(id);
    }
  }

  public void remove(PlayerEntity player) {
    this.forcedOff.remove(player.getUuidAsString());
  }

  public boolean disabledForPlayer(PlayerEntity player) {
    return this.forcedOff.contains(player.getUuidAsString());
  }

  public static PlayerPreferenceTracker getInstance() {
    if (instance == null) {
      instance = new PlayerPreferenceTracker();
    }
    return instance;
  }

  private PlayerPreferenceTracker() {
  }
}
