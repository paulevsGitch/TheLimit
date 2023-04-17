package paulevs.thelimit.items;

import net.minecraft.item.tool.ToolMaterial;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.tool.TemplateHoe;
import paulevs.thelimit.rendering.SimpleTexturedItem;

public class TLHoe extends TemplateHoe implements SimpleTexturedItem {
	public TLHoe(Identifier identifier, ToolMaterial material) {
		super(identifier, material);
	}
}
