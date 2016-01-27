package org.gfg.dartearth.util;

import gov.nasa.worldwind.Movable;
import gov.nasa.worldwind.View;
import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.event.DragSelectEvent;
import gov.nasa.worldwind.event.SelectEvent;
import gov.nasa.worldwind.event.SelectListener;
import gov.nasa.worldwind.geom.Intersection;
import gov.nasa.worldwind.geom.Line;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.geom.Vec4;
import gov.nasa.worldwind.globes.Globe;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.Renderable;
import gov.nasa.worldwind.util.Logging;
import gov.nasa.worldwind.util.RayCastingSupport;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.util.Iterator;

import org.gfg.dartearth.feature.annotion.AnnotationLayer;
import org.gfg.dartearth.feature.draw.CircleLayer;
import org.gfg.dartearth.feature.draw.ShapeLayer;

public class BasicDragger implements SelectListener {
	private final WorldWindow wwd;
	private boolean dragging = false;
    private boolean active=true;
	private Point dragRefCursorPoint;
	private Vec4 dragRefObjectPoint;
	private double dragRefAltitude;
	//private Cursor cursor;
	
	private RenderableLayer layer;

	public BasicDragger(WorldWindow wwd,RenderableLayer layer) {
		if (wwd == null) {
			String msg = Logging.getMessage("nullValue.WorldWindow");
			Logging.logger().severe(msg);
			throw new IllegalArgumentException(msg);
		}
		this.layer=layer;
		//System.out.println(this.layer);
		this.wwd = wwd;
	}

	public boolean isDragging() {
		return this.dragging;
	}

	public void selected(SelectEvent event) {
	//	if(((Component) (wwd)).getCursor().equals(Cursor.CROSSHAIR_CURSOR)){
	//		System.out.println(((Component) (wwd)).getCursor());
	//		return ;
//		}

		if (event == null) {
			String msg = Logging.getMessage("nullValue.EventIsNull");
			Logging.logger().severe(msg);
			throw new IllegalArgumentException(msg);
		}

		if (event.getEventAction().equals(SelectEvent.DRAG_END)) {
			this.dragging = false;
//			if(cursor!=null){
//				((Component) wwd).getCursor().equals(cursor);
//				cursor=null;
//			}
			event.consume();
		} else if (event.getEventAction().equals(SelectEvent.DRAG)&&this.active) {
			DragSelectEvent dragEvent = (DragSelectEvent) event;
			Object topObject = dragEvent.getTopObject();
			//if(layer.)
			if (topObject == null){
				return;
			}
			if (!(topObject instanceof Movable)){
				return;
			}
			//System.out.println(this.layer);
			Iterator<Renderable> renderables=this.layer.getRenderables().iterator();
			boolean selectTheCorrectLayer=false;
			while(renderables.hasNext()){
				if(renderables.next()==topObject){
					selectTheCorrectLayer=true;
					break;
				}
			}
			if(selectTheCorrectLayer==false){
				return;
			}
				
			Movable dragObject = (Movable) topObject;
			View view = wwd.getView();
			Globe globe = wwd.getModel().getGlobe();

			// Compute dragged object ref-point in model coordinates.
			// Use the Icon and Annotation logic of elevation as offset above
			// ground when below max elevation.
			Position refPos = dragObject.getReferencePosition();
			if (refPos == null)
				return;

			Vec4 refPoint = null;
			if (refPos.getElevation() < globe.getMaxElevation())
				refPoint = wwd.getSceneController().getTerrain().getSurfacePoint(refPos);
			if (refPoint == null)
				refPoint = globe.computePointFromPosition(refPos);

			if (!this.isDragging()) // Dragging started
			{
				// Save initial reference points for object and cursor in screen
				// coordinates
				// Note: y is inverted for the object point.
				this.dragRefObjectPoint = view.project(refPoint);
				// Save cursor position
				this.dragRefCursorPoint = dragEvent.getPreviousPickPoint();
				// Save start altitude
				this.dragRefAltitude = globe.computePositionFromPoint(refPoint).getElevation();
			}

			// Compute screen-coord delta since drag started.
			int dx = dragEvent.getPickPoint().x - this.dragRefCursorPoint.x;
			int dy = dragEvent.getPickPoint().y - this.dragRefCursorPoint.y;

			// Find intersection of screen coord (refObjectPoint + delta) with
			// globe.
			double x = this.dragRefObjectPoint.x + dx;
			double y = event.getMouseEvent().getComponent().getSize().height - this.dragRefObjectPoint.y + dy - 1;
			Line ray = view.computeRayFromScreenPoint(x, y);
			Position pickPos = null;
			if (view.getEyePosition().getElevation() < globe.getMaxElevation() * 10) {
				// Use ray casting below some altitude
				// Try ray intersection with current terrain geometry
				Intersection[] intersections = wwd.getSceneController().getTerrain().intersect(ray);
				if (intersections != null && intersections.length > 0)
					pickPos = globe.computePositionFromPoint(intersections[0].getIntersectionPoint());
				else
					// Fallback on raycasting using elevation data
					pickPos = RayCastingSupport.intersectRayWithTerrain(globe, ray.getOrigin(), ray.getDirection(), 200, 20);
			}
			if (pickPos == null) {
				// Use intersection with sphere at reference altitude.
				Intersection inters[] = globe.intersect(ray, this.dragRefAltitude);
				if (inters != null)
					pickPos = globe.computePositionFromPoint(inters[0].getIntersectionPoint());
			}

			if (pickPos != null) {
				// Intersection with globe. Move reference point to the
				// intersection point,
				// but maintain current altitude.
				Position p = new Position(pickPos, dragObject.getReferencePosition().getElevation());
				dragObject.moveTo(p);
				if(this.layer instanceof AnnotationLayer){
					((AnnotationLayer) this.layer).fresh(p);
				}else if(this.layer instanceof ShapeLayer){
					((ShapeLayer) this.layer).refresh(p);
				}
				
			}
//		if(cursor==null){
//			cursor=((Component)wwd).getCursor();
//			((Component)wwd).setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
//		}
			
			this.dragging = true;
			event.consume();
		}
	}

	public boolean isActive() {
		return active;
	}

	synchronized public  void setActive(boolean active) {
		this.active = active;
	}
	
}