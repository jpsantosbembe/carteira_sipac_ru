package com.joaobembe.carteiraru;

import java.util.List;

public class SliderItem {

    private List<String> pratoPrincipal;
    private List<String> segundaOpcao;
    private List<String> acompanhamentos;
    private List<String> guarnicao;
    private List<String> sobremesa;
    private List<String> suco;

    public SliderItem(List<String> pratoPrincipal, List<String> segundaOpcao, List<String> acompanhamentos, List<String> guarnicao, List<String> sobremesa, List<String> suco) {
        this.pratoPrincipal = pratoPrincipal;
        this.segundaOpcao = segundaOpcao;
        this.acompanhamentos = acompanhamentos;
        this.guarnicao = guarnicao;
        this.sobremesa = sobremesa;
        this.suco = suco;
    }

    public List<String> getPratoPrincipal() {
        return pratoPrincipal;
    }

    public void setPratoPrincipal(List<String> pratoPrincipal) {
        this.pratoPrincipal = pratoPrincipal;
    }

    public List<String> getSegundaOpcao() {
        return segundaOpcao;
    }

    public void setSegundaOpcao(List<String> segundaOpcao) {
        this.segundaOpcao = segundaOpcao;
    }

    public List<String> getAcompanhamentos() {
        return acompanhamentos;
    }

    public void setAcompanhamentos(List<String> acompanhamentos) {
        this.acompanhamentos = acompanhamentos;
    }

    public List<String> getGuarnicao() {
        return guarnicao;
    }

    public void setGuarnicao(List<String> guarnicao) {
        this.guarnicao = guarnicao;
    }

    public List<String> getSobremesa() {
        return sobremesa;
    }

    public void setSobremesa(List<String> sobremesa) {
        this.sobremesa = sobremesa;
    }

    public List<String> getSuco() {
        return suco;
    }

    public void setSuco(List<String> suco) {
        this.suco = suco;
    }
}
