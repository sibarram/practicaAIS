package buscaminas;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Buscaminas extends JFrame implements ActionListener, MouseListener {

    Timer t;
    JMenuBar menuBar;
    JMenu menu, submenu;
    JMenuItem tiempos, guardar, cargar;
    JRadioButtonMenuItem principiante, intermedio, experto, personalizado;
    ButtonGroup group;
    JButton b[][];
    JButton reiniciar;
    JPanel tablero;
    JPanel botonera;
    JTextField minasRestantes, tiempo;
    int nomines = 40;
    int restantes;
    int n = 16;
    int m = 16;
    int row;
    int column;
    int guesses[][];
    int perm[][];
    int[][] mines;
    String tmp;
    boolean found = false;
    boolean allmines, personalizadoBool;
    int deltax[] = {-1, 0, 1, -1, 1, -1, 0, 1};
    int deltay[] = {-1, -1, -1, 0, 0, 1, 1, 1};
    double starttime, endtime, currenttime;

    public Buscaminas() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        t = new Timer(1000, reloj);
        perm = new int[n][m];
        allmines = false;
        restantes = nomines;
        guesses = new int[n + 2][m + 2];
        mines = new int[n + 2][m + 2];
        b = new JButton[n][m];

        menuBar = new JMenuBar();
        menu = new JMenu("Opciones");
        menuBar.add(menu);
        guardar = new JMenuItem("Guardar partida");
        menu.add(guardar);
        cargar = new JMenuItem("Cargar partida");
        menu.add(cargar);
        tiempos = new JMenuItem("Mejores tiempos");
        menu.add(tiempos);
        menu.addSeparator();
        submenu = new JMenu("Dificultad");
        group = new ButtonGroup();
        principiante = new JRadioButtonMenuItem("Principiante (10x10, 10 minas)");
        group.add(principiante);
        submenu.add(principiante);
        intermedio = new JRadioButtonMenuItem("Intermedio (16x16, 40 minas)");
        intermedio.setSelected(true);
        group.add(intermedio);
        submenu.add(intermedio);
        experto = new JRadioButtonMenuItem("Experto (32x16, 99 minas)");
        group.add(experto);
        submenu.add(experto);
        personalizado = new JRadioButtonMenuItem("Personalizado");
        group.add(personalizado);
        submenu.add(personalizado);
        menu.add(submenu);
        this.setJMenuBar(menuBar);
        cargar.addActionListener(menus);
        guardar.addActionListener(menus);
        tiempos.addActionListener(menus);
        principiante.addActionListener(dif);
        intermedio.addActionListener(dif);
        experto.addActionListener(dif);
        personalizado.addActionListener(dif);

        reiniciar = new JButton("Reiniciar");
        reiniciar.addActionListener(this);
        reiniciar.addMouseListener(this);
        reiniciar.setEnabled(true);
        tablero = new JPanel();
        tablero.setVisible(true);
        tablero.setLayout(new GridLayout(n, m));
        minasRestantes = new JTextField();
        minasRestantes.setEnabled(false);
        minasRestantes.setText(String.valueOf(restantes));
        tiempo = new JTextField();
        tiempo.setEnabled(false);

        botonera = new JPanel();
        botonera.setVisible(true);
        botonera.setLayout(new GridLayout(1, 5));
        botonera.add(reiniciar);
        botonera.add(new JLabel("Minas restantes: "));
        botonera.add(minasRestantes);
        botonera.add(new JLabel("Tiempo: "));
        botonera.add(tiempo);
        this.setLayout(new BorderLayout());
        add(botonera, BorderLayout.PAGE_START);
        add(tablero, BorderLayout.CENTER);

        for (int y = 0; y < m + 2; y++) {
            mines[0][y] = 3;
            mines[n + 1][y] = 3;
            guesses[0][y] = 3;
            guesses[n + 1][y] = 3;
        }
        for (int x = 0; x < n + 2; x++) {
            mines[x][0] = 3;
            mines[x][m + 1] = 3;
            guesses[x][0] = 3;
            guesses[x][m + 1] = 3;
        }
        do {
            int check = 0;
            for (int y = 1; y < m + 1; y++) {
                for (int x = 1; x < n + 1; x++) {
                    mines[x][y] = 0;
                    guesses[x][y] = 0;
                }
            }
            for (int x = 0; x < nomines; x++) {
                mines[(int) (Math.random() * (n) + 1)][(int) (Math.random() * (m) + 1)] = 1;
            }
            for (int x = 0; x < n; x++) {
                for (int y = 0; y < m; y++) {
                    if (mines[x + 1][y + 1] == 1) {
                        check++;
                    }
                }
            }
            if (check == nomines) {
                allmines = true;
            }
        } while (allmines == false);
        for (int y = 0; y < m; y++) {
            for (int x = 0; x < n; x++) {
                if ((mines[x + 1][y + 1] == 0) || (mines[x + 1][y + 1] == 1)) {
                    perm[x][y] = perimcheck(x, y);
                }
                b[x][y] = new JButton(" ");
                b[x][y].addActionListener(this);
                b[x][y].addMouseListener(this);
                tablero.add(b[x][y]);
                b[x][y].setEnabled(true);
            }//end inner for
        }//end for
        pack();
        tablero.setVisible(true);
        setVisible(true);
        for (int y = 0; y < m + 2; y++) {
            for (int x = 0; x < n + 2; x++) {
                System.out.print(mines[x][y]);
            }
            System.out.println("");
        }
        starttime = System.nanoTime();
        t.start();
    }//end constructor Mine()

    private ActionListener reloj = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            currenttime = System.nanoTime();
            tiempo.setText(String.valueOf((int) ((currenttime - starttime) / 1000000000)));
        }
    };

    private ActionListener menus = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            JMenuItem current = (JMenuItem) ae.getSource();
            if (current == guardar) {
                // Cod Guardar
            } else if (current == cargar) {
                //Cod cargar
            } else if (current == tiempos) {
                //Cod tiempos
            } else {
                System.out.println("Error en boton. Menu principal");
                System.exit(-1);
            }
        }
    };

    private ActionListener dif = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            JRadioButtonMenuItem current = (JRadioButtonMenuItem) ae.getSource();
            if (current == principiante) {
                n = 10;
                m = 10;
                nomines = 10;
                reinicio();
            } else if (current == intermedio) {
                n = 16;
                m = 16;
                nomines = 40;
                reinicio();
            } else if (current == experto) {
                n = 16;
                m = 32;
                nomines = 99;
                reinicio();
            } else if (current == personalizado) {
                //Cod personalizado
            } else {
                System.out.println("Error en boton. Menu principal");
                System.exit(-1);
            }
        }
    };

    public void actionPerformed(ActionEvent e) {
        found = false;
        JButton current = (JButton) e.getSource();
        if (current == reiniciar) {
            System.out.println("REINICIO");
            reinicio();
        } else {
            for (int y = 0; y < m; y++) {
                for (int x = 0; x < n; x++) {
                    JButton t = b[x][y];
                    if (t == current) {
                        row = x;
                        column = y;
                        found = true;
                    }
                }//end inner for
            }//end for
            if (!found) {
                System.out.println("didn't find the button, there was an error ");
                System.exit(-1);
            }
            Component temporaryLostComponent = null;
            if (b[row][column].getBackground() == Color.orange) {
                return;
            } else if (mines[row + 1][column + 1] == 1) {
                JOptionPane.showMessageDialog(temporaryLostComponent, "You set off a Mine!!!!.");
                reinicio();
            } else {
                tmp = Integer.toString(perm[row][column]);
                if (perm[row][column] == 0) {
                    tmp = " ";
                }
                b[row][column].setText(tmp);
                b[row][column].setEnabled(false);
                checkifend();
                if (perm[row][column] == 0) {
                    scan(row, column);
                    checkifend();
                }
            }
        }
    }

    public void reinicio() {
        allmines = false;
        restantes = nomines;
        minasRestantes.setText(String.valueOf(restantes));
        b = new JButton[n][m];
        perm = new int[n][m];
        guesses = new int[n + 2][m + 2];
        mines = new int[n + 2][m + 2];
        tablero.setVisible(false);
        this.remove(tablero);
        tablero = new JPanel();
        tablero.setVisible(true);
        tablero.setLayout(new GridLayout(n, m));
        add(tablero, BorderLayout.CENTER);

        for (int y = 0; y < m + 2; y++) {
            mines[0][y] = 3;
            mines[n + 1][y] = 3;
            guesses[0][y] = 3;
            guesses[n + 1][y] = 3;
        }
        for (int x = 0; x < n + 2; x++) {
            mines[x][0] = 3;
            mines[x][m + 1] = 3;
            guesses[x][0] = 3;
            guesses[x][m + 1] = 3;
        }
        do {
            int check = 0;
            for (int y = 1; y < m + 1; y++) {
                for (int x = 1; x < n + 1; x++) {
                    mines[x][y] = 0;
                    guesses[x][y] = 0;
                }
            }
            for (int x = 0; x < nomines; x++) {
                mines[(int) (Math.random() * (n) + 1)][(int) (Math.random() * (m) + 1)] = 1;
            }
            for (int x = 0; x < n; x++) {
                for (int y = 0; y < m; y++) {
                    if (mines[x + 1][y + 1] == 1) {
                        check++;
                    }
                }
            }
            if (check == nomines) {
                allmines = true;
            }
        } while (allmines == false);
        for (int y = 0; y < m; y++) {
            for (int x = 0; x < n; x++) {
                if ((mines[x + 1][y + 1] == 0) || (mines[x + 1][y + 1] == 1)) {
                    perm[x][y] = perimcheck(x, y);
                }
                b[x][y] = new JButton(" ");
                b[x][y].addActionListener(this);
                b[x][y].addMouseListener(this);
                tablero.add(b[x][y]);
                b[x][y].setEnabled(true);
            }//end inner for
        }//end for
        for (int y = 0; y < m + 2; y++) {
            for (int x = 0; x < n + 2; x++) {
                System.out.print(mines[x][y]);
            }
            System.out.println("");
        }
        starttime = System.nanoTime();
        t.start();
    }

    public void checkifend() {
        int check = 0;
        for (int y = 0; y < m; y++) {
            for (int x = 0; x < n; x++) {
                if (b[x][y].isEnabled()) {
                    check++;
                }
            }
        }
        if (check == nomines) {
            endtime = System.nanoTime();
            Component temporaryLostComponent = null;
            t.stop();
            JOptionPane.showMessageDialog(temporaryLostComponent, "Congratulations you won!!! It took you " + (int) ((endtime - starttime) / 1000000000) + " seconds!");
        }
    }

    public void scan(int x, int y) {
        for (int a = 0; a < 8; a++) {
            if (mines[x + 1 + deltax[a]][y + 1 + deltay[a]] == 3) {

            } else if ((perm[x + deltax[a]][y + deltay[a]] == 0) && (mines[x + 1 + deltax[a]][y + 1 + deltay[a]] == 0) && (guesses[x + deltax[a] + 1][y + deltay[a] + 1] == 0)) {
                if (b[x + deltax[a]][y + deltay[a]].isEnabled()) {
                    b[x + deltax[a]][y + deltay[a]].setText(" ");
                    b[x + deltax[a]][y + deltay[a]].setEnabled(false);
                    scan(x + deltax[a], y + deltay[a]);
                }
            } else if ((perm[x + deltax[a]][y + deltay[a]] != 0) && (mines[x + 1 + deltax[a]][y + 1 + deltay[a]] == 0) && (guesses[x + deltax[a] + 1][y + deltay[a] + 1] == 0)) {
                tmp = new Integer(perm[x + deltax[a]][y + deltay[a]]).toString();
                b[x + deltax[a]][y + deltay[a]].setText(Integer.toString(perm[x + deltax[a]][y + deltay[a]]));
                b[x + deltax[a]][y + deltay[a]].setEnabled(false);
            }
        }
    }

    public int perimcheck(int a, int y) {
        int minecount = 0;
        for (int x = 0; x < 8; x++) {
            if (mines[a + deltax[x] + 1][y + deltay[x] + 1] == 1) {
                minecount++;
            }
        }
        return minecount;
    }

    public void windowIconified(WindowEvent e) {

    }

    public static void main(String[] args) {
        new Buscaminas();
    }

    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            found = false;
            Object current = e.getSource();
            for (int y = 0; y < m; y++) {
                for (int x = 0; x < n; x++) {
                    JButton t = b[x][y];
                    if (t == current) {
                        row = x;
                        column = y;
                        found = true;
                    }
                }//end inner for
            }//end for
            if (!found) {
                System.out.println("didn't find the button, there was an error ");
                System.exit(-1);
            }
            if ((guesses[row + 1][column + 1] == 0) && (b[row][column].isEnabled())) {
                b[row][column].setText("x");
                restantes--;
                guesses[row + 1][column + 1] = 1;
                b[row][column].setBackground(Color.orange);
            } else if (guesses[row + 1][column + 1] == 1) {
                b[row][column].setText(" ");
                restantes++;
                guesses[row + 1][column + 1] = 0;
                b[row][column].setBackground(null);
            }
        }
        minasRestantes.setText(String.valueOf(restantes));
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub
    }
}//end class
