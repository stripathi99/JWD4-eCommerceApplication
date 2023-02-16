package com.example.demo.util;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.requests.ModifyCartRequest;
import java.math.BigDecimal;

public class CreateMocksUtil {

  public static final Long TEST_ID = 0L;
  public static final int TEST_QUANTITY = 11;
  public static final String TEST_PRICE = "9.99";
  public static final String TEST_USERNAME = "username";
  public static final String TEST_PASSWORD = "password";
  public static final String TEST_ITEM_NAME = "Test Item Name";
  public static final String TEST_ITEM_DESC = "Test Item Description.";
  public static final BigDecimal TEST_ITEM_PRICE = BigDecimal.valueOf(99.99);

  public static User getMockUser() {
    User mockUser = new User();

    mockUser.setId(TEST_ID);
    mockUser.setUsername(TEST_USERNAME);
    mockUser.setPassword(TEST_PASSWORD);

    return mockUser;
  }

  public static Item getMockItem() {
    Item item = new Item();

    item.setId(TEST_ID);
    item.setName(TEST_ITEM_NAME);
    item.setDescription(TEST_ITEM_DESC);
    item.setPrice(TEST_ITEM_PRICE);

    return item;
  }

  public static ModifyCartRequest getMockModifiedCart() {
    ModifyCartRequest modifyCartRequest = new ModifyCartRequest();

    modifyCartRequest.setUsername(TEST_USERNAME);
    modifyCartRequest.setItemId(TEST_ID);
    modifyCartRequest.setQuantity(TEST_QUANTITY);

    return modifyCartRequest;
  }
}
