package com.artTech.wisewallet.service;

import com.artTech.wisewallet.dto.UserDTO;
import com.artTech.wisewallet.model.User;
import com.artTech.wisewallet.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDTO createUser(UserDTO userDTO) {
        // Verifica se email ou CPF/CNPJ já existem
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado!");
        }

        if (userRepository.findByCpfCnpj(userDTO.getCpfCnpj()).isPresent()) {
            throw new RuntimeException("CPF/CNPJ já cadastrado!");
        }

        if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            throw new RuntimeException("Senha não pode ser vazia ou nula");
        }
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);

        UserDTO savedUserDTO = new UserDTO();
        BeanUtils.copyProperties(savedUser, savedUserDTO);
        return savedUserDTO;
    }
}
