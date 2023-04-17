package paulevs.thelimit.items;

import net.minecraft.item.tool.ToolMaterial;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.tool.TemplateSword;
import paulevs.thelimit.rendering.SimpleTexturedItem;

public class TLSword extends TemplateSword implements SimpleTexturedItem {
	public TLSword(Identifier identifier, ToolMaterial material) {
		super(identifier, material);
	}
}
