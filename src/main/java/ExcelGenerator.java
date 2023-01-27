import java.util.List;

public interface ExcelGenerator<T> {

    byte[] generateBytes(List<T>rows) throws Exception;

}
