package com.artTech.wisewallet.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoryDTO {

    private Long id;

    @NotBlank(message = "O nome da categoria é obrigatório")
    private String name;


    private Number codigo;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Number getCodigo() {
        return codigo;
    }

    public void setCodigo(Number codigo) {
        this.codigo = codigo;
    }
}