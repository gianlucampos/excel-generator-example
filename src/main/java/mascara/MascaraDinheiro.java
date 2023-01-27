package mascara;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MascaraDinheiro implements Mascara {

    @Override
    public void aplicarMascara(XSSFWorkbook workbook, Cell cell) {

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat((short) 8);
        cell.setCellStyle(cellStyle);
    }
}
