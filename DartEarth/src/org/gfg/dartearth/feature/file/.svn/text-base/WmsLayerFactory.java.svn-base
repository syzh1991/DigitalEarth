package org.gfg.dartearth.feature.file;

import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.avlist.AVList;
import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Sector;
import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.layers.TiledImageLayer;
import gov.nasa.worldwind.util.LevelSet;
import gov.nasa.worldwind.util.Logging;
import gov.nasa.worldwind.util.Tile;
import gov.nasa.worldwind.util.TileUrlBuilder;
import gov.nasa.worldwind.util.WWIO;
import gov.nasa.worldwind.wms.BoundingBox;
import gov.nasa.worldwind.wms.Capabilities;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.gfg.dartearth.layer.BasicExportableTiledImageLayer;
import org.gfg.dartearth.layer.mercator.BasicMercatorTiledImageLayer;
import org.gfg.dartearth.layer.mercator.MercatorSector;
import org.gfg.dartearth.layer.mercator.MercatorTextureTile;
import org.w3c.dom.Element;

public class WmsLayerFactory {

	public static Layer newLayer(Capabilities caps, AVList params, boolean isMecator) {
		if (caps == null) {
			String message = Logging.getMessage("nullValue.WMSCapabilities");
			Logging.logger().severe(message);
			throw new IllegalArgumentException(message);
		}

		if (params == null) {
			String message = Logging.getMessage("nullValue.LayerConfigParams");
			Logging.logger().severe(message);
			throw new IllegalArgumentException(message);
		}

		String layerNames = params.getStringValue(AVKey.LAYER_NAMES);
		if (layerNames == null || layerNames.length() == 0) {
			String message = Logging.getMessage("nullValue.WMSLayerNames");
			Logging.logger().severe(message);
			throw new IllegalArgumentException(message);
		}

		String[] names = layerNames.split(",");
		if (names == null || names.length == 0) {
			String message = Logging.getMessage("nullValue.WMSLayerNames");
			Logging.logger().severe(message);
			throw new IllegalArgumentException(message);
		}

		for (String name : names) {
			if (caps.getLayerByName(name) == null) {
				String message = Logging.getMessage("WMS.LayerNameMissing", name);
				Logging.logger().severe(message);
				throw new IllegalArgumentException(message);
			}
		}

		params.setValue(AVKey.DATASET_NAME, layerNames);

		String mapRequestURIString = caps.getGetMapRequestGetURL();
		mapRequestURIString = fixGetMapString(mapRequestURIString);
		if (params.getValue(AVKey.SERVICE) == null)
			params.setValue(AVKey.SERVICE, mapRequestURIString);
		mapRequestURIString = params.getStringValue(AVKey.SERVICE);
		if (mapRequestURIString == null || mapRequestURIString.length() == 0) {
			Logging.logger().severe("WMS.RequestMapURLMissing");
			throw new IllegalArgumentException(Logging.getMessage("WMS.RequestMapURLMissing"));
		}

		String styleNames = params.getStringValue(AVKey.STYLE_NAMES);
		if (params.getValue(AVKey.DATA_CACHE_NAME) == null) {
			try {
				URI mapRequestURI = new URI(mapRequestURIString);
				String cacheName = WWIO.formPath(mapRequestURI.getAuthority(), mapRequestURI.getPath(), layerNames, styleNames);
				params.setValue(AVKey.DATA_CACHE_NAME, cacheName);
			} catch (URISyntaxException e) {
				String message = Logging.getMessage("WMS.RequestMapURLBad", mapRequestURIString);
				Logging.logger().severe(message);
				throw new IllegalArgumentException(message);
			}
		}

		// Determine image format to request.
		if (params.getStringValue(AVKey.IMAGE_FORMAT) == null) {
			String imageFormat = chooseImageFormat(caps);
			params.setValue(AVKey.IMAGE_FORMAT, imageFormat);
		}

		if (params.getStringValue(AVKey.IMAGE_FORMAT) == null) {
			Logging.logger().severe("WMS.NoImageFormats");
			throw new IllegalArgumentException(Logging.getMessage("WMS.NoImageFormats"));
		}

		if (params.getValue(AVKey.LEVEL_ZERO_TILE_DELTA) == null) {
			Angle delta = Angle.fromDegrees(36);
			params.setValue(AVKey.LEVEL_ZERO_TILE_DELTA, new LatLon(delta, delta));
		}

		if (params.getValue(AVKey.TILE_WIDTH) == null)
			params.setValue(AVKey.TILE_WIDTH, 512);
		if (params.getValue(AVKey.TILE_HEIGHT) == null)
			params.setValue(AVKey.TILE_HEIGHT, 512);
		if (params.getValue(AVKey.FORMAT_SUFFIX) == null)
			params.setValue(AVKey.FORMAT_SUFFIX, ".dds");
		if (params.getValue(AVKey.NUM_LEVELS) == null)
			params.setValue(AVKey.NUM_LEVELS, 5); // approximately 0.5 meters
													// per pixel
		if (params.getValue(AVKey.NUM_EMPTY_LEVELS) == null)
			params.setValue(AVKey.NUM_EMPTY_LEVELS, 0);

		params.setValue(AVKey.TILE_URL_BUILDER, new URLBuilder(caps, layerNames, styleNames, params));

		if (isMecator == false) {
			// Determine bounding sector.
			Sector sector = (Sector) params.getValue(AVKey.SECTOR);
			if (sector == null) {
				for (String name : names) {
					BoundingBox bb = caps.getLayerGeographicBoundingBox(caps.getLayerByName(name));
					if (bb == null) {
						Logging.logger().log(java.util.logging.Level.SEVERE, "WMS.NoGeographicBoundingBoxForLayer", name);
						continue;
					}

					sector = Sector.union(
							sector,
							Sector.fromDegrees(clamp(bb.getMiny(), -90d, 90d), clamp(bb.getMaxy(), -90d, 90d),
									clamp(bb.getMinx(), -180d, 180d), clamp(bb.getMaxx(), -180d, 180d)));
				}

				if (sector == null) {
					Logging.logger().severe("WMS.NoGeographicBoundingBox");
					throw new IllegalArgumentException(Logging.getMessage("WMS.NoGeographicBoundingBox"));
				}
				params.setValue(AVKey.SECTOR, sector);
			}
			TiledImageLayer layer = new BasicExportableTiledImageLayer(params);
			layer.setUseTransparentTextures(true);
			// layer.setShowImageTileOutlines(true);
			// layer.setDrawTileIDs(true);

			layer.setName(makeTitle(caps, layerNames, styleNames));

			return layer;
		} else {
			MercatorSector mSector = new MercatorSector(-1, 1, Angle.NEG180, Angle.POS180);
			params.setValue(AVKey.SECTOR, mSector);
			System.out.println("BasicMercatorTiledImageLayer");
			BasicMercatorTiledImageLayer layer = new BasicMercatorTiledImageLayer(params);
			layer.setUseTransparentTextures(true);
			// layer.setShowImageTileOutlines(true);
			// layer.setDrawTileIDs(true);

			layer.setName(makeTitle(caps, layerNames, styleNames));

			return layer;
		}
	}

