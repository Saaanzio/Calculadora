package visao;

import javax.swing.*;
import java.awt.*;

public class Calculadora extends JFrame {
    public Calculadora(){

        organizarLayout();
        setSize(292,362);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void organizarLayout() {
        setLayout(new BorderLayout());
        Display display = new Display();
        display.setPreferredSize(new Dimension(233,60));
        add(display, BorderLayout.NORTH);

        Teclado teclado = new Teclado();
        add(teclado, BorderLayout.CENTER);
    }
}
