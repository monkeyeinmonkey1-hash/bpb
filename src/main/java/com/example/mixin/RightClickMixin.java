package com.example.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class RightClickMixin {
    private int rightClickTicks = 0;
    private static final int INITIAL_DELAY = 5;

    @Inject(method = "handleKeybinds", at = @At("HEAD"))
    private void injectKeyboardRepeat(CallbackInfo ci) {
        Minecraft client = Minecraft.getInstance();

        if (client.player == null) {
            return;
        }

        KeyMapping useKey = client.options.keyUse;

        long handle = client.getWindow().handle();

        boolean rightHeld = GLFW.glfwGetMouseButton(
                handle,
                GLFW.GLFW_MOUSE_BUTTON_RIGHT
        ) == GLFW.GLFW_PRESS;

        if (rightHeld) {
            useKey.setDown(true);
            ++this.rightClickTicks;

            if (this.rightClickTicks >= INITIAL_DELAY) {
                KeyBindingAccessor accessor = (KeyBindingAccessor) useKey;
                accessor.setClicks(accessor.getClicks() + 1);
            }
        } else {
            useKey.setDown(false);
            this.rightClickTicks = 0;
        }
    }
}
