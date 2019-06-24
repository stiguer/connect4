package com.example.connect4.partida;

public class PartidaGuardada {
    private String alias;
    private String data;
    private int mida;
    private String control;
    private String temps;
    private String resultat;

    public PartidaGuardada(String alias, String data, int mida, String control, String temps, String resultat) {
        this.alias = alias;
        this.data = data;
        this.mida = mida;
        this.control = control;
        this.temps = temps;
        this.resultat = resultat;
    }
    public PartidaGuardada(){}

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getMida() {
        return mida;
    }

    public void setMida(int mida) {
        this.mida = mida;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public String getTemps() {
        return temps;
    }

    public void setTemps(String temps) {
        this.temps = temps;
    }

    public String getResultat() {
        return resultat;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }
}
