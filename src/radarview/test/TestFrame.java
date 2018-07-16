package radarview.test;

import radarview.RadarDetectObject;
import radarview.RadarPanel;

import javax.swing.*;
import java.awt.*;

public final class TestFrame extends JFrame
{
    private static final long serialVersionUID = 566945503012138006L;
    private final RadarPanel _radarPanel;

    public TestFrame()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(420, 430);
        this.setLayout(null);
        this.setTitle("Radar View");

        _radarPanel = new RadarPanel();
        _radarPanel.setBounds(0, 0, 400, 400);
        _radarPanel.setSize(new Dimension(400, 400));

        RadarDetectObject rdo1 = new RadarDetectObject(50, 75, .1f, 0.0f);
        _radarPanel.addRadarDetectObject(rdo1);
        RadarDetectObject rdo2 = new RadarDetectObject(90, 95, .1f, .1f);
        _radarPanel.addRadarDetectObject(rdo2);
        RadarDetectObject rdo3 = new RadarDetectObject(130, 415, 0.1f, -.1f);
        _radarPanel.addRadarDetectObject(rdo3);
        RadarDetectObject rdo6 = new RadarDetectObject(310, 95, -0.1f, .2f);
        _radarPanel.addRadarDetectObject(rdo6);
        RadarDetectObject rdo7 = new RadarDetectObject(90, 105, .1f, .2f);
        _radarPanel.addRadarDetectObject(rdo7);

        this.getContentPane().add(_radarPanel);

        this.setLocationRelativeTo(null);

        runRadar();
    }

    private void runRadar()
    {
        new Thread(() -> {
            while(true)
            {
                try
                {
                    _radarPanel.radarScan();
                    Thread.sleep(30);
                }
                catch(InterruptedException ignored)
                {
                }
            }
        }).start();
    }

    public static void main(String [] args)
    {
        TestFrame testFrame = new TestFrame();
        testFrame.setVisible(true);
    }
}