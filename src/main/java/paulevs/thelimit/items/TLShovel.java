package paulevs.thelimit.items;

import net.minecraft.item.tool.ToolMaterial;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.tool.TemplateShovel;
import paulevs.thelimit.rendering.SimpleTexturedItem;

public class TLShovel extends TemplateShovel implements SimpleTexturedItem {
	public TLShovel(Identifier identifier, ToolMaterial material) {
		super(identifier, material);
	}
}
