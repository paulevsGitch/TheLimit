package paulevs.thelimit.items;

import net.minecraft.item.ItemBase;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;
import paulevs.thelimit.TheLimit;

import java.util.ArrayList;
import java.util.List;

public class TLItems {
	public static final List<ItemBase> ITEMS = new ArrayList<>();
	
	public static final ItemBase BISMUTH = make("bismuth");
	
	public static void init() {}
	
	private static ItemBase make(String name) {
		Identifier id = TheLimit.id(name);
		ItemBase item = new TemplateItemBase(id);
		item.setTranslationKey(id.toString());
		ITEMS.add(item);
		return item;
	}
}
