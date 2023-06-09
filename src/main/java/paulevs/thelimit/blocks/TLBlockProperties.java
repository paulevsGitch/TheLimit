package paulevs.thelimit.blocks;

import net.modificationstation.stationapi.api.state.property.BooleanProperty;
import net.modificationstation.stationapi.api.state.property.EnumProperty;
import net.modificationstation.stationapi.api.util.StringIdentifiable;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.util.math.Direction.Axis;

public class TLBlockProperties {
	public static final EnumProperty<TriplePart> TRIPLE_PART = EnumProperty.of("part", TriplePart.class);
	public static final EnumProperty<TopBottom> TOP_BOTTOM = EnumProperty.of("part", TopBottom.class);
	public static final EnumProperty<PillarShape> PILLAR = EnumProperty.of("shape", PillarShape.class);
	public static final EnumProperty<SlabShape> SLAB = EnumProperty.of("shape", SlabShape.class);
	public static final EnumProperty<Axis> AXIS = EnumProperty.of("axis", Axis.class);
	public static final BooleanProperty[] FACES = new BooleanProperty[6];
	public static final BooleanProperty VOIDLOGGED = BooleanProperty.of("voidlogged");
	public static final BooleanProperty BOTTOM = BooleanProperty.of("bottom");
	
	public static BooleanProperty getFaceProp(Direction dir) {
		return FACES[dir.getId()];
	}
	
	public enum TopBottom implements StringIdentifiable {
		TOP("top"), BOTTOM("bottom");
		
		private final String name;
		
		TopBottom(String name) {
			this.name = name;
		}
		
		@Override
		public String asString() {
			return name;
		}
	}
	
	public enum TriplePart implements StringIdentifiable {
		BOTTOM("bottom"), MIDDLE("middle"), TOP("top");
		
		private final String name;
		
		TriplePart(String name) {
			this.name = name;
		}
		
		@Override
		public String asString() {
			return name;
		}
	}
	
	public enum SlabShape implements StringIdentifiable {
		BOTTOM("bottom"),
		TOP("top"),
		EAST("east"),
		WEST("west"),
		NORTH("north"),
		SOUTH("south"),
		FULL("full");
		
		private static final SlabShape[] VALUES = values();
		private final String name;
		
		SlabShape(String name) {
			this.name = name;
		}
		
		@Override
		public String asString() {
			return name;
		}
		
		public static SlabShape fromDirection(Direction dir) {
			return VALUES[dir.ordinal()];
		}
		
		public Direction getDirection() {
			if (this == FULL) return null;
			return Direction.byId(this.ordinal());
		}
	}
	
	public enum PillarShape implements StringIdentifiable {
		SMALL("small"),
		BOTTOM("bottom"),
		MIDDLE("middle"),
		TOP("top");
		
		private final String name;
		
		PillarShape(String name) {
			this.name = name;
		}
		
		@Override
		public String asString() {
			return name;
		}
	}
	
	static {
		for (int i = 0; i < 6; i++) {
			FACES[i] = BooleanProperty.of(Direction.byId(i).getName());
		}
	}
}
