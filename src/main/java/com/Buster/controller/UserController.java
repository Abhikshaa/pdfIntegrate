package com.Buster.controller;

import com.Buster.payload.UserDTO;
import com.Buster.service.UserService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;

@RestController
@RequestMapping("/api/users")
public class UserController {

     private UserService userService;

     public UserController(UserService userService) {
          this.userService = userService;
     }
     @PostMapping
     @PreAuthorize("hasRole('USER')")
     public ResponseEntity<?> saveUser (@Valid @RequestBody UserDTO userDTO , BindingResult result){

     if(result.hasErrors()){

          return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
     }
          UserDTO dto = userService.saveUser(userDTO);

          return new ResponseEntity<>(dto, HttpStatus.CREATED);
     }
     @PutMapping("/{id}")
     public ResponseEntity<UserDTO> updateUser(@PathVariable("id") long id,
                                               @RequestBody UserDTO userDTO){

          UserDTO dto = userService.updateuser(id,userDTO);

          return new ResponseEntity<>(dto,HttpStatus.OK);
     }
     @GetMapping
     @PreAuthorize("hasRole('ADMIN')")
     public  ResponseEntity<Page<UserDTO>> getAllUser(@PageableDefault(size = 10,sort="id")Pageable pageable){

          Page<UserDTO> user = userService.getAllUser(pageable);

          return new ResponseEntity<>(user,HttpStatus.OK);
     }
     @GetMapping("/{id}")
     public ResponseEntity<UserDTO> getUser(@PathVariable("id") long id){

          UserDTO dto = userService.findById(id);
          return new ResponseEntity<>(dto,HttpStatus.OK);
     }
     @DeleteMapping("/{id}")
     public ResponseEntity<String> deleteUser(@PathVariable("id") long id){

          userService.deleteById(id);
          return new ResponseEntity<>("user deleted!!!",HttpStatus.OK);
     }
     //http://localhost:8080/api/users/download
     @GetMapping("/download")
     public ResponseEntity<InputStreamResource> downloadUsersAsExcel() {
          try {
               InputStreamResource stream = userService.getUserAsExcel();
               HttpHeaders headers = new HttpHeaders();
               headers.add("Content-Disposition", "attachment; filename=users.xlsx");
                       headers.add("Content-Type", "application/vnd.openxmlformatsofficedocument.spreadsheetml.sheet");
               return ResponseEntity.ok()
                       .headers(headers)
                       .body(stream);
          } catch (Exception e) {
               return
                       ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
          }
     }

}
