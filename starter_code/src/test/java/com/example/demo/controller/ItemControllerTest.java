package com.example.demo.controller;

import static com.example.demo.util.CreateMocksUtil.TEST_ID;
import static com.example.demo.util.CreateMocksUtil.TEST_ITEM_NAME;
import static com.example.demo.util.CreateMocksUtil.getMockItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import com.example.demo.controllers.ItemController;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

public class ItemControllerTest {

  @InjectMocks
  private ItemController itemController;

  @Mock
  private ItemRepository itemRepository;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void get_item_by_id_found() {
    when(itemRepository.findById(anyLong())).thenReturn(Optional.of(getMockItem()));

    ResponseEntity<Item> responseEntity = itemController.getItemById(TEST_ID);

    assertNotNull(responseEntity);
    assertEquals(OK, responseEntity.getStatusCode());
    assertEquals(TEST_ID, Objects.requireNonNull(responseEntity.getBody()).getId());
  }

  @Test
  public void get_item_by_id_not_found() {
    ResponseEntity<Item> responseEntity = itemController.getItemById(TEST_ID);
    assertNotNull(responseEntity);
    assertEquals(NOT_FOUND, responseEntity.getStatusCode());
  }

  @Test
  public void get_items_happy() {
    when(itemRepository.findAll()).thenReturn(Collections.singletonList(getMockItem()));
    ResponseEntity<List<Item>> responseEntity = itemController.getItems();
    assertNotNull(responseEntity.getBody());
    assertEquals(OK, responseEntity.getStatusCode());
    assertEquals(1, responseEntity.getBody().size());
  }

  @Test
  public void get_items_by_name_found() {
    when(itemRepository.findByName(anyString())).thenReturn(Collections.singletonList(getMockItem()));
    ResponseEntity<List<Item>> responseEntity = itemController.getItemsByName(TEST_ITEM_NAME);
    assertNotNull(responseEntity.getBody());
    assertEquals(OK, responseEntity.getStatusCode());
    assertEquals(1, responseEntity.getBody().size());
  }

  @Test
  public void get_items_by_name_not_found() {
    ResponseEntity<List<Item>> responseEntity = itemController.getItemsByName(TEST_ITEM_NAME);
    assertNotNull(responseEntity);
    assertEquals(NOT_FOUND, responseEntity.getStatusCode());
  }
}
