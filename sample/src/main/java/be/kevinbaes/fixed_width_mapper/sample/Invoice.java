package be.kevinbaes.fixed_width_mapper.sample;


import be.kevinbaes.fixed_width_mapper.annotation.StringEncoded;
import be.kevinbaes.fixed_width_mapper.annotation.StringEncodedValue;

@StringEncoded
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
