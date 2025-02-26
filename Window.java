import javax.swing.JFrame;

class Window extends JFrame {

	private static final long serialVersionUID = 1L;

	public Window() {
		initUI();
	}

	private void initUI() {
		Superficie s = new Superficie();
		s.setFocusable(true);
		s.requestFocusInWindow();
		add(s);
		setTitle("Progetto Navicella JAVA");
		setSize(1200, 800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		s.timer();
	}
}