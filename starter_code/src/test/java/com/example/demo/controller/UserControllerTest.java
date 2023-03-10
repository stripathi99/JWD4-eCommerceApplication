package com.example.demo.controller;

import static com.example.demo.util.CreateMocksUtil.TEST_ID;
import static com.example.demo.util.CreateMocksUtil.TEST_PASSWORD;
import static com.example.demo.util.CreateMocksUtil.TEST_USERNAME;
import static com.example.demo.util.CreateMocksUtil.getMockUser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserControllerTest {

  private final BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);

  @InjectMocks
  private UserController userController;

  @Mock
  private UserRepository userRepository;

  @Mock
  private CartRepository cartRepository;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void create_user_happy_path() {
    when(bCryptPasswordEncoder.encode(anyString())).thenReturn(TEST_PASSWORD);

    CreateUserRequest createUserRequest = new CreateUserRequest();
    createUserRequest.setUsername(TEST_USERNAME);
    createUserRequest.setPassword(TEST_PASSWORD);
    createUserRequest.setConfirmPassword(TEST_PASSWORD);

    ResponseEntity<User> responseEntity = userController.createUser(createUserRequest);
    User user = responseEntity.getBody();

    assertNotNull(responseEntity);
    assertEquals(OK, responseEntity.getStatusCode());
    assertEquals(0, user.getId());
    assertEquals(TEST_USERNAME, user.getUsername());
    assertEquals(TEST_PASSWORD, user.getPassword());
  }

  @Test
  public void find_by_id_found() {
    when(userRepository.findById(TEST_ID)).thenReturn(Optional.of(getMockUser()));
    ResponseEntity<User> responseEntity = userController.findById(TEST_ID);
    assertNotNull(responseEntity);
    assertEquals(OK, responseEntity.getStatusCode());
    assertEquals(TEST_ID.longValue(), responseEntity.getBody().getId());
  }

  @Test
  public void find_by_id_not_found() {
    ResponseEntity<User> responseEntity = userController.findById(TEST_ID);
    assertNotNull(responseEntity);
    assertEquals(NOT_FOUND, responseEntity.getStatusCode());
  }

  @Test
  public void find_by_username_found() {
    when(userRepository.findByUsername(TEST_USERNAME)).thenReturn(getMockUser());
    ResponseEntity<User> responseEntity = userController.findByUserName(TEST_USERNAME);
    assertNotNull(responseEntity);
    assertEquals(OK, responseEntity.getStatusCode());
    assertEquals(TEST_USERNAME, responseEntity.getBody().getUsername());
  }

  @Test
  public void find_by_username_not_found() {
    ResponseEntity<User> responseEntity = userController.findByUserName(TEST_USERNAME);
    assertNotNull(responseEntity);
    assertEquals(NOT_FOUND, responseEntity.getStatusCode());
  }
}
