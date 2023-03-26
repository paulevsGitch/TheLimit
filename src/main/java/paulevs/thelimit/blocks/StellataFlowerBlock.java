package paulevs.thelimit.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.level.BlockView;
import net.minecraft.level.Level;
import net.minecraft.util.maths.Box;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;

import java.util.ArrayList;

public class StellataFlowerBlock extends TemplateBlockBase {
	public StellataFlowerBlock(Identifier identifier) {
		super(identifier, Material.WOOD);
		setLightEmittance(1F);
		setHardness(0.5F);
	}
	
	@Override
	public boolean isFullOpaque() {
		return false;
	}
	
	@Override
	public boolean isFullCube() {
		return false;
	}
	
	@Override
	public void updateBoundingBox(BlockView arg, int i, int j, int k) {
		this.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
	}
	
	public void doesBoxCollide(Level level, int x, int y, int z, Box box, ArrayList list) {
		this.setBoundingBox(0.25F, 0.0F, 0.25F, 0.75F, 0.375F, 0.75F);
		super.doesBoxCollide(level, x, y, z, box, list);
		this.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
	}
}
