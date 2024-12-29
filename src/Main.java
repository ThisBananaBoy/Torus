//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        var size = Toolkit.getDefaultToolkit().getScreenSize();
        int width = size.width;
        int height = size.height;


        var frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);


        var screen = new Screen();
        var proj = new Projection(10, 1000, 90, width, height);
        proj.initializeProjecionMatrix();
        screen.setProjectionMatrix(proj);


        // slider einbauen
        var sliderList = new ArrayList<Slider>();
        sliderList.add(new Slider(0, 360, 45, "Winkel-x"));
        sliderList.add(new Slider(0, 360, 45, "Winkel-y"));

        sliderList.add(new Slider(5, 300, 30, "Max-Segements-Außen"));
        sliderList.add(new Slider(5, 300, 30, "Max-Segements-Innen"));

        sliderList.add(new Slider(1, 300, 50, "Radius-Innenkreis"));
        sliderList.add(new Slider(1, 300, 100, "Radius-Außenkreis"));

        sliderList.add(new Slider(0, 1000, 500, "Verschiebung-Z"));
        sliderList.add(new Slider(-500, 500, 0, "Verschiebung-Y"));
        sliderList.add(new Slider(-500, 500, 0, "Verschiebung-X"));

        sliderList.add(new Slider(0, 255, 255, "R"));
        sliderList.add(new Slider(0, 255, 255, "G"));
        sliderList.add(new Slider(0, 255, 255, "B"));

        //Panel bauen
        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS));


        //HashMap einbauen
        Map<String, Integer> sliderParams= new HashMap<>();

        for (Slider slider : sliderList) {
           sliderParams.put(slider.titel, slider.placeholder);
        }


        for (Slider slider_component : sliderList) {
            TitledBorder title = BorderFactory.createTitledBorder(slider_component.titel);
            JSlider slider = new JSlider(slider_component.min, slider_component.max, slider_component.placeholder); // Min: 0, Max: 100, Startwert: 50
            slider.setMajorTickSpacing(200);
            slider.setPaintTicks(true);
            slider.setBorder(title);

            JLabel valueLabel = new JLabel(String.valueOf(slider_component.placeholder));

            sliderPanel.add(slider);
            sliderPanel.add(valueLabel, BorderLayout.EAST);

            slider.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    sliderParams.put(slider_component.titel, slider.getValue());
                   screen.updateScreen(sliderParams);
                   screen.repaint();
                   valueLabel.setText(String.valueOf(slider.getValue()));
                }
            });
        }

        frame.setVisible(true);
        screen.updateScreen(sliderParams);
        screen.repaint();
        frame.add(screen);
        frame.add(sliderPanel, BorderLayout.WEST);
//        var i = 0;
//        while (true) {
//            i += 10;
//            proj.setRotation(i);
//            screen.setProjectionMatrix(proj);
//            screen.repaint();
//            frame.add(screen);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                // Handle the exception if the thread is interrupted
//                System.err.println("Thread was interrupted!");
//            }
//        }

    }
}