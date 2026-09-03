/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_304
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 */
package betterplacebind.mixin;

import net.minecraft.class_304;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={class_304.class})
public interface KeyBindingAccessor {
    @Accessor(value="field_1661")
    public int getTimesPressed();

    @Accessor(value="field_1661")
    public void setTimesPressed(int var1);
}

