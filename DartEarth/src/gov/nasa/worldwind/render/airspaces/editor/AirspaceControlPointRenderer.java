/*
 * Copyright (C) 2011 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */
package gov.nasa.worldwind.render.airspaces.editor;

import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.render.DrawContext;

import java.awt.*;

/**
 * @author dcollins
 * @version $Id: AirspaceControlPointRenderer.java 1 2011-07-16 23:22:47Z dcollins $
 */
public interface AirspaceControlPointRenderer
{
    void render(DrawContext dc, Iterable<? extends AirspaceControlPoint> controlPoints);

    void pick(DrawContext dc, Iterable<? extends AirspaceControlPoint> controlPoints, Point pickPoint, Layer layer);
}
