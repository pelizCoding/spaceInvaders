import java.awt.Rectangle;

public class Proiettile extends Elemento{

    public Proiettile(int x, int y, int width, int height, String img) {
		super(x, y, width, height, img);
	}

    public boolean checkColpito(Nemico n) {
		
		Rectangle RecProiettile = new Rectangle(this.getX(), this.getY(), this.getWidth()-10, this.getHeight()-10);
		Rectangle RecNemico = new Rectangle(n.getX(), n.getY(), n.getWidth(), n.getHeight());
		
	    return RecProiettile.intersects(RecNemico);
	}

}
