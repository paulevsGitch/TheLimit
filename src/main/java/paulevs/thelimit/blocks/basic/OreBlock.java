package paulevs.thelimit.blocks.basic;

import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.Identifier;

import java.util.List;
import java.util.function.Supplier;

public class OreBlock extends StoneBlock {
	private final Supplier<ItemBase> drop;
	private final int minCount;
	private final int delta;
	
	public OreBlock(Identifier identifier, Supplier<ItemBase> drop, int minCount, int maxCount) {
		super(identifier);
		this.drop = drop;
		this.minCount = minCount;
		this.delta = maxCount - minCount + 1;
	}
	
	@Override
	public List<ItemInstance> getDropList(Level level, int x, int y, int z, BlockState state, int meta) {
		int count = level.rand.nextInt(delta) + minCount;
		ItemBase drop = this.drop.get();
		return List.of(new ItemInstance(drop, count));
	}
}
