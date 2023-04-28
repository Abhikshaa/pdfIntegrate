package com.Buster.service;

import com.Buster.payload.UserDTO;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
      UserDTO saveUser(UserDTO userDTO);

     UserDTO updateuser(long id,UserDTO userDTO);

     Page<UserDTO> getAllUser(Pageable pageable);

     UserDTO findById(long id);

     void deleteById(long id);
     InputStreamResource getUserAsExcel();
}
