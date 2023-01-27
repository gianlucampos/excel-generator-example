package enums;

import java.math.BigDecimal;
import java.util.Calendar;

public enum TipoColuna {

    TEXTO(String.class),
    NUMERO(BigDecimal.class),
    INTEIRO(Integer.class),
    DATA(Calendar.class);

    private Class value;

    TipoColuna(Class value) {
        this.value = value;
    }

    public Class getValue() {
        return value;
    }
}
