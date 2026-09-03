package com.example.mixin;

import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(KeyMapping.class)
public interface KeyBindingAccessor {
    @Accessor("clickCount")
    int getClicks();

    @Accessor("clickCount")
    void setClicks(int clicks);
}
