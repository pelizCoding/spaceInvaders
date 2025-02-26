import javax.swing.ImageIcon;
import java.awt.Image;

public class Elemento{

    private int x;
    private int y;
    private int width;
    private int height;
    private Image icon; 

    public Elemento(int x, int y, int width, int height, String img){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.icon = new ImageIcon(img).getImage();
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    public Image getImage(){
        return this.icon;
    }

    public void setImage(String newIcon){
        this.icon = new ImageIcon(newIcon).getImage();
        this.icon.flush();
    }

    public void aumentaX(int incremento){
        this.x += incremento;
    }

    public void aumentaY(int incremento){
        this.y += incremento;
    }


}
