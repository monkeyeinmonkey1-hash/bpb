/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_304
 *  net.minecraft.class_310
 *  org.lwjgl.glfw.GLFW
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package betterplacebind.mixin;

import betterplacebind.mixin.KeyBindingAccessor;
import net.minecraft.class_304;
import net.minecraft.class_310;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_310.class})
public class RightClickMixin {
    private int rightClickTicks = 0;
    private static final int INITIAL_DELAY = 5;

    @Inject(method={"method_1508"}, at={@At(value="HEAD")})
    private void injectKeyboardRepeat(CallbackInfo ci) {
        boolean rightHeld;
        class_310 client = class_310.method_1551();
        if (client.field_1724 == null) {
            return;
        }
        class_304 useKey = client.field_1690.field_1904;
        if (!useKey.method_1428().equals("key.mouse.right")) {
            return;
        }
        long handle = client.method_22683().method_4490();
        boolean bl = rightHeld = GLFW.glfwGetMouseButton((long)handle, (int)1) == 1;
        if (rightHeld) {
            useKey.method_23481(true);
            ++this.rightClickTicks;
            if (this.rightClickTicks >= 5) {
                KeyBindingAccessor accessor = (KeyBindingAccessor)useKey;
                accessor.setTimesPressed(accessor.getTimesPressed() + 1);
            }
        } else {
            useKey.method_23481(false);
            this.rightClickTicks = 0;
        }
    }
}

