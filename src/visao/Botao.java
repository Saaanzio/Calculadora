package visao;

import javax.swing.*;
import java.awt.*;
public class Botao extends JButton {
    public Botao(String texto, Color cor){
        setText(texto);
        setOpaque(true);
        setBackground(cor);
        setFont(new Font("courier",Font.PLAIN,25));
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setForeground(Color.WHITE);
        setFocusPainted(false);
    }
}
