/*
 * Copyright (C) 2001, 2010 United States Government
 * as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package org.gfg.dartearth.feature.KML;

import gov.nasa.worldwind.Exportable;
import gov.nasa.worldwind.ogc.kml.KMLConstants;
import gov.nasa.worldwind.ogc.kml.gx.GXConstants;

import javax.xml.stream.*;
import java.io.*;
import java.util.List;

/**
 * Utility class to create KML documents.
 *
 * @author pabercrombie
 * @version $Id: KMLDocumentBuilder.java 1 2011-07-16 23:22:47Z dcollins $
 */
public class KMLDocumentBuilder
{
    protected XMLStreamWriter writer;

    /**
     * Create a KML document using a Writer.
     *
     * @param writer Writer to receive KML output.
     *
     * @throws XMLStreamException If an error is encountered while writing KML.
     */
    public KMLDocumentBuilder(Writer writer) throws XMLStreamException
    {
        this.writer = XMLOutputFactory.newInstance().createXMLStreamWriter(writer);
        this.startDocument();
    }

    /**
     * Create a KML document using an OutputStream.
     *
     * @param stream Stream to receive KML output.
     *
     * @throws XMLStreamException If an error is encountered while writing KML.
     */
    public KMLDocumentBuilder(OutputStream stream) throws XMLStreamException
    {
        this.writer = XMLOutputFactory.newInstance().createXMLStreamWriter(stream);
        this.startDocument();
    }

    /**
     * Start the KML document and write namespace declarations.
     *
     * @throws XMLStreamException If an error is encountered while writing KML.
     */
    protected void startDocument() throws XMLStreamException
    {
        this.writer.writeStartDocument();
        this.writer.writeStartElement("kml");
        this.writer.writeDefaultNamespace(KMLConstants.KML_NAMESPACE);
        this.writer.setPrefix("gx", GXConstants.GX_NAMESPACE);
        this.writer.writeNamespace("gx", GXConstants.GX_NAMESPACE);
        this.writer.writeStartElement("Document");
    }

    /**
     * End the KML document.
     *
     * @throws XMLStreamException If an error is encountered while writing KML.
     */
    protected void endDocument() throws XMLStreamException
    {
        this.writer.writeEndElement(); // Document
        this.writer.writeEndElement(); // kml
        this.writer.writeEndDocument();

        this.writer.close();
    }

    /**
     * Close the document builder.
     *
     * @throws XMLStreamException If an error is encountered while writing KML.
     */
    public void close() throws XMLStreamException
    {
        this.endDocument();
        this.writer.close();
    }

    /**
     * Write an {@link Exportable} object to the document. If the object does not support export in KML format, it will
     * be ignored.
     *
     * @param exportable Object to export in KML.
     *
     * @throws IOException If an error is encountered while writing KML.
     */
    public void writeObject(Exportable exportable) throws IOException
    {
        String supported = exportable.isExportFormatSupported(KMLConstants.KML_MIME_TYPE);
        if (Exportable.FORMAT_SUPPORTED.equals(supported) || Exportable.FORMAT_PARTIALLY_SUPPORTED.equals(supported))
        {
            exportable.export(KMLConstants.KML_MIME_TYPE, this.writer);
        }
    }

    /**
     * Write a list of {@link Exportable} objects to the document. If any objects do not support export in KML format,
     * they will be ignored.
     *
     * @param list List of objects to export in KML.
     *
     * @throws IOException If an error is encountered while writing KML.
     */
    public void writeObjects(List<Exportable> list) throws IOException
    {
        for (Exportable exportable : list)
        {
            exportable.export(KMLConstants.KML_MIME_TYPE, this.writer);
        }
    }
}
