package org.gfg.dartearth.layer;

import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.avlist.AVList;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.layers.BasicTiledImageLayer;

import java.io.IOException;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.gfg.dartearth.feature.KML.ExportableLayer;

public class BasicExportableTiledImageLayer extends BasicTiledImageLayer implements ExportableLayer {

	private AVList params = null;

	public BasicExportableTiledImageLayer(AVList params) {
		super(params);
		this.params = params;
	}

	@Override
	public Element exportAsDeml() throws IOException {
		org.dom4j.Document document = DocumentHelper.createDocument();
		Element wmsLayer = document.addElement("WmsLayer");

		addProperty(wmsLayer, "WmsUrl", AVKey.GET_CAPABILITIES_URL);
		addProperty(wmsLayer, "LayerName", AVKey.LAYER_NAMES);
		addProperty(wmsLayer, "TileWidth", AVKey.TILE_WIDTH);
		addProperty(wmsLayer, "TileHeight", AVKey.TILE_HEIGHT);
		addProperty(wmsLayer, "FormatSuffix", AVKey.FORMAT_SUFFIX);
		addProperty(wmsLayer, "NumEmptyLevels", AVKey.NUM_EMPTY_LEVELS);
		addProperty(wmsLayer, "NumLevels", AVKey.NUM_LEVELS);

		LatLon latLon = (LatLon) this.params.getValue(AVKey.LEVEL_ZERO_TILE_DELTA);

		Element levelZeroTileDeltaLat = wmsLayer.addElement("LevelZeroTileDeltaLat");
		levelZeroTileDeltaLat.setText(String.valueOf(latLon.getLatitude().getDegrees()));

		Element levelZeroTileDeltaLng = wmsLayer.addElement("LevelZeroTileDeltaLng");
		levelZeroTileDeltaLng.setText(String.valueOf(latLon.getLongitude().getDegrees()));

		Element isMercator = wmsLayer.addElement("IsMercator");
		isMercator.setText("false");

		return wmsLayer;

	}

	private void addProperty(Element wmsLayer, String exportProptrtyName, String avkeyPropertyName) {
		Element property = wmsLayer.addElement(exportProptrtyName);
		property.setText(this.params.getValue(avkeyPropertyName).toString());
	}

}
