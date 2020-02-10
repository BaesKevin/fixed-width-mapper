package be.kevinbaes.encoded_string_mapper.sample;


import be.kevinbaes.encoded_string_mapper.annotation.StringEncodedValue;

public class Invoice {

  @StringEncodedValue
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
