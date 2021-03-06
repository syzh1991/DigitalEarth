/*
 * Copyright (C) 2011 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */
package gov.nasa.worldwind.formats.geojson;

import gov.nasa.worldwind.avlist.AVList;

/**
 * @author dcollins
 * @version $Id: GeoJSONGeometryCollection.java 1 2011-07-16 23:22:47Z dcollins $
 */
public class GeoJSONGeometryCollection extends GeoJSONGeometry
{
    public GeoJSONGeometryCollection(AVList fields)
    {
        super(fields);
    }

    @Override
    public boolean isGeometryCollection()
    {
        return true;
    }

    public GeoJSONGeometry[] getGeometries()
    {
        return (GeoJSONGeometry[]) this.getValue(GeoJSONConstants.FIELD_GEOMETRIES);
    }
}
