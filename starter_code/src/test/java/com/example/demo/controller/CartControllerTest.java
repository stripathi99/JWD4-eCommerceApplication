package com.example.demo.controller;

import static com.example.demo.util.CreateMocksUtil.getMockItem;
import static com.example.demo.util.CreateMocksUtil.getMockModifiedCart;
import static com.example.demo.util.CreateMocksUtil.getMockUser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import com.example.demo.controllers.CartController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

public class CartControllerTest {

  @InjectMocks
  private CartController cartController;

  @Mock
  private UserRepository userRepository;

  @Mock
  private ItemRepository itemRepository;

  @Mock
  private CartRepository cartRepository;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void add_to_cart_happy() {
    Item item = getMockItem();
    User user = getMockUser();
    Cart cart = new Cart();
    cart.addItem(item);
    cart.setUser(user);
    user.setCart(cart);
    when(userRepository.findByUsername(anyString())).thenReturn(user);
    when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
    ResponseEntity<Cart> responseEntity = cartController.addTocart(getMockModifiedCart());
    assertNotNull(responseEntity);
    assertEquals(OK, responseEntity.getStatusCode());
    assertNotNull(responseEntity.getBody());
  }

  @Test
  public void add_to_cart_no_user() {
    ResponseEntity<Cart> responseEntity = cartController.addTocart(getMockModifiedCart());
    assertEquals(NOT_FOUND, responseEntity.getStatusCode());
    assertNull(responseEntity.getBody());
  }

  @Test
  public void add_to_cart_no_item() {
    when(userRepository.findByUsername(anyString())).thenReturn(getMockUser());
    when(itemRepository.findById(anyLong())).thenReturn(Optional.empty());
    ResponseEntity<Cart> responseEntity = cartController.addTocart(getMockModifiedCart());
    assertEquals(NOT_FOUND, responseEntity.getStatusCode());
    assertNull(responseEntity.getBody());
  }

  @Test
  public void remove_from_cart_happy() {
    Item item = getMockItem();
    User user = getMockUser();
    Cart cart = new Cart();
    cart.addItem(item);
    cart.setUser(user);
    user.setCart(cart);
    when(userRepository.findByUsername(anyString())).thenReturn(user);
    when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
    ResponseEntity<Cart> responseEntity = cartController.removeFromcart(getMockModifiedCart());
    assertNotNull(responseEntity);
    assertEquals(OK, responseEntity.getStatusCode());
    assertNotNull(responseEntity.getBody());
  }

  @Test
  public void remove_from_cart_no_user() {
    ResponseEntity<Cart> responseEntity = cartController.removeFromcart(getMockModifiedCart());
    assertEquals(NOT_FOUND, responseEntity.getStatusCode());
    assertNull(responseEntity.getBody());
  }

  @Test
  public void remove_from_cart_no_item() {
    when(userRepository.findByUsername(anyString())).thenReturn(getMockUser());
    when(itemRepository.findById(anyLong())).thenReturn(Optional.empty());
    ResponseEntity<Cart> responseEntity = cartController.removeFromcart(getMockModifiedCart());
    assertEquals(NOT_FOUND, responseEntity.getStatusCode());
    assertNull(responseEntity.getBody());
  }
}
