package mobile.myandroid.storage;

import java.io.File;
import java.util.List;

/**
 * Created by beou on 27/10/2015.
 */
public class GeneralStorage implements Storage {
    @Override
    public List<File> getAllStorage() {
        return null;
    }

    @Override
    public File getExternalSDCard() {
        return null;
    }

    @Override
    public File getInternalSDCard() {
        return null;
    }
}
