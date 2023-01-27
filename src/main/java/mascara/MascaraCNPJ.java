package mascara;

import java.text.ParseException;
import javax.swing.text.MaskFormatter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MascaraCNPJ implements Mascara {

    @Override
    public void aplicarMascara(XSSFWorkbook workbook, Cell cell) {
        String cnpj = cell.getStringCellValue();
        try {
            MaskFormatter mask = new MaskFormatter("##.###.###/####-##");
            mask.setValueContainsLiteralCharacters(false);
            cell.setCellValue(mask.valueToString(cnpj));
        } catch (ParseException ex) {
            cell.setCellValue("");
        }
    }
}
