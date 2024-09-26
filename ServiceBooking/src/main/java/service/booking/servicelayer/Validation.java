package service.booking.servicelayer;

import service.booking.Registration.UserLoginDto;

public class Validation {

	public boolean validateLogin(UserLoginDto user,UserLoginDto userInfo) {
		
		if(!user.getEmail().equals(userInfo.getEmail()))
			return false;
		
		else if(!user.getUserName().equals(userInfo.getUserName()))
			return false;
		
		else if(!user.getPassWord().equals(userInfo.getPassWord()))
			return false;
		
		return true;
		
	}
	
}
