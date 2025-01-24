package com.cmc.dice.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateHostInfoRequest implements Serializable {
	String name;
	String phone;
	String password;
	String bankName;
	String accountNumber;
}