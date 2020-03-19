package Utilities;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author tanh2k2k
 */
public class FileConnection
{
    public String path = "sample";

    public String getPath()
    {
        return path;
    }
    
    public ArrayList<FileCustom> getItems()
    {
        ArrayList<FileCustom> listItem = new ArrayList<>();
        File file = new File(path);
        String[] ArrayItem = file.list();
        for (String item : ArrayItem)
        {
            int dot = item.lastIndexOf('.');
            String base = (dot == -1) ? item : item.substring(0, dot);
            String extension = (dot == -1) ? "fol" : item.substring(dot + 1);
            listItem.add(new FileCustom(base, extension));
        }
        return listItem;
    }
}
