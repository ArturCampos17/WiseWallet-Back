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


    public UserDTO updateAuthenticatedUser(UserDTO userDTO, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (userDTO.getName() != null) user.setName(userDTO.getName());
        if (userDTO.getLastName() != null) user.setLastName(userDTO.getLastName());
        if (userDTO.getBirthDate() != null) user.setBirthDate(userDTO.getBirthDate());
        if (userDTO.getProfession() != null) user.setProfession(userDTO.getProfession());
        if (userDTO.getPassword() != null) user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        if (userDTO.getPhone() != null) user.setPhone(userDTO.getPhone());
        if (userDTO.getAddress() != null) user.setAddress(userDTO.getAddress());
        if (userDTO.getComplement() != null) user.setComplement(userDTO.getComplement());
        if (userDTO.getNumber() != null) user.setNumber(userDTO.getNumber());
        if (userDTO.getNeighborhood() != null) user.setNeighborhood(userDTO.getNeighborhood());
        if (userDTO.getCity() != null) user.setCity(userDTO.getCity());
        if (userDTO.getState() != null) user.setState(userDTO.getState());
        if (userDTO.getCountry() != null) user.setCountry(userDTO.getCountry());

        User updatedUser = userRepository.save(user);
        return convertToDTO(updatedUser);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(user, dto);
        dto.setPassword(null);
        return dto;
    }
}

