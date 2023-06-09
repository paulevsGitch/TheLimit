package paulevs.thelimit.world.structures;

import net.modificationstation.stationapi.api.util.math.BlockPos;
import net.modificationstation.stationapi.api.util.math.MathHelper;
import paulevs.thelimit.blocks.TLBlocks;
import paulevs.thelimit.noise.PerlinNoise;
import paulevs.thelimit.world.structures.placers.HeightmapPlacer;
import paulevs.thelimit.world.structures.placers.StructurePlacer;
import paulevs.thelimit.world.structures.placers.VoidPlacer;
import paulevs.thelimit.world.structures.scatters.FeatureScatter;
import paulevs.thelimit.world.structures.scatters.GrassScatter;
import paulevs.thelimit.world.structures.scatters.MossScatter;
import paulevs.thelimit.world.structures.scatters.SimpleScatter;
import paulevs.thelimit.world.structures.trees.CalabellumStructure;
import paulevs.thelimit.world.structures.trees.StellataTreeSmallStructure;
import paulevs.thelimit.world.structures.trees.StellataTreeStructure;

import java.util.Random;

public class TheLimitStructures {
	public static final StellataTreeStructure STELLATA_TREE = new StellataTreeStructure();
	public static final StellataTreeSmallStructure STELLATA_TREE_SMALL = new StellataTreeSmallStructure();
	public static final CalabellumStructure CALABELLUM = new CalabellumStructure();
	public static final FeatureScatter CALABELLUM_GROUP = new FeatureScatter(
		5, 30, CALABELLUM,
		state -> state.isOf(TLBlocks.VOID_FLUID),
		state -> state.getBlock().isFullCube()
	);
	
	public static final GrassScatter GUTTARBA = new GrassScatter(
		4, 30,
		TLBlocks.GUTTARBA_SHORT,
		TLBlocks.GUTTARBA_NORMAL,
		TLBlocks.GUTTARBA_TALL
	);
	
	public static final SimpleScatter GLOW_PLANT = new SimpleScatter(3, 20, TLBlocks.GLOW_PLANT);
	
	public static final SimpleScatter MOSS = new MossScatter(2, 2, TLBlocks.MOSS);
	
	public static final SmallIsland SMALL_ISLAND = new SmallIsland();
	
	public static final StructurePlacer STELLATA_TREE_PLACER = new HeightmapPlacer(STELLATA_TREE, 3);
	public static final StructurePlacer STELLATA_TREE_SMALL_PLACER = new HeightmapPlacer(STELLATA_TREE_SMALL, 1);
	public static final StructurePlacer CALABELLUM_GROUP_PLACER = new HeightmapPlacer(CALABELLUM_GROUP, 1);
	
	public static final StructurePlacer GUTTARBA_PLACER = new HeightmapPlacer(GUTTARBA, 3);
	public static final StructurePlacer GLOW_PLANT_PLACER = new HeightmapPlacer(GLOW_PLANT, 1);
	public static final StructurePlacer MOSS_PLACER = new HeightmapPlacer(MOSS, 10);
	
	public static final StructurePlacer SMALL_ISLAND_PLACER = new VoidPlacer(SMALL_ISLAND, 1, 128 - 40, 80);
	
	public static void updateDensity(long seed) {
		Random random = new Random(seed);
		
		PerlinNoise noise1 = new PerlinNoise(random.nextInt());
		PerlinNoise noise2 = new PerlinNoise(random.nextInt());
		SMALL_ISLAND_PLACER.setDensityFunction(
			pos -> (hash(pos) & 7) == 0 && noise1.get(pos, 0.20) > 0.5 && noise2.get(pos.getX(), 0.1) > 0.5
		);
	}
	
	private static long hash(BlockPos pos) {
		return MathHelper.hashCode(pos.getX(), pos.getY(), pos.getZ());
	}
}
