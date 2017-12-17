package com.mt.app.store;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@DynamoDBTable(tableName = "Store")
public class Store {

  private String name;
  private String address;
  private String phoneNumber;

  @DynamoDBHashKey(attributeName = "Name")
  @NotNull(message = "Name must not be empty")
  public String getName() {

    return name;
  }

  public void setName(String name) {

    this.name = name;
  }

  public Store withName(String name) {

    setName(name);
    return this;
  }

  @DynamoDBAttribute(attributeName = "Address")
  public String getAddress() {

    return address;
  }

  public void setAddress(String address) {

    this.address = address;
  }

  public Store withAddress(String address) {

    setAddress(address);
    return this;
  }

  @DynamoDBAttribute(attributeName = "PhoneNumber")
  public String getPhoneNumber() {

    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {

    this.phoneNumber = phoneNumber;
  }

  public Store withPhoneNumber(String phoneNumber) {

    setPhoneNumber(phoneNumber);
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Store store = (Store) o;
    return Objects.equals(getName(), store.getName()) &&
        Objects.equals(getAddress(), store.getAddress()) &&
        Objects.equals(getPhoneNumber(), store.getPhoneNumber());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getAddress(), getPhoneNumber());
  }
}
