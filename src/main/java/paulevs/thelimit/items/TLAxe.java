package paulevs.thelimit.items;

import net.minecraft.item.tool.ToolMaterial;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.tool.TemplateHatchet;
import paulevs.thelimit.rendering.SimpleTexturedItem;

public class TLAxe extends TemplateHatchet implements SimpleTexturedItem {
	public TLAxe(Identifier identifier, ToolMaterial material) {
		super(identifier, material);
	}
}
