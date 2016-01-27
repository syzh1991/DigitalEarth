package org.gfg.dartearth.feature.annotion;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;

import org.gfg.dartearth.core.ApplicationTemplate;

import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.AnnotationAttributes;
import gov.nasa.worldwind.render.BalloonAttributes;
import gov.nasa.worldwind.render.BasicBalloonAttributes;
import gov.nasa.worldwind.render.GlobeBrowserBalloon;
import gov.nasa.worldwind.render.PointPlacemark;
import gov.nasa.worldwind.render.PointPlacemarkAttributes;
import gov.nasa.worldwind.render.Size;
import gov.nasa.worldwindx.examples.util.AudioPlayerAnnotation;
import gov.nasa.worldwindx.examples.util.AudioPlayerAnnotationController;
import gov.nasa.worldwindx.examples.util.SlideShowAnnotation;
import gov.nasa.worldwindx.examples.util.SlideShowAnnotationController;

public class MutimediaAnnotationLayer extends RenderableLayer {

	private MutimediaAnnotationVO annotationVO;
	
	private ContentAnnotation annotation;
	
	private ApplicationTemplate.AppFrame appFrame;
	
	private Font titleFont=new Font("黑体",Font.BOLD,16);
	
	public MutimediaAnnotationLayer(MutimediaAnnotationVO annotationVO, ApplicationTemplate.AppFrame appFrame) {
		this.annotationVO = annotationVO;
		this.appFrame = appFrame;
		this.setName(annotationVO.getLayerName());
		Position position = Position.fromDegrees(annotationVO.getLat(), annotationVO.getLng());
		
		switch (annotationVO.getType()) {
			case 0:
				{
					AudioPlayerAnnotation apa = new AudioPlayerAnnotation(position);
			        apa.setAlwaysOnTop(true);
			        apa.getTitleLabel().setText(annotationVO.getLayerName());
//			        AnnotationAttributes  attrs = new AnnotationAttributes();
//					attrs.setFont(titleFont);
//		
//					apa.setAttributes(attrs);
					
			        AudioPlayerAnnotationController aController = new AudioPlayerAnnotationController(appFrame.getWwd(), apa);
					
					this.annotation = new AudioContentAnnotation(appFrame, apa, aController, (Object)annotationVO.getSource(), this);
				}
				break;
			case 1:
				{
					SlideShowAnnotation ssa = new SlideShowAnnotation(position);
			        ssa.setAlwaysOnTop(true);
			        ssa.getTitleLabel().setText(annotationVO.getLayerName());
			        
			        AnnotationAttributes  attrs = new AnnotationAttributes();
					attrs.setFont(titleFont);
		
					ssa.setAttributes(attrs);

			        String[] images = annotationVO.getSource().split(",");
			        ArrayList<String> imageList = new ArrayList<String>();
			        for(String image : images) {
			        	imageList.add(image);
			        }
			        
			        SlideShowAnnotationController sController = new SlideShowAnnotationController(appFrame.getWwd(), ssa,
			        		(Iterable<?>)imageList);

			        this.annotation = new ImageContentAnnotation(appFrame, ssa, sController, this);
				}
				break;
			default:
				break;
		}
		
//		AudioPlayerAnnotation apa = new AudioPlayerAnnotation(position);
//        apa.setAlwaysOnTop(true);
//        apa.getTitleLabel().setText(annotationVO.getLayerName());
//
//        AudioPlayerAnnotationController controller = new AudioPlayerAnnotationController(appFrame.getWwd(), apa);
//		
//		this.annotation = new AudioContentAnnotation(appFrame, apa, controller, (Object)annotationVO.getSource(), this);
	}

	public MutimediaAnnotationVO getAnnotationVO() {
		return annotationVO;
	}

	public void fresh() {
		this.removeAllRenderables();
		Position position = Position.fromDegrees(annotationVO.getLat(), annotationVO.getLng());

		switch (annotationVO.getType()) {
		case 0:
			{
				AudioPlayerAnnotation apa = new AudioPlayerAnnotation(position);
		        apa.setAlwaysOnTop(true);
		        apa.getTitleLabel().setText(annotationVO.getLayerName());
	
		        AudioPlayerAnnotationController aController = new AudioPlayerAnnotationController(appFrame.getWwd(), apa);
				
				this.annotation = new AudioContentAnnotation(appFrame, apa, aController, (Object)annotationVO.getSource(), this);
			}
			break;
		case 1:
			{
				SlideShowAnnotation ssa = new SlideShowAnnotation(position);
		        ssa.setAlwaysOnTop(true);
		        ssa.getTitleLabel().setText(annotationVO.getLayerName());

		        String[] images = annotationVO.getSource().split(",");
		        ArrayList<String> imageList = new ArrayList<String>();
		        for(String image : images) {
		        	imageList.add(image);
		        }
		        
		        SlideShowAnnotationController sController = new SlideShowAnnotationController(appFrame.getWwd(), ssa,
		        		(Iterable<?>)imageList);

		        this.annotation = new ImageContentAnnotation(appFrame, ssa, sController, this);
			}
			break;
		default:
			break;
	}
	}

	
	
	
}
