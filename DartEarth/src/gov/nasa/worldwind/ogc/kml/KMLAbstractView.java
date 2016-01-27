/*
 * Copyright (C) 2011 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package gov.nasa.worldwind.ogc.kml;

/**
 * Represents the KML <i>AbstractView</i> element.
 *
 * @author tag
 * @version $Id: KMLAbstractView.java 1 2011-07-16 23:22:47Z dcollins $
 */
public abstract class KMLAbstractView extends KMLAbstractObject
{
    protected KMLAbstractView(String namespaceURI)
    {
        super(namespaceURI);
    }
}
