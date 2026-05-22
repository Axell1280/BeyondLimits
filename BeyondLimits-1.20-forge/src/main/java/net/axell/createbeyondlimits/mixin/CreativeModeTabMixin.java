package net.axell.createbeyondlimits.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.mcexpanded.fancytabsections.FancyTabSections;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mixin(CreativeModeTab.class)
public class CreativeModeTabMixin {
    @WrapMethod(method = "buildContents")
    private void buildContents(CreativeModeTab.ItemDisplayParameters parameters, Operation<Void> original) {
        CreativeModeTab self = (CreativeModeTab)(Object)this;
        ResourceLocation rl = BuiltInRegistries.CREATIVE_MODE_TAB.getKey(self);

        System.out.println("[CBL DEBUG] buildContents called for: " + rl);
        System.out.println("[CBL DEBUG] SECTIONS_MAP keys: " + FancyTabSections.SECTIONS_MAP.keySet());
        System.out.println("[CBL DEBUG] ITEMS_MAP keys: " + FancyTabSections.ITEMS_MAP.keySet());

        if (FancyTabSections.SECTIONS_MAP.containsKey(rl)) {
            List<ItemStack> display = FancyTabSections.ITEMS_MAP.get(rl);
            System.out.println("[CBL DEBUG] display is null? " + (display == null));
            if (display != null) {
                System.out.println("[CBL DEBUG] display size: " + display.size());
                ((CreativeModeTabAccessor)self).setDisplayItems(display);
                ((CreativeModeTabAccessor)self).setDisplayItemsSearchTab(
                        display.stream().filter(s -> !s.isEmpty()).collect(Collectors.toCollection(LinkedHashSet::new))
                );
                return;
            }
        }
        original.call(parameters);
    }
}