package GUI;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author tanh2k2k
 */
public class ImageTool
{   
    public static ImageIcon setImage(String path)
    {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage();
        int resizeWidth = resizeImage(icon.getIconWidth(), icon.getIconHeight(), 415);
        Image newImg;
        if (resizeWidth > 698)
        {
            int resizeHeight = resizeImage(icon.getIconHeight(), icon.getIconWidth(), 698);
            newImg = img.getScaledInstance(698, resizeHeight, Image.SCALE_SMOOTH);
        } else
        {
            newImg = img.getScaledInstance(resizeWidth, 415, Image.SCALE_SMOOTH);
        }
        ImageIcon newImc = new ImageIcon(newImg);
        return newImc;
    }
    
    private static int resizeImage(int x, int y, int z)
    {
        double i = (double) y / z;
        double j = 1 / i;
        double k = x * j;
        return (int) k;
    }
}
