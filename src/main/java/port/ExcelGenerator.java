package port;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface ExcelGenerator<T> {

    byte[] generateBytes(List<T> rows) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException,
        InstantiationException, IllegalAccessException, IOException;

}
