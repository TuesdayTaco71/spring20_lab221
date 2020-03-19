package Utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tanh2k2k
 */
public class Validation
{   
    public boolean checkSpecialChars(String s)
    {
        if(s.matches("^[a-zA-Z]*$"))
            return false;
        return true;
    }
    
    public boolean checkLength(String s, int length)
    {
        if(s.length() > length)
            return false;
        return true;
    }
    
    public boolean checkValidDate(String inputDate)
    {
        try
        {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY");
            format.setLenient(false);
            Date date = format.parse(inputDate);
        } catch (ParseException ex)
        {
            Logger.getLogger(Validation.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public boolean checkNumericString(String s)
    {
        if(s.matches("[0-9]+"))
            return true;
        return false;
    }
    
    
}
