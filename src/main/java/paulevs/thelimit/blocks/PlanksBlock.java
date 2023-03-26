package paulevs.thelimit.blocks;

import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;

public class PlanksBlock extends TemplateBlockBase {
	public PlanksBlock(Identifier identifier) {
		super(identifier, Material.WOOD);
		setSounds(WOOD_SOUNDS);
		setHardness(1F);
	}
}
