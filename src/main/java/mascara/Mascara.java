package mascara;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public interface Mascara {

    void aplicarMascara(XSSFWorkbook workbook, Cell cell);

}
