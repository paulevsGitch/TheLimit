package paulevs.thelimit.blocks;

import net.modificationstation.stationapi.api.state.property.BooleanProperty;
import net.modificationstation.stationapi.api.state.property.EnumProperty;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.util.math.Direction.Axis;

public class TLBlockProperties {
	public static final EnumProperty<Axis> AXIS = EnumProperty.of("axis", Axis.class);
	public static final BooleanProperty[] FACES = new BooleanProperty[6];
	
	public static BooleanProperty getFaceProp(Direction dir) {
		return FACES[dir.getId()];
	}
	
	static {
		for (int i = 0; i < 6; i++) {
			FACES[i] = BooleanProperty.of(Direction.byId(i).getName());
		}
	}
}
