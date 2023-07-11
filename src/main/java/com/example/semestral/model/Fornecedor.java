package com.example.semestral.model;

import javafx.fxml.FXML;

public class Fornecedor {



    @FXML
    public int fornecedorID;

    @FXML
    public String nomeFornecedor;

    @FXML
    public String CNPJ;

    @FXML
    public String telefone;

    @FXML
    public String site;

    @FXML
    public String pais;

//----------------------------------------------------------------//
    public int getFornecedorID() {
        return fornecedorID;
    }

    public void setFornecedorID(int fornecedorID) {
        this.fornecedorID = fornecedorID;
    }

    public String getNomeFornecedor() {
        return nomeFornecedor;
    }

    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}
