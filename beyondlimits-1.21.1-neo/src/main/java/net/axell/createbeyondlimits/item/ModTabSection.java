package net.axell.createbeyondlimits.item;

import net.mcexpanded.fancytabsections.creativetab.ConglomerateOfItems;
import net.mcexpanded.fancytabsections.creativetab.Section;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ModTabSection implements Section {
    private final ResourceLocation id;
    private final Component title;
    private final int textColor;
    private final List<Item> items;

    public ModTabSection(ResourceLocation id, Component title, int textColor) {
        this.id = id;
        this.title = title;
        this.textColor = textColor;
        this.items = new ArrayList<>();
    }

    public ModTabSection add(Item item) {
        this.items.add(item);
        return this;
    }

    @Override
    public ResourceLocation id() { return this.id; }

    @Override
    public Component title() { return this.title; }

    @Override
    public int textColor() { return this.textColor; }

    @Override
    public ConglomerateOfItems items() { return (ConglomerateOfItems) this.items; }
}