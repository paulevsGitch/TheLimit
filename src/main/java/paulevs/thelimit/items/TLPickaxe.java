package paulevs.thelimit.items;

import net.minecraft.item.tool.ToolMaterial;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.tool.TemplatePickaxe;
import paulevs.thelimit.rendering.SimpleTexturedItem;

public class TLPickaxe extends TemplatePickaxe implements SimpleTexturedItem {
	public TLPickaxe(Identifier identifier, ToolMaterial material) {
		super(identifier, material);
	}
}
