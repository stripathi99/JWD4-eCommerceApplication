package com.example.demo.controller;

import static com.example.demo.util.CreateMocksUtil.TEST_USERNAME;
import static com.example.demo.util.CreateMocksUtil.getMockItem;
import static com.example.demo.util.CreateMocksUtil.getMockUser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import com.example.demo.controllers.OrderController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

public class OrderControllerTest {

  @InjectMocks
  private OrderController orderController;

  @Mock
  private UserRepository userRepository;

  @Mock
  private ItemRepository itemRepository;

  @Mock
  private CartRepository cartRepository;

  @Mock
  private OrderRepository orderRepository;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void submit_happy() {
    Item item = getMockItem();
    User user = getMockUser();
    Cart cart = new Cart();
    cart.addItem(item);
    cart.setUser(user);
    user.setCart(cart);

    when(userRepository.findByUsername(anyString())).thenReturn(user);

    ResponseEntity<UserOrder> responseEntity =  orderController.submit(TEST_USERNAME);

    assertNotNull(responseEntity);
    assertEquals(OK, responseEntity.getStatusCode());
    assertNotNull(responseEntity.getBody());
  }

  @Test
  public void submit_no_user() {
    ResponseEntity<UserOrder> responseEntity =  orderController.submit(TEST_USERNAME);
    assertEquals(NOT_FOUND, responseEntity.getStatusCode());
    assertNull(responseEntity.getBody());
  }

  @Test
  public void get_orders_for_user_happy() {
    submit_happy();
    ResponseEntity<List<UserOrder>> responseEntity = orderController.getOrdersForUser(TEST_USERNAME);
    assertNotNull(responseEntity);
    assertEquals(OK, responseEntity.getStatusCode());
    assertNotNull(responseEntity.getBody());
    assertEquals(0, responseEntity.getBody().size());
  }

  @Test
  public void get_orders_for_no_user() {
    ResponseEntity<List<UserOrder>> responseEntity = orderController.getOrdersForUser(TEST_USERNAME);
    assertEquals(NOT_FOUND, responseEntity.getStatusCode());
    assertNull(responseEntity.getBody());
  }
}
