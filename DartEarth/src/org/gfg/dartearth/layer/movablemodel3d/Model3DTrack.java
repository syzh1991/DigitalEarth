package org.gfg.dartearth.layer.movablemodel3d;

import gov.nasa.worldwind.geom.Position;

import java.util.Date;

public interface Model3DTrack {
	
	public Position getPosition(Date time);
	
	public Position getCurrentPosition();
}
