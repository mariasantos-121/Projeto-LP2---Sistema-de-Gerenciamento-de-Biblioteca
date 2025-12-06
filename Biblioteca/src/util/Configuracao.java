package util;
import java.io.Serializable;

public class Configuracao implements Serializable {
    private String nomeExibicao;
    private String tema; // "CLARO" ou "ESCURO"

    public Configuracao() {
        // Valores padrão (para a primeira vez que rodar)
        this.nomeExibicao = "Administrador";
        this.tema = "CLARO";
    }

    public String getNomeExibicao() {
        return nomeExibicao;
    }

    public void setNomeExibicao(String nomeExibicao) {
        this.nomeExibicao = nomeExibicao;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }
}