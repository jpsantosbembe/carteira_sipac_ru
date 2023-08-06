package com.joaobembe.carteiraru;

public class Item {
    String data;
    String hora;
    String operacao;
    String creditoGerados;
    String saldoAnterior;
    String saldoAtual;

    public Item(String data, String hora, String operacao, String creditoGerados, String saldoAnterior, String saldoAtual) {
        this.data = data;
        this.hora = hora;
        this.operacao = operacao;
        this.creditoGerados = creditoGerados;
        this.saldoAnterior = saldoAnterior;
        this.saldoAtual = saldoAtual;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public String getCreditoGerados() {
        return creditoGerados;
    }

    public void setCreditoGerados(String creditoGerados) {
        this.creditoGerados = creditoGerados;
    }

    public String getSaldoAnterior() {
        return saldoAnterior;
    }

    public void setSaldoAnterior(String saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }

    public String getSaldoAtual() {
        return saldoAtual;
    }

    public void setSaldoAtual(String saldoAtual) {
        this.saldoAtual = saldoAtual;
    }
}
