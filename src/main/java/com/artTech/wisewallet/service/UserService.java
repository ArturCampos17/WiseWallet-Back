package com.artTech.wisewallet.service;

import com.artTech.wisewallet.dto.UserDTO;
import com.artTech.wisewallet.model.User;
import com.artTech.wisewallet.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
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


    public UserDTO getAuthenticatedUser(String email) {
        System.out.println("Buscando usuário pelo email: " + email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        userDTO.setPassword(null);
        return userDTO;
    }

    public UserDTO updateAuthenticatedUser(UserDTO userDTO, HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Atualiza os campos permitidos
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());

        User updatedUser = userRepository.save(user);

        UserDTO updatedUserDTO = new UserDTO();
        BeanUtils.copyProperties(updatedUser, updatedUserDTO);
        return updatedUserDTO;
    }

}
