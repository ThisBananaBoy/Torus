import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    static JFrame frame = new Frame();

            public Frame()
            {
                add(new Screen());
                setUndecorated(true);
                setSize(1000, 1000);
                setVisible(true);
            }
}
