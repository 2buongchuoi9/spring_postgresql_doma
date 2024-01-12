package com.den.utils;

public final class _enum {
  public static enum RoleShopEnum {
    ADMIN,
    SHOP,
    USER,
    MOD;
  }

  public static enum AuthTypeEnum {
    LOCAL,
    FACEBOOK,
    GOOGLE,
  }

  public static enum StatusStudentEnum {
    ACTIVE(0),
    IN(1),
    OUT(2),
    END(3),
    OFF(4);

    public final int value;

    private StatusStudentEnum(int value) {
      this.value = value;
    }

  }

  // Phương thức kiểm tra chuỗi có phù hợp với enum không
  public static StatusStudentEnum getEnumFromString(String value) throws IllegalArgumentException {
    for (StatusStudentEnum status : StatusStudentEnum.values()) {
      if (status.name().equalsIgnoreCase(value)) {
        return status;
      }
    }
    throw new IllegalArgumentException("Invalid value: " + value);
  }

}