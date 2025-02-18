package com.artTech.wisewallet.dto;

import com.artTech.wisewallet.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class UserDTO {

    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 50, message = "Nome deve ter entre 2 e 50 caracteres")
    private String name;

    @NotBlank(message = "Sobrenome é obrigatório")
    @Size(min = 2, max = 100, message = "Sobrenome deve ter entre 2 e 100 caracteres")
    private String lastName;

    @NotNull(message = "birthDate is required")
    private LocalDate birthDate;

    @NotBlank(message = "CPF/CNPJ é obrigatório")
    @Pattern(
            regexp = "^\\d{11}$|^\\d{14}$",
            message = "CPF deve ter 11 dígitos ou CNPJ 14 dígitos"
    )
    private String cpfCnpj;

    @Size(max = 100, message = "Profissão deve ter até 100 caracteres")
    private String profession;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;


    //    @NotBlank(message = "Senha é obrigatória")
//    @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres")
//    private String password;
    // @JsonIgnore // Ignora este campo na serialização JSON
    private String password;

    @Pattern(
            regexp = "^\\+?[1-9]\\d{1,14}$",
            message = "Número de telefone inválido"
    )
    private String phone;

    // Endereço
    @Size(max = 200)
    private String address;

    @Size(max = 50)
    private String complement;

    @Size(max = 10)
    private String number;

    @Size(max = 100)
    private String neighborhood;

    @Size(max = 100)
    private String city;

    @Size(max = 50)
    private String state;

    @Size(max = 50)
    private String country;

    @Size(max = 10)
    @Pattern(regexp = "\\d{5}-?\\d{3}", message = "CEP inválido")
    private String cep;

    // Construtor com campos obrigatórios
    public UserDTO(User user) {
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.cpfCnpj = cpfCnpj;
        this.email = email;
        this.password = password;
    }


    public UserDTO() {
    }

    // Getters e Setters
    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}