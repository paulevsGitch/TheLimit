package paulevs.thelimit.world.dimension;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.level.dimension.Dimension;
import net.minecraft.level.dimension.DimensionData;
import net.minecraft.level.source.LevelSource;
import net.minecraft.util.maths.Vec3f;
import net.modificationstation.stationapi.impl.level.StationDimension;
import paulevs.thelimit.TheLimit;
import paulevs.thelimit.mixins.common.LevelAccessor;

public class TheLimitDimension extends Dimension implements StationDimension {
	private final Vec3f skyColor = Vec3f.method_1293(90F / 255F, 40F / 255F, 121F / 255F);
	
	public TheLimitDimension(int id) {
		this.id = id;
		this.halvesMapping = true; // No Skylight
	}
	
	@Override
	public void initBiomeSource() {
		long seed = this.level.getSeed();
		LevelAccessor accessor = (LevelAccessor) this.level;
		DimensionData data = accessor.thelimit_getDimensionData();
		this.biomeSource = new TheLimitBiomeSource(seed, data);
	}
	
	@Override
	public LevelSource createLevelSource() {
		return new TheLimitWorldgen(this.level);
	}
	
	@Override
	public boolean canSpawnOn(int x, int z) {
		int y = TheLimit.getHeight(level, x, z);
		return y > 0;
	}
	
	// That is fog color!
	@Environment(value= EnvType.CLIENT)
	public Vec3f getSkyColour(float f, float g) {
		return skyColor;
	}
	
	@Environment(value=EnvType.CLIENT)
	public boolean hasPaleSky() {
		return false;
	}
	
	@Override
	public float getSunPosition(long l, float f) {
		return 0.0f;
	}
	
	@Override
	public short getDefaultLevelHeight() {
		return 256;
	}
}
