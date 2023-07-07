package com.example.semestral.controller;

import com.example.semestral.HelloApplication;
import com.example.semestral.model.Fornecedor;
import com.example.semestral.model.Produto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class FornecedorModalController implements Initializable {

    public static Fornecedor fornecedor;

    @FXML
    TextField fornecedorIDField;

    @FXML
    TextField nomeFornecedorField;

    @FXML
    TextField CNPJField;

    @FXML
    TextField telefoneField;

    @FXML
    TextField siteField;

    @FXML
    TextField paisField;

    @FXML
    Label labelCheckYourFields;

    @FXML
    public void salvar() {

        Fornecedor novoFornecedor = new Fornecedor();
        try {
            if(!fornecedorIDField.getText().isBlank()) {
                novoFornecedor.fornecedorID = Integer.parseInt(fornecedorIDField.getText());
            }
            novoFornecedor.nomeFornecedor = nomeFornecedorField.getText();
            novoFornecedor.CNPJ = CNPJField.getText();
            novoFornecedor.telefone = telefoneField.getText();
            novoFornecedor.site = siteField.getText();
            novoFornecedor.pais = paisField.getText();
            fornecedor = novoFornecedor;
            HelloApplication.closeCurrentWindow();
        }catch (NumberFormatException e) {
            labelCheckYourFields.setText("Todos os campos são obrigatórios");
            System.out.println(e);
        }
    }
    @FXML
    public void cancelar() {
        HelloApplication.closeCurrentWindow();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Fornecedor fornecedorSelecionado = fornecedor;

        if (fornecedor != null) {
            fornecedorIDField.setText(Integer.toString(fornecedorSelecionado.fornecedorID));
            nomeFornecedorField.setText(fornecedorSelecionado.nomeFornecedor);
            CNPJField.setText(fornecedorSelecionado.CNPJ);
            telefoneField.setText(fornecedorSelecionado.telefone);
            siteField.setText(fornecedorSelecionado.site);
            paisField.setText(fornecedorSelecionado.pais);
        }
    }
}

