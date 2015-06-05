package at.skert.swe.ue6.utils;

public class Validator {
  public boolean isDoubleValid(String doubleToCheck) {
    try {
      Double.valueOf(doubleToCheck.replace(',', '.'));
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }
}
