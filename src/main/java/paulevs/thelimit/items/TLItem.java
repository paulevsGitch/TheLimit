package paulevs.thelimit.items;

import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;
import paulevs.thelimit.rendering.SimpleTexturedItem;

public class TLItem extends TemplateItemBase implements SimpleTexturedItem {
	public TLItem(Identifier identifier) {
		super(identifier);
	}
}
