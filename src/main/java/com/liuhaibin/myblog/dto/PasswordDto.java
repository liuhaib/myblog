package com.liuhaibin.myblog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordDto {
   private  Long id;
 //  private String  oldPassword;
   private String newPassword;
   private String confirmPassword;
}
