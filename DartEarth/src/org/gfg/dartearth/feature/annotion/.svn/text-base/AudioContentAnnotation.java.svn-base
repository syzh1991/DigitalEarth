package org.gfg.dartearth.feature.annotion;

import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.util.Logging;
import gov.nasa.worldwind.util.WWIO;
import gov.nasa.worldwindx.examples.util.AudioPlayerAnnotation;
import gov.nasa.worldwindx.examples.util.AudioPlayerAnnotationController;

import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.SwingUtilities;

import org.gfg.dartearth.core.ApplicationTemplate;

public class AudioContentAnnotation extends ContentAnnotation {
	protected Clip clip;
    protected Object source;
    protected Thread readThread;

    public AudioContentAnnotation(ApplicationTemplate.AppFrame appFrame, AudioPlayerAnnotation annnotation,
        AudioPlayerAnnotationController controller, Object source, RenderableLayer layer)
    {
        super(appFrame, annnotation, controller, layer);
        this.source = source;
        this.retrieveAndSetClip(source);
    }

    public Object getSource()
    {
        return this.source;
    }

    public void detach()
    {
        super.detach();

        // Stop any threads or timers the controller may be actively running.
        AudioPlayerAnnotationController controller = (AudioPlayerAnnotationController) this.getController();
        if (controller != null)
        {
            this.stopController(controller);
        }

        // Stop any threads that may be reading the audio source.
        this.stopClipRetrieval();
    }

    @SuppressWarnings( {"StringEquality"})
    protected void stopController(AudioPlayerAnnotationController controller)
    {
        String status = controller.getClipStatus();
        if (status == AVKey.PLAY)
        {
            controller.stopClip();
        }
    }

    protected void retrieveAndSetClip(Object source)
    {
        this.startClipRetrieval(source);
    }

    protected void doRetrieveAndSetClip(final Object source)
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                getAnnotation().setBusy(true);
                appFrame.getWwd().redraw();
            }
        });

        final Clip clip = this.readClip(source);

        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                AudioPlayerAnnotationController controller = (AudioPlayerAnnotationController) getController();
                if (controller != null)
                {
                    controller.setClip(clip);
                }

                AudioPlayerAnnotation annotation = (AudioPlayerAnnotation) getAnnotation();
                if (annotation != null)
                {
                    if (clip == null)
                    {
                        annotation.getTitleLabel().setText(createErrorTitle(source.toString()));
                    }
                }

                getAnnotation().setBusy(false);
                appFrame.getWwd().redraw();
            }
        });
    }

    protected Clip readClip(Object source)
    {
        InputStream stream = null;
        try
        {
            stream = WWIO.openStream(source);
            return openAudioStream(stream);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            WWIO.closeStream(stream, source.toString());
        }

        return null;
    }

    protected void startClipRetrieval(final Object source)
    {
        this.readThread = new Thread(new Runnable()
        {
            public void run()
            {
                doRetrieveAndSetClip(source);
            }
        });
        this.readThread.start();
    }

    protected void stopClipRetrieval()
    {
        if (this.readThread != null)
        {
            if (this.readThread.isAlive())
            {
                this.readThread.interrupt();
            }
        }

        this.readThread = null;
    }
    
    public static String createErrorTitle(String path)
    {
        if (path == null)
        {
            String message = Logging.getMessage("nullValue.PathIsNull");
            Logging.logger().severe(message);
            throw new IllegalArgumentException(message);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Cannot open the resource at <i>").append(path).append("</i>");
        return sb.toString();
    }
    
    public static Clip openAudioStream(InputStream stream) throws Exception
    {
        if (stream == null)
        {
            String message = Logging.getMessage("nullValue.InputStreamIsNull");
            Logging.logger().severe(message);
            throw new IllegalArgumentException(message);
        }

        Clip clip = null;

        AudioInputStream ais = null;
        try
        {
            // AudioSystem.getAudioInputStream requires that InputStreams for audio clip resources support the
            // mark/reset functionality. Streams opened to class-path resources do not support this functionality, so
            // we provide this functionality by wrapping the specified InputStream in a BufferedInputStream, which
            // always supports mark/reset.
            ais = AudioSystem.getAudioInputStream(WWIO.getBufferedInputStream(stream));
            AudioFormat format = ais.getFormat();

            // Code taken from Java Sound demo at
            // http://java.sun.com/products/java-media/sound/samples/JavaSoundDemo
            //
            // We can't yet open the device for ALAW/ULAW playback, convert ALAW/ULAW to PCM.
            if ((format.getEncoding() == AudioFormat.Encoding.ULAW) ||
                (format.getEncoding() == AudioFormat.Encoding.ALAW))
            {
                AudioFormat tmp = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    format.getSampleRate(),
                    format.getSampleSizeInBits() * 2,
                    format.getChannels(),
                    format.getFrameSize() * 2,
                    format.getFrameRate(),
                    true);
                ais = AudioSystem.getAudioInputStream(tmp, ais);
                format = tmp;
            }

            DataLine.Info info = new DataLine.Info(
                Clip.class,
                ais.getFormat(),
                ((int) ais.getFrameLength() * format.getFrameSize()));

            clip = (Clip) AudioSystem.getLine(info);
            clip.open(ais);
        }
        finally
        {
            if (ais != null)
            {
                ais.close();
            }
        }

        return clip;
    }
}
