import me.roundaround.gradle.extension.library.LibModule

plugins {
  id("roundalib-gradle") version "1.0.0"
}

roundalib {
  library {
    local = true
    version = "3.2.0"
    modules = setOf(LibModule.CONFIG_GUI)
  }
}
