
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

public class FrmJuego extends JFrame {

    private JButton btnRepartir;
    private JButton btnVerificar;
    private JButton btnFormarEscalera;
    private JPanel pnlJugador1;
    private JPanel pnlJugador2;
    private JTabbedPane tpJugadores;

    public FrmJuego() {
        btnRepartir = new JButton();
        btnVerificar = new JButton();
        btnFormarEscalera = new JButton();
        tpJugadores = new JTabbedPane();
        pnlJugador1 = new JPanel();
        pnlJugador2 = new JPanel();

        setSize(600, 300);
        setTitle("Juego de Cartas");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        pnlJugador1.setBackground(new Color(255,0,255));
        pnlJugador1.setLayout(null);
        pnlJugador2.setBackground(new Color(255, 175, 175));
        pnlJugador2.setLayout(null);

        tpJugadores.setBounds(10, 40, 550, 170);
        tpJugadores.addTab("Corrosco", pnlJugador1);
        tpJugadores.addTab("Fray Leon", pnlJugador2);

        btnRepartir.setBounds(10, 10, 100, 25);
        btnRepartir.setText("Repartir");
        btnRepartir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnRepartirClick(evt);
            }
        });

        btnVerificar.setBounds(120, 10, 100, 25);
        btnVerificar.setText("Verificar");
        btnVerificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnVerificarClick(evt);
            }
        });

        btnFormarEscalera.setBounds(230, 10, 130, 25);
        btnFormarEscalera.setText("Formar Escalera");
        btnFormarEscalera.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnFormarEscaleraClick(evt);
            }
        });

        getContentPane().setLayout(null);
        getContentPane().add(tpJugadores);
        getContentPane().add(btnRepartir);
        getContentPane().add(btnVerificar);
        getContentPane().add(btnFormarEscalera);

        jugador1 = new Jugador();
        jugador2 = new Jugador();
    }

    Jugador jugador1, jugador2;

    private void btnRepartirClick(ActionEvent evt) {
        jugador1.repartir();
        jugador1.mostrar(pnlJugador1);

        jugador2.repartir();
        jugador2.mostrar(pnlJugador2);
    }

    private void btnVerificarClick(ActionEvent evt) {
        int pestaña = tpJugadores.getSelectedIndex();
        switch (pestaña) {
            case 0:
                JOptionPane.showMessageDialog(null, jugador1.getGrupos());
                break;
            case 1:
                JOptionPane.showMessageDialog(null, jugador2.getGrupos());
                break;
        }
    }

    private void btnFormarEscaleraClick(ActionEvent evt) {
        int pestaña = tpJugadores.getSelectedIndex();

        switch (pestaña) {
            case 0:
                jugador1.formarEscalera();
                jugador1.mostrar(pnlJugador1);

             
                JOptionPane.showMessageDialog(null, jugador1.getEscaleras());

                int puntajeJugador1 = jugador1.calcularPuntaje();
                JOptionPane.showMessageDialog(null, "Puntaje de Jugador 1: " + puntajeJugador1);
                break;

            case 1:
                jugador2.formarEscalera();
                jugador2.mostrar(pnlJugador2);

                JOptionPane.showMessageDialog(null, jugador2.getEscaleras());

                
                int puntajeJugador2 = jugador2.calcularPuntaje();
                JOptionPane.showMessageDialog(null, "Puntaje de Jugador 2: " + puntajeJugador2);
                break;
        }
    }

    public static void main(String args[]) {
        FrmJuego frm = new FrmJuego();
        frm.setVisible(true);
    }
}