	private static double clamp(double v, double min, double max) {
		return v < min ? min : v > max ? max : v;
	}

	private static String fixGetMapString(String gms) {
		gms = gms.trim();
		int qMarkIndex = gms.indexOf("?");
		if (qMarkIndex < 0)
			gms += "?";
		else if (qMarkIndex != gms.length() - 1)
			if (gms.lastIndexOf("&") != gms.length() - 1)
				gms += "&";

		return gms;
	}

	private static String makeTitle(Capabilities caps, String layerNames, String styleNames) {
		String[] lNames = layerNames.split(",");
		String[] sNames = styleNames != null ? styleNames.split(",") : null;

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < lNames.length; i++) {
			if (sb.length() > 0)
				sb.append(", ");

			String layerName = lNames[i];
			Element layer = caps.getLayerByName(layerName);
			String layerTitle = caps.getLayerTitle(layer);
			sb.append(layerTitle != null ? layerTitle : layerName);

			if (sNames == null || sNames.length <= i)
				continue;

			String styleName = sNames[i];
			Element style = caps.getLayerStyleByName(layer, styleName);
			if (style == null)
				continue;

			sb.append(" : ");
			String styleTitle = caps.getStyleTitle(layer, style);
			sb.append(styleTitle != null ? styleTitle : styleName);
		}

