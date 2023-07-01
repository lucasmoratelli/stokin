package com.example.semestral.model;

import javafx.fxml.FXML;

public class Notificacao {
    @FXML
    public int notificacaoID;
    @FXML
    public int produtoID;
    @FXML
    public String notificaTexto;

    public int getNotificacaoID() {
        return notificacaoID;
    }

    public void setNotificacaoID(int notificacaoID) {
        this.notificacaoID = notificacaoID;
    }

    public int getProdutoID() {
        return produtoID;
    }

    public void setProdutoID(int produtoID) {
        this.produtoID = produtoID;
    }

    public String getNotificaTexto() {
        return notificaTexto;
    }

    public void setNotificaTexto(String notificaTexto) {
        this.notificaTexto = notificaTexto;
    }
}
