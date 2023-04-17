package paulevs.thelimit.items;

import net.minecraft.item.ItemBase;
import net.minecraft.item.tool.ToolMaterial;
import net.modificationstation.stationapi.api.registry.Identifier;
import paulevs.thelimit.TheLimit;

import java.util.ArrayList;
import java.util.List;

public class TLItems {
	public static final List<ItemBase> ITEMS = new ArrayList<>();
	
	public static final ItemBase BISMUTH = make("bismuth");
	public static final ItemBase BISMUTH_PICKAXE = makeTool("bismuth_pickaxe", TLToolMaterials.BISMUTH);
	public static final ItemBase BISMUTH_SHOVEL = makeTool("bismuth_shovel", TLToolMaterials.BISMUTH);
	public static final ItemBase BISMUTH_AXE = makeTool("bismuth_axe", TLToolMaterials.BISMUTH);
	public static final ItemBase BISMUTH_HOE = makeTool("bismuth_hoe", TLToolMaterials.BISMUTH);
	public static final ItemBase BISMUTH_SWORD = makeTool("bismuth_sword", TLToolMaterials.BISMUTH);
	
	public static void init() {}
	
	private static ItemBase make(String name) {
		Identifier id = TheLimit.id(name);
		ItemBase item = new TLItem(id);
		item.setTranslationKey(id.toString());
		ITEMS.add(item);
		return item;
	}
	
	private static ItemBase makeTool(String name, ToolMaterial material) {
		ItemBase item = null;
		Identifier id = TheLimit.id(name);
		String type = name.substring(name.lastIndexOf('_') + 1);
		switch (type) {
			case "pickaxe" -> item = new TLPickaxe(id, material);
			case "shovel" -> item = new TLShovel(id, material);
			case "axe" -> item = new TLAxe(id, material);
			case "hoe" -> item = new TLHoe(id, material);
			case "sword" -> item = new TLSword(id, material);
		}
		if (item == null) return null;
		item.setTranslationKey(id.toString());
		ITEMS.add(item);
		return item;
	}
}
