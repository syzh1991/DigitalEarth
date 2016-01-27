/*
Copyright (C) 2001, 2006 United States Government as represented by
the Administrator of the National Aeronautics and Space Administration.
All Rights Reserved.
*/
package org.gfg.dartearth.feature.KML;

import gov.nasa.worldwind.*;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.render.*;
import gov.nasa.worldwind.util.Logging;

import javax.xml.transform.*;
import javax.xml.transform.stream.*;

import org.gfg.dartearth.core.DartEarthAppFrame;

import java.io.*;
import java.util.*;

/**
 * Shows how to generate KML from World Wind elements. This example creates several objects, and writes their KML
 * representation to stdout.
 *
 * @author pabercrombie
 * @version $Id: ExportKML.java 1 2011-07-16 23:22:47Z dcollins $
 */
public class ExportKML
{
    protected static ShapeAttributes normalShapeAttributes;
    protected static ShapeAttributes highlightShapeAttributes;
    private DartEarthAppFrame frame;
    public ExportKML(DartEarthAppFrame frame){
    	this.frame=frame;
    }
    
    public List<Exportable> getAllElems() throws IOException{
    	return frame.getExportElems();
    }

    

    /**
     * Generate sample PointPlacemarks, Paths, and Polygons, and write the KML representation to stdout.
     *
     * @param args Not used.
     */
    public void exportKml()
    {
        try
        {
            normalShapeAttributes = new BasicShapeAttributes();
            normalShapeAttributes.setInteriorMaterial(Material.BLUE);
            normalShapeAttributes.setOutlineMaterial(Material.BLACK);

            highlightShapeAttributes = new BasicShapeAttributes();
            highlightShapeAttributes.setInteriorMaterial(Material.RED);
            highlightShapeAttributes.setOutlineMaterial(Material.BLACK);

            // Create a StringWriter to collect KML in a string buffer
            Writer stringWriter = new StringWriter();

            // Create a document builder that will write KML to the StringWriter
            KMLDocumentBuilder kmlBuilder = new KMLDocumentBuilder(stringWriter);

            // Export the objects
            kmlBuilder.writeObjects(getAllElems());

            kmlBuilder.close();

            // Get the exported document as a string
            String xmlString = stringWriter.toString();

            // Set up a transformer to pretty-print the XML
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            // Write the pretty-printed document to stdout
            //transformer.transform(new StreamSource(new StringReader(xmlString)), new StreamResult(System.out));
            transformer.transform(new StreamSource(new StringReader(xmlString)), new StreamResult(new FileOutputStream("D:\\user.kml")));
        }
        catch (Exception e)
        {
            String message = Logging.getMessage("generic.ExceptionAttemptingToWriteXml", e.toString());
            Logging.logger().severe(message);
            e.printStackTrace();
        }
    }
}
