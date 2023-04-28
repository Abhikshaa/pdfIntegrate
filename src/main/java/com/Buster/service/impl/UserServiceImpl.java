package com.Buster.service.impl;

import com.Buster.entity.User;
import com.Buster.exception.ResourceNotFoundException;
import com.Buster.payload.UserDTO;
import com.Buster.repository.UserRepository;
import com.Buster.service.UserService;
import com.Buster.util.ExcelExporter;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

     private UserRepository userRepository;
     private ModelMapper modelMapper;

     public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
          this.userRepository = userRepository;
          this.modelMapper = modelMapper;
     }

     @Override
     public UserDTO saveUser(UserDTO userDTO) {

          User user = mapToEntity(userDTO);
          User save = userRepository.save(user);
          UserDTO dto = mapToDto(save);
          return dto;
     }

     @Override
     public UserDTO updateuser(long id, UserDTO userDTO) {
          User user = userRepository.findById(id).orElseThrow(
                  () -> new ResourceNotFoundException("USER", "ID", id));
          user.setName(userDTO.getName());
          user.setBody(userDTO.getBody());
          user.setEmail(userDTO.getEmail());
          User save = userRepository.save(user);
          return mapToDto(save);
     }

     @Override
     public Page<UserDTO> getAllUser(Pageable pageable) {
          Page<User> page = userRepository.findAll(pageable);
          List<UserDTO> collect = page.stream().map(this::mapToDto).collect(Collectors.toList());
          return new PageImpl<>(collect, pageable, page.getTotalElements());
     }

     @Override
     public UserDTO findById(long id) {
          User user = userRepository.findById(id).orElseThrow(
                  () -> new ResourceNotFoundException("USER", "ID", id));
          return mapToDto(user);
     }

     @Override
     public void deleteById(long id) {
          User user = userRepository.findById(id).orElseThrow(
                  () -> new ResourceNotFoundException("USER", "ID", id));

          userRepository.delete(user);
     }

     private UserDTO mapToDto(User save) {
          UserDTO dto = modelMapper.map(save, UserDTO.class);
          return dto;
     }

     private User mapToEntity(UserDTO userDTO) {
          User user = modelMapper.map(userDTO, User.class);
          return user;
     }

     @Override
     public InputStreamResource getUserAsExcel() {
          List<User> users = userRepository.findAll();
          try {
               return ExcelExporter.exportUsersToExcel(users);
          } catch (IOException e) {
               throw new RuntimeException("Failed to generate Excel file", e);
          }
     }
}