		return sb.toString();
	}

	private static final String[] formatOrderPreference = new String[] { "image/dds", "image/png", "image/jpeg" };

	private static String chooseImageFormat(Capabilities caps) {
		String[] formats = caps.getGetMapFormats();
		if (formats == null || formats.length == 0)
			return null;

		for (String s : formatOrderPreference) {
			for (String f : formats) {
				if (f.equalsIgnoreCase(s))
					return f;
			}
		}

		return formats[0]; // none recognized; just use the first in the caps
							// list
	}

	private static class URLBuilder implements TileUrlBuilder {
		private static final String MAX_VERSION = "1.3.0";

		private final String layerNames;
		private final String styleNames;
		private final String imageFormat;
		private final String wmsVersion;
		private final String crs;
		public String URLTemplate = null;

		private URLBuilder(Capabilities caps, String layerNames, String styleNames, AVList params) {
			this.layerNames = layerNames;
			this.styleNames = styleNames;
			this.imageFormat = params.getStringValue(AVKey.IMAGE_FORMAT);

			String version = caps.getVersion();
			if (version == null || version.compareTo(MAX_VERSION) >= 0) {
				this.wmsVersion = MAX_VERSION;
				this.crs = "&crs=CRS:84";
			} else {
				this.wmsVersion = version;
				this.crs = "&srs=EPSG:4326";
			}
		}

		public URL getURL(Tile tile, String altImageFormat) throws MalformedURLException {
			StringBuffer sb;
//			if (tile instanceof MercatorTextureTile) {
//				sb = new StringBuffer("http://192.168.0.45:8080/VirEarth/VirEarthServlet?");
//				MercatorSector s = ((MercatorTextureTile) tile).getMercatorSector();
//				int t = (int) (2 / (s.getMaxLatPercent() - s.getMinLatPercent()));
//				int z = (int) (Math.log(t) / Math.log(2));
//				double dx = 360 / t;
//				int x = (int) ((180 + s.getMinLongitude().getDegrees()) / dx);
//				int y = (int) ((s.getMinLatPercent() + 1) / 2 * t);
//				sb.append("X=" + y + "&");
//				sb.append("Y=" + x + "&");
//				sb.append("L=" + z + "&");
//				sb.append("transparent=TRUE");
//				sb.append("&bgcolor=0x000000");
//				System.out.println(sb.toString());
//			} else {
				if (this.URLTemplate == null) {
					sb = new StringBuffer(tile.getLevel().getService());
					sb.append("service=WMS");
					sb.append("&request=GetMap");
					sb.append("&version=");
					sb.append(this.wmsVersion);
					sb.append(this.crs);
					sb.append("&layers=");
					sb.append(this.layerNames);
					sb.append("&styles=");
					sb.append(this.styleNames != null ? this.styleNames : "default");
					sb.append("&width=");
					sb.append(tile.getLevel().getTileWidth());
					sb.append("&height=");
					sb.append(tile.getLevel().getTileHeight());
					sb.append("&format=");
					if (altImageFormat == null)
						sb.append(this.imageFormat);
					else
						sb.append(altImageFormat);
					sb.append("&transparent=TRUE");
					sb.append("&bgcolor=0x000000");

					this.URLTemplate = sb.toString();
				} else {
					sb = new StringBuffer(this.URLTemplate);
				}
				if (tile instanceof MercatorTextureTile) {
					MercatorSector s = ((MercatorTextureTile) tile).getMercatorSector();
					sb.append("&bbox=");
					sb.append(s.getMinLongitude().getDegrees());
					sb.append(",");
					sb.append(s.getMinLatPercent() * 90);
					sb.append(",");
					sb.append(s.getMaxLongitude().getDegrees());
					sb.append(",");
					sb.append(s.getMaxLatPercent() * 90);
					sb.append("&"); // terminate the query string
					// System.out.println(sb.toString());
				} else {
					Sector s = tile.getSector();
					sb.append("&bbox=");
					sb.append(s.getMinLongitude().getDegrees());
					sb.append(",");
					sb.append(s.getMinLatitude().getDegrees());
					sb.append(",");
					sb.append(s.getMaxLongitude().getDegrees());
					sb.append(",");
					sb.append(s.getMaxLatitude().getDegrees());
					sb.append("&"); // terminate the query string
				}
			//}
			return new java.net.URL(sb.toString().replace(" ", "%20"));
		}
	}

}
