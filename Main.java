
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

class Superficie extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final int delay = 10;
	private final int delayNemici = 3000;
	private boolean terminato = false;

	private ArrayList<Proiettile> listaProiettili;
	private ArrayList<Nemico> listaNemici;
	private ArrayList<Cuore> listaCuori;
	private ArrayList<Cuore> listaCuoriRotti;
	private ArrayList<Integer> listaPosCuoriRotti;

	private Navicella navicella;
	private Random cas;
	private int vite;
	private int punteggio;

	private Thread tCuori;
	private boolean[] keysPressed = new boolean[256];

	public Superficie() {
		creaNavicella();
	}

	private void doDrawing(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.drawImage(new ImageIcon("img/spazio.jpg").getImage(), 0, 0, 1200, 800, null);

		g2d.drawImage(navicella.getImage(), navicella.getX(), navicella.getY(), navicella.getWidth(),
				navicella.getHeight(), null);

		for (int i = 0; i < listaNemici.size(); i++) {
			g2d.drawImage(listaNemici.get(i).getImage(), listaNemici.get(i).getX() + 75, listaNemici.get(i).getY() + 48,
					listaNemici.get(i).getWidth(), listaNemici.get(i).getHeight(), null);
		}

		for (int i = 0; i < listaProiettili.size(); i++) {
			g2d.drawImage(listaProiettili.get(i).getImage(), listaProiettili.get(i).getX() + 75,
					listaProiettili.get(i).getY() + 48, listaProiettili.get(i).getWidth(),
					listaProiettili.get(i).getHeight(), null);
		}

		for (int i = 0; i < vite; i++) {
			g2d.drawImage(listaCuori.get(i).getImage(), listaCuori.get(i).getX(), listaCuori.get(i).getY(),
					listaCuori.get(i).getWidth(), listaCuori.get(i).getHeight(), null);
		}

		for (int i = 0; i < listaCuoriRotti.size(); i++) {
			g2d.drawImage(listaCuoriRotti.get(i).getImage(), listaPosCuoriRotti.get(i), listaCuoriRotti.get(i).getY(),
					listaCuoriRotti.get(i).getWidth(), listaCuoriRotti.get(i).getHeight(), null);
		}

		g2d.setColor(new Color(255, 255, 255));

		g2d.setFont(new Font("Microsoft YaHei", Font.PLAIN, 40));
		g2d.drawString(Integer.toString(punteggio), 1000, 70);

		if (terminato) {
			g2d.drawImage(new ImageIcon("img/game_over.png").getImage(), 320, 120, 500, 362, null);
			g2d.drawString("Score: " + Integer.toString(punteggio), 480, 500);
			g2d.drawString("Press R for Restart", 405, 550);

		}
	}

	public void paint(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}

	public void timer() {
		Timer timer = new Timer(delay, this);
		timer.start();
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				spara();
			}
		});
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP
						|| e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
					keysPressed[e.getKeyCode()] = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_R && terminato) {
					creaNavicella();
					terminato = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					spara();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP
						|| e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
					keysPressed[e.getKeyCode()] = false;
				}
			}
		});
		Thread t = new Thread() {
			public void run() {
				while (true) {

					for (int i = 0; i < 3; i++) {
						listaNemici.add(new Nemico(1100 + cas.nextInt(300), cas.nextInt(550), 70, 70, "img/alieno.gif"));
					}

					try {
						Thread.sleep(delayNemici);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};

		t.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (!terminato) {
			if (keysPressed[KeyEvent.VK_DOWN] || keysPressed[KeyEvent.VK_S]) {
				navicella.movimento(10);
			}
			if (keysPressed[KeyEvent.VK_UP] || keysPressed[KeyEvent.VK_W]) {
				navicella.movimento(-10);
			}

			if (keysPressed[KeyEvent.VK_SPACE]) {
				spara();
			}

			boolean colpito;

			for (int i = 0; i < listaProiettili.size(); i++) {
				colpito = false;
				for (int j = 0; j < listaNemici.size() && !colpito; j++) {
					if (listaProiettili.get(i).checkColpito(listaNemici.get(j))) {
						listaProiettili.remove(i);
						listaNemici.remove(j);
						punteggio += 10;
						colpito = true;
					}
				}
			}

			for (int i = 0; i < listaProiettili.size(); i++) {
				if (listaProiettili.get(i).getX() <= 1050) {
					listaProiettili.get(i).aumentaX(10);
				} else {
					listaProiettili.remove(i);
				}
			}

			for (int i = 0; i < listaNemici.size(); i++) {
				if (listaNemici.get(i).getX() >= 50) {
					listaNemici.get(i).aumentaX(-2);
				} else {
					if (vite > 0) {
						vite--;

						tCuori = new Thread() {
							public void run() {
								try {
									Thread.sleep(2300);

									if (listaCuoriRotti.size() > 0) {
										listaCuoriRotti.remove(0);
										listaPosCuoriRotti.remove(0);
									}

								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						};

						tCuori.start();

						listaPosCuoriRotti.add(listaCuori.get(vite).getX());
						listaCuori.get(vite).setImage("img/cuoreRotto" + (vite + 1) + ".gif");
						listaCuoriRotti.add(listaCuori.get(vite));
						listaCuori.remove(vite);
					}
					listaNemici.remove(i);
				}
			}

			if (vite == 0) {
				terminato = true;
			}
		}

		// System.out.println("nProiettili: " + listaProiettili.size());
		// System.out.println("nNemici: " + listaNemici.size());
		repaint();
	}

	private void spara() {
		listaProiettili.add(new Proiettile(navicella.getX(), navicella.getY(), 72, 32, "img/proiettile.png"));
	}

	public void creaNavicella() {
		listaProiettili = new ArrayList<Proiettile>();
		listaNemici = new ArrayList<Nemico>();
		listaCuori = new ArrayList<Cuore>();
		listaCuoriRotti = new ArrayList<Cuore>();
		listaPosCuoriRotti = new ArrayList<Integer>();

		vite = 3;
		punteggio = 0;
		cas = new Random();

		navicella = new Navicella(50, 300, 119, 122, "img/navicella.gif");

		for (int i = 0; i < vite; i++) {
			listaCuori.add(new Cuore(50 + i * 50, 0, 100, 100, "img/cuore.gif"));
		}

	}

}

public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Window w = new Window();
				w.setVisible(true);
			}
		});
	}
}
