package org.gfg.dartearth.feature.measure;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.Polyline;
import gov.nasa.worldwind.render.SurfaceCircle;
import gov.nasa.worldwind.render.SurfaceEllipse;
import gov.nasa.worldwind.render.SurfacePolygon;
import gov.nasa.worldwind.render.SurfaceShape;
import gov.nasa.worldwind.util.measure.MeasureTool;

import java.util.List;

import org.gfg.dartearth.core.DartEarthAppFrame;
import org.gfg.dartearth.feature.draw.CircleLayer;
import org.gfg.dartearth.feature.draw.EllipseLayer;
import org.gfg.dartearth.feature.draw.LineLayer;
import org.gfg.dartearth.feature.draw.PathLayer;
import org.gfg.dartearth.feature.draw.PolygonLayer;

public class Measurer {
	private MeasureTool followTerrainMeasureTool;
	private MeasureTool unfollowTerrainMeasureTool;
	private WorldWindow wwd;
	private DartEarthAppFrame frame;
	private LengthMeasurePanel lengthMeasurePanel;
	private AreaAndLengthMeasurePanel areaAndLengthMeasurePanel;

	public Measurer(WorldWindow wwd, DartEarthAppFrame frame) {
		this.wwd = wwd;
		this.frame = frame;

	}

	public void clear() {
		followTerrainMeasureTool.clear();
		unfollowTerrainMeasureTool.clear();
		followTerrainMeasureTool = null;
		unfollowTerrainMeasureTool = null;
	}

	private void measureLength(final List<Position> positions, String layerName) {

		this.followTerrainMeasureTool = new MeasureTool(wwd);
		this.unfollowTerrainMeasureTool = new MeasureTool(wwd);

		Polyline folllowTerrainLine = new Polyline(positions);
		folllowTerrainLine.setFollowTerrain(true);
		followTerrainMeasureTool.setMeasureShape(folllowTerrainLine);
		Polyline unfollowTerrianLine = new Polyline(positions);
		unfollowTerrianLine.setFollowTerrain(false);
		unfollowTerrainMeasureTool.setMeasureShape(unfollowTerrianLine);

		int index = positions.size() / 2;
		Position centPosition = positions.get(index);
		frame.updateProfile(followTerrainMeasureTool);
		lengthMeasurePanel = new LengthMeasurePanel(followTerrainMeasureTool, unfollowTerrainMeasureTool, frame, centPosition);
		lengthMeasurePanel.refresh();
		frame.getMeasureDialog().show(layerName, lengthMeasurePanel);
	}

	public void measureLine(LineLayer layer) {
		List<Position> positions = layer.getPositions();
		String layerName = layer.getName();
		// System.out.println(positions);
		measureLength(positions, layerName);
		// System.out.println(followTerrainMeasureTool);
	}

	public void measurePath(PathLayer layer) {
		List<Position> positions = layer.getPositions();
		String layerName = layer.getName();
		measureLength(positions, layerName);
	}

	private void measureAreaAndLenth(SurfaceShape shape, String layerName, Position centPosition) {

		this.followTerrainMeasureTool = new MeasureTool(wwd);
		this.unfollowTerrainMeasureTool = new MeasureTool(wwd);

		followTerrainMeasureTool.setMeasureShape(shape);
		unfollowTerrainMeasureTool.setMeasureShape(shape);

		frame.updateProfile(followTerrainMeasureTool);

		areaAndLengthMeasurePanel = new AreaAndLengthMeasurePanel(followTerrainMeasureTool, unfollowTerrainMeasureTool, wwd, frame,
				centPosition);
		areaAndLengthMeasurePanel.refresh();
		frame.getMeasureDialog().show(layerName, areaAndLengthMeasurePanel);
	}

	public void measureCircle(CircleLayer layer) {
		SurfaceCircle circle = layer.getCircle();
		SurfaceCircle measureCircle = new SurfaceCircle(circle.getCenter(), circle.getRadius(), circle.getIntervals());

		LatLon centLatLng = measureCircle.getCenter();
		Position centPosition = Position.fromDegrees(centLatLng.getLatitude().getDegrees(), centLatLng.getLongitude().getDegrees());

		measureAreaAndLenth(measureCircle, layer.getName(), centPosition);
	}

	public void measureEllipse(EllipseLayer layer) {

		SurfaceEllipse ellipse = layer.getEllipse();

		SurfaceEllipse measueEllipse = new SurfaceEllipse(ellipse.getCenter(), ellipse.getMajorRadius(), ellipse.getMinorRadius(),
				ellipse.getHeading(), ellipse.getIntervals());

		LatLon centLatLng = ellipse.getCenter();
		Position centPosition = Position.fromDegrees(centLatLng.getLatitude().getDegrees(), centLatLng.getLongitude().getDegrees());

		measureAreaAndLenth(measueEllipse, layer.getName(), centPosition);
	}
	
	public void measurePolygon(PolygonLayer layer) {

		SurfacePolygon polygon = layer.getPolygon();
		
		SurfacePolygon measurePolygon = new SurfacePolygon(polygon.getLocations());
		
		LatLon centLatLng = measurePolygon.getLocations().iterator().next();
		Position centPosition = Position.fromDegrees(centLatLng.getLatitude().getDegrees(), centLatLng.getLongitude().getDegrees());
		
		measureAreaAndLenth(measurePolygon, layer.getName(), centPosition);
	}
}
