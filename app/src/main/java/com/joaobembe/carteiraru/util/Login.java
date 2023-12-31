package com.joaobembe.carteiraru.util;

import com.joaobembe.carteiraru.controller.ControladorUsuario;
import com.joaobembe.carteiraru.model.*;
import exception.ExcecaoErroDeConectividade;
import exception.ExcecaoUsuarioSenhaInvalido;
import com.joaobembe.carteiraru.helper.*;
import okhttp3.Response;
import java.io.IOException;
import java.util.Objects;

public class Login{
    public void fazerLogin(Credenciais credenciais) throws ExcecaoUsuarioSenhaInvalido, ExcecaoErroDeConectividade {

        String cookie = obterCookie();
        autenticarUsuario(credenciais, cookie);
        String strResponseBody1 = obterPaginaSaldoCartaoRU(cookie);
        String strResponseBody2 = obterPaginaPerfilUsuario(cookie);

        String nomeCompleto;
        nomeCompleto = AnalisadorRegex.localizarOcorrencia(ColecaoRegex.NOME_COMPLETO, strResponseBody1);
        String matricula;
        matricula = AnalisadorRegex.localizarOcorrencia(ColecaoRegex.MATRICULA, strResponseBody2).replace(" ", "");
        String codigo;
        codigo = AnalisadorRegex.localizarOcorrencia(ColecaoRegex.CODIGO, strResponseBody1);
        String situacaoDoVinculo;
        situacaoDoVinculo = AnalisadorRegex.localizarOcorrencia(ColecaoRegex.SITUACAO, strResponseBody1);
        String tipoDeVinculo;
        tipoDeVinculo = AnalisadorRegex.localizarOcorrencia(ColecaoRegex.TIPO_DE_VINCULO, strResponseBody1);
        int saldo;
        saldo = Integer.parseInt(Objects.requireNonNull(AnalisadorRegex.localizarOcorrencia(ColecaoRegex.SALDO, strResponseBody1)));
        String strQRCode;
        strQRCode = AnalisadorRegex.localizarOcorrencia(ColecaoRegex.STRING_QRCODE, strResponseBody1);
        String URLPerfil;
        URLPerfil = AnalisadorRegex.localizarOcorrencia(ColecaoRegex.FOTO_DE_PERFIL, strResponseBody2);
        HistoricoTransacoes historico;
        historico = AnalisadorRegex.localizarTransacoes(strResponseBody1);


        new ControladorUsuario().criarNovoUsuario(
                new Perfil(nomeCompleto, tipoDeVinculo, matricula, situacaoDoVinculo, URLPerfil),
                new Carteira(codigo, saldo, strQRCode),
                new Credenciais(credenciais.getUsuario(), credenciais.getSenha()),
                historico
        );
    }
    private String obterCookie() throws ExcecaoErroDeConectividade{
        try {
            ServicoHttp servicoHttp = new ServicoHttp();
            try (Response response = servicoHttp.fazerRequisicaoHttpGET(Endpoints.PAGINA_INICIAL_SIPAC)) {
                int index = Objects.requireNonNull(response.headers().get("Set-Cookie")).indexOf(";");
                return Objects.requireNonNull(response.headers().get("Set-Cookie")).substring(0, index);
            }
        } catch (IOException e) {
            throw new ExcecaoErroDeConectividade();
        }

    }
    private void autenticarUsuario(Credenciais credenciais, String cookie) throws ExcecaoErroDeConectividade, ExcecaoUsuarioSenhaInvalido {
        try {
            ServicoHttp servicoHttp = new ServicoHttp();
            String respostaSipac = servicoHttp.fazerRequisicaoHttpPOST(
                    Endpoints.LOGON_SIPAC + cookie.toLowerCase(),
                    "width=1920&height=1080&login=" + credenciais.getUsuario() + "&senha=" + credenciais.getSenha(),
                    cookie);
            if (AnalisadorRegex.localizarOcorrencia(ColecaoRegex.USUARIO_SENHA_INVALIDO, respostaSipac) != null) {
                throw new ExcecaoUsuarioSenhaInvalido();
            }
        } catch (IOException e) {
            throw new ExcecaoErroDeConectividade();
        }

    }
    private String obterPaginaSaldoCartaoRU(String cookie) throws ExcecaoErroDeConectividade {
        try {
            ServicoHttp servicoHttp = new ServicoHttp();
            return servicoHttp.fazerRequisicaoHttpPOST(Endpoints.SALDO_RU_SIPAC,
                    "formmenuadm=formmenuadm&jscook_action=formmenuadm_menuaaluno_menu%3AA%5D%23%7BsaldoCartao.iniciar%7D&javax.faces.ViewState=j_id1",
                    cookie);
        } catch (IOException e) {
            throw new ExcecaoErroDeConectividade();
        }

    }
    private String obterPaginaPerfilUsuario(String cookie) throws ExcecaoErroDeConectividade {
        try {
            ServicoHttp servicoHttp = new ServicoHttp();
            return servicoHttp.fazerRequisicaoHttpGET(Endpoints.PORTAL_DO_ALUNO_SIPAC, cookie);
        } catch (IOException e) {
            throw new ExcecaoErroDeConectividade();
        }

    }
}
