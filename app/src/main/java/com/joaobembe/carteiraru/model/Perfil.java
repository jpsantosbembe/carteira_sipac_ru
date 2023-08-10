package com.joaobembe.carteiraru.model;

public class Perfil {
    private String nomeCompleto;
    private String tipoDeVinculo;
    private String matricula;
    private String situacaoDoVinculo;
    private String URLFoto;
    public Perfil(String nomeCompleto, String tipoDeVinculo, String matricula, String situacaoDoVinculo, String URLFoto) {
        this.nomeCompleto = nomeCompleto;
        this.tipoDeVinculo = tipoDeVinculo;
        this.matricula = matricula;
        this.situacaoDoVinculo = situacaoDoVinculo;
        this.URLFoto = URLFoto;
    }
    public String getNomeCompleto() {
        return nomeCompleto;
    }
    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }
    public String getTipoDeVinculo() {
        return tipoDeVinculo;
    }
    public void setTipoDeVinculo(String tipoDeVinculo) {
        this.tipoDeVinculo = tipoDeVinculo;
    }
    public String getSituacaoDoVinculo() {
        return situacaoDoVinculo;
    }
    public void setSituacaoDoVinculo(String situacaoDoVinculo) {
        this.situacaoDoVinculo = situacaoDoVinculo;
    }
    public String getURLFoto() {
        return URLFoto;
    }
    public void setURLFoto(String URLFoto) {
        this.URLFoto = URLFoto;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}
