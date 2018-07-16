package radarview;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class RadarPanel extends JPanel
{
    private static final long serialVersionUID = -5606894491901340966L;

    private BufferedImage _imgRadarBeam;
    private int _beamAngle = 0;
    private final List<RadarDetectObject> _listDetectObjects;

    private Rectangle _rdrBeamRect;
    private Shape _rdrBeamShape;

    public RadarPanel()
    {
        this.setDoubleBuffered(true);

        _listDetectObjects = new ArrayList<>();

        try
        {
            _imgRadarBeam = ImageIO.read(getClass().getResourceAsStream("/img/radarBeam.png"));
            _rdrBeamRect = new Rectangle(_imgRadarBeam.getWidth(), _imgRadarBeam.getHeight());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void addRadarDetectObject(RadarDetectObject rdo)
    {
        _listDetectObjects.add(rdo);
    }

    public void radarScan()
    {
        for (RadarDetectObject rdo : _listDetectObjects)
        {
            rdo.radarBeamCollisionCheck(_rdrBeamShape);
            rdo.moveObject();
        }

        _beamAngle++;
        if(_beamAngle > 360)
            _beamAngle = 0;

        repaint();
    }

    @Override
    public void paint(Graphics g)
    {
        int centerX = 170;
        int centerY = 20;
        _rdrBeamRect.setLocation(centerX, centerY);

        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint
                (
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(Color.GREEN);
        g2d.drawOval(170, 170, 60, 60);
        g2d.drawOval(140, 140, 120, 120);
        g2d.drawOval(110, 110, 180, 180);
        g2d.drawOval(80, 80, 240, 240);
        g2d.drawOval(50, 50, 300, 300);
        g2d.drawOval(20, 20, 360, 360);

        g2d.drawLine(15, 200, 385, 200);
        g2d.drawLine(200, 15, 200, 385);

        for (RadarDetectObject rdo : _listDetectObjects)
        {
            rdo.drawObject(g2d);
        }

        AffineTransform tmpTrans = g2d.getTransform();
        AffineTransform rotatedBeam = AffineTransform.getRotateInstance(Math.toRadians(_beamAngle),200,200);
        //g2d.setTransform(rotatedBeam);
        rotatedBeam.translate(200, 200);
        g2d.drawImage(_imgRadarBeam, rotatedBeam, this);

        rotatedBeam.translate(-170, -20);
        _rdrBeamShape = rotatedBeam.createTransformedShape(_rdrBeamRect);
        //g2d.draw(_rdrBeamShape);
        g2d.setTransform(tmpTrans);

        /***calisan**/
//		AffineTransform at = new AffineTransform();
//		at.rotate(Math.toRadians(_beamAngle), _rdrBeamRect.getCenterX() + _rdrBeamRect.getWidth() /2 , _rdrBeamRect.getCenterY() + _rdrBeamRect.getHeight() / 2);
//		_rdrBeamShape = at.createTransformedShape(_rdrBeamRect);
//		g2d.draw(_rdrBeamShape);

    }
}
