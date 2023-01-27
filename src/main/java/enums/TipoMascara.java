package enums;

import mascara.Mascara;
import mascara.MascaraAdapter;
import mascara.MascaraCNPJ;
import mascara.MascaraDinheiro;
import mascara.MascaraInteiro;

public enum TipoMascara implements MascaraAdapter {

    CNPJ() {
        @Override
        public Mascara instance() {
            return new MascaraCNPJ();
        }
    },
    PERCENTUAL() {
        @Override
        public Mascara instance() {
            return (value, cell) -> {
            };
        }
    },
    DINHEIRO() {
        @Override
        public Mascara instance() {
            return new MascaraDinheiro();
        }
    },
    INTEIRO() {
        @Override
        public Mascara instance() {
            return new MascaraInteiro();
        }
    },
    PADRAO() {
        @Override
        public Mascara instance() {
            return (value, cell) -> {
            };
        }
    };

    TipoMascara() {

    }
}
