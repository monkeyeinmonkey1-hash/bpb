package com.example.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MinecraftClient.class)
public class RightClickMixin {
    private int rightClickTicks = 0;
    private static final int INITIAL_DELAY = 5;

    @Inject(method = "handleInputEvents", at = @At("HEAD"))
    private void injectKeyboardRepeat(CallbackInfo ci) {
        boolean rightHeld;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) {
            return;
        }
        KeyBinding useKey = client.options.useKey;
        if (!useKey.getBoundKeyTranslationKey().equals("key.mouse.right")) {
            return;
        }
        long handle = client.getWindow().getHandle();
        rightHeld = GLFW.glfwGetMouseButton(handle, GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS;
        
        if (rightHeld) {
            useKey.setPressed(true);
            ++this.rightClickTicks;
            if (this.rightClickTicks >= INITIAL_DELAY) {
                KeyBindingAccessor accessor = (KeyBindingAccessor) useKey;
                accessor.setTimesPressed(accessor.getTimesPressed() + 1);
            }
        } else {
            useKey.setPressed(false);
            this.rightClickTicks = 0;
        }
    }
}
