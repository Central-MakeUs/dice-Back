package com.cmc.dice.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateGuestInfoRequest implements Serializable {
	String password;
	String name;
	String phone;
	String bankName;
	String accountNumber;
}