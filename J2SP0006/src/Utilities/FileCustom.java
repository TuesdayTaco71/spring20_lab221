package Utilities;

/**
 *
 * @author tanh2k2k
 */
public class FileCustom
{
    private String base;
    private String extension;

    public FileCustom(String base, String extension)
    {
        this.base = base;
        this.extension = extension;
    }

    public String getBase()
    {
        return base;
    }

    public String getExtension()
    {
        return extension;
    }
}