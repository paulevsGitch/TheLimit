package paulevs.thelimit.blocks.basic;

import net.minecraft.level.Level;
import net.minecraft.level.structure.Structure;
import net.modificationstation.stationapi.api.registry.Identifier;

import java.util.function.Supplier;

public class SaplingBlock extends PlantBlock {
	private final Supplier<Structure> structure;
	
	public SaplingBlock(Identifier identifier, Supplier<Structure> structure) {
		super(identifier);
		this.structure = structure;
	}
	
	@Override
	public void onBlockPlaced(Level level, int x, int y, int z) {
		structure.get().generate(level, level.rand, x, y, z);
	}
	
	@Override
	public boolean canPlaceAt(Level level, int x, int y, int z) {
		return true;
	}
}
