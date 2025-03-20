import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JPanel;

public class Jugador {

    private int TOTAL_CARTAS = 10;
    private int MARGEN = 10;
    private int DISTANCIA = 40;

    private Carta[] cartas = new Carta[TOTAL_CARTAS];
    private Random r = new Random();

    public void repartir() {
        for (int i = 0; i < cartas.length; i++) {
            cartas[i] = new Carta(r);
        }
    }

    public void mostrar(JPanel pnl) {
        pnl.removeAll();
        int posicionHorizontal = MARGEN + cartas.length * DISTANCIA;
        for (Carta c : cartas) {
            c.mostrar(pnl, posicionHorizontal, MARGEN);
            posicionHorizontal -= DISTANCIA;
        }
        pnl.repaint();
    }

    public String getGrupos() {
        String mensaje = "No se encontraron grupos";

        int[] contadores = new int[NombreCarta.values().length];
        for (Carta c : cartas) {
            contadores[c.getNombre().ordinal()]++;
        }

        boolean hayGrupos = false;
        for (int c : contadores) {
            if (c >= 2) {
                hayGrupos = true;
                break;
            }
        }
        if (hayGrupos) {
            mensaje = "Se encontraron los siguientes grupos:\n";
            int p=0;
            for (int c : contadores) {
                if (c >= 2) {
                    mensaje += Grupo.values()[c] + " de " + NombreCarta.values()[p] + "\n";
                }
                p++;
            }
        }

        return mensaje;
    }
    
    public void formarEscalera() {
        Pinta pintaSeleccionada = Pinta.values()[r.nextInt(Pinta.values().length)];
        int nombreInicial = r.nextInt(NombreCarta.values().length - 4);
        
       
        for (int i = 0; i < 5; i++) {
            int indiceNuevo = nombreInicial + i + 1; 
            int indiceCarta = 0;
    
            switch (pintaSeleccionada) {
                case TREBOL:
                    indiceCarta = indiceNuevo;
                    break;
                case PICA:
                    indiceCarta = indiceNuevo + 13;
                    break;
                case CORAZON:
                    indiceCarta = indiceNuevo + 26;
                    break;
                case DIAMANTE:
                    indiceCarta = indiceNuevo + 39;
                    break;
            }
            
            
            cartas[i] = new Carta(indiceCarta);
        }
        
       
        for (int i = 5; i < TOTAL_CARTAS; i++) {
            cartas[i] = new Carta(r);
        }
    }
    
    public String getEscaleras() {
        String mensaje = "No se encontraron escaleras";
        
     
        List<Carta>[] cartasPorPinta = new ArrayList[Pinta.values().length];
        for (int i = 0; i < cartasPorPinta.length; i++) {
            cartasPorPinta[i] = new ArrayList<>();
        }
        
        
        for (Carta c : cartas) {
            cartasPorPinta[c.getPinta().ordinal()].add(c);
        }
        
        
        for (List<Carta> listaCartas : cartasPorPinta) {
            Collections.sort(listaCartas, new Comparator<Carta>() {
                @Override
                public int compare(Carta c1, Carta c2) {
                    return c1.getNombre().ordinal() - c2.getNombre().ordinal();
                }
            });
        }
        
        
        List<String> escaleras = new ArrayList<>();
        for (int i = 0; i < cartasPorPinta.length; i++) {
            List<Carta> listaCartas = cartasPorPinta[i];
            if (listaCartas.size() < 3) continue; 
            
            List<List<Carta>> secuencias = new ArrayList<>();
            List<Carta> secuenciaActual = new ArrayList<>();
            
            
            for (int j = 0; j < listaCartas.size(); j++) {
                Carta cartaActual = listaCartas.get(j);
                
                if (secuenciaActual.isEmpty()) {
                    secuenciaActual.add(cartaActual);
                } else {
                    Carta cartaAnterior = secuenciaActual.get(secuenciaActual.size() - 1);
                    
                
                    if (cartaActual.getNombre().ordinal() == cartaAnterior.getNombre().ordinal() + 1) {
                        secuenciaActual.add(cartaActual);
                    } else if (cartaActual.getNombre().ordinal() != cartaAnterior.getNombre().ordinal()) {
                        
                        if (secuenciaActual.size() >= 3) {
                            secuencias.add(new ArrayList<>(secuenciaActual));
                        }
                        secuenciaActual = new ArrayList<>();
                        secuenciaActual.add(cartaActual);
                    }
                    
                }
            }
            
            
            if (secuenciaActual.size() >= 3) {
                secuencias.add(secuenciaActual);
            }
            
            
            for (List<Carta> secuencia : secuencias) {
                String nombreEscalera;
                if (secuencia.size() <= 10) {
                    nombreEscalera = Grupo.values()[secuencia.size()].toString();
                } else {
                    nombreEscalera = secuencia.size() + " cartas en secuencia";
                }
                
                StringBuilder cartasEscalera = new StringBuilder();
                for (Carta c : secuencia) {
                    cartasEscalera.append(c.getNombre()).append(", ");
                }
                
                String cartasStr = cartasEscalera.substring(0, cartasEscalera.length() - 2);
                
                escaleras.add(nombreEscalera + " de " + Pinta.values()[i] + ": " + cartasStr);
            }
        }
        
        if (!escaleras.isEmpty()) {
            mensaje = "Se encontraron las siguientes escaleras:\n";
            for (String escalera : escaleras) {
                mensaje += escalera + "\n";
            }
        }
        
        return mensaje;
    }

    public int calcularPuntaje() {
        int puntaje = 0;
    
       
        for (Carta c : cartas) {
            
            if (c.getNombre() == NombreCarta.AS || c.getNombre() == NombreCarta.JACK ||
                c.getNombre() == NombreCarta.QUEEN || c.getNombre() == NombreCarta.KING) {
                puntaje += 10;
            } else {
             
                puntaje += c.getNombre().ordinal() + 1;  
            }
        }
    
        return puntaje;
    }
    
}