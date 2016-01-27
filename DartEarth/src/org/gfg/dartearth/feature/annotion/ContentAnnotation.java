package org.gfg.dartearth.feature.annotion;

import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwindx.examples.util.DialogAnnotation;
import gov.nasa.worldwindx.examples.util.DialogAnnotationController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.gfg.dartearth.core.ApplicationTemplate;

public class ContentAnnotation implements ActionListener {

	protected ApplicationTemplate.AppFrame appFrame;
    protected DialogAnnotation annnotation;
    protected DialogAnnotationController controller;
    protected RenderableLayer layer;

    public ContentAnnotation(ApplicationTemplate.AppFrame appFrame, DialogAnnotation annnotation, DialogAnnotationController controller, RenderableLayer layer)
    {
        this.appFrame = appFrame;
        this.annnotation = annnotation;
        this.annnotation.addActionListener(this);
        this.controller = controller;
        this.layer = layer;
        attach();
    }

    public ApplicationTemplate.AppFrame  getAppFrame()
    {
        return this.appFrame;
    }

    public DialogAnnotation getAnnotation()
    {
        return this.annnotation;
    }

    public DialogAnnotationController getController()
    {
        return this.controller;
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e == null)
            return;

        if (e.getActionCommand() == AVKey.CLOSE)
        {
            closeResource(this);
        }
    }

    public void detach()
    {
        this.getController().setEnabled(false);

        layer.removeRenderable(this.getAnnotation());
    }

    public void attach()
    {
        this.getController().setEnabled(true);

        layer.removeRenderable(this.getAnnotation());
        layer.addRenderable(this.getAnnotation());
    }
    
    protected void closeResource(ContentAnnotation content)
    {
        if (content == null)
            return;

        content.detach();
    }

}
