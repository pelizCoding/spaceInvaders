
public class Navicella extends Elemento{
    
    public Navicella(int x, int y, int width, int height, String img){
        super(x, y, width, height, img);
    }

    public void movimento(int Yspostamento) {
		if(super.getY() + Yspostamento >= 60 && super.getY() + Yspostamento <= 645) {
			aumentaY(Yspostamento);
		}
	}


}
