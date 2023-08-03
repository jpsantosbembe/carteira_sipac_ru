package com.joaobembe.carteiraru.controller;

import com.joaobembe.carteiraru.model.Carteira;
import com.joaobembe.carteiraru.model.Credenciais;
import com.joaobembe.carteiraru.model.HistoricoTransacoes;
import com.joaobembe.carteiraru.model.Perfil;
import com.joaobembe.carteiraru.model.Usuario;

public class ControladorUsuario {
    public void criarNovoUsuario(Perfil perfil, Carteira carteira, Credenciais credenciais, HistoricoTransacoes historicoTransacoes) {
        Usuario.getInstance(perfil, carteira, credenciais, historicoTransacoes);
    }
    public Usuario obterUsuario() {
        return Usuario.getInstance(null, null, null, null);
    }
    public String consultarNomeUsuario (Usuario usuario) {
        return usuario.getPerfil().getNomeCompleto ();
    }
    public String consultarTipoDeVinculo (Usuario usuario) {
        return usuario.getPerfil().getTipoDeVinculo();
    }
    public String consultarSituacaoDoVinculo (Usuario usuario) {
        return usuario.getPerfil().getSituacaoDoVinculo();
    }
    public String consultarURLFoto (Usuario usuario) {
        return usuario.getPerfil().getURLFoto ();
    }
    public String consultarCodigoCarteira (Usuario usuario) {
        return usuario.getCarteira().getCodigo();
    }
    public double consultarSaldoCarteira (Usuario usuario) {
        return usuario.getCarteira ().getSaldo ();
    }
    public String consultarStrQRCodeCarteira (Usuario usuario) {
        return usuario.getCarteira ().getStrQRCode ();
    }
    public void atualizarSaldoCarteira (Usuario usuario, int novoSaldo) {
        usuario.getCarteira ().setSaldo (novoSaldo);
    }
}
