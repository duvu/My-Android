package mobile.myandroid.storage;

import java.io.File;
import java.util.List;

/**
 * Created by beou on 27/10/2015.
 */
public interface Storage {
    public List<File> getAllStorage();
    public File getExternalSDCard();
    public File getInternalSDCard();
}
