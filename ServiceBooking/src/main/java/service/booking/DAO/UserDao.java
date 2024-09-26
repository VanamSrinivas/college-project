package service.booking.DAO;

import java.util.List;

import service.booking.Registration.UserDto;
import service.booking.Registration.UserIdDto;
import service.booking.Registration.UserIssueDto;
import service.booking.Registration.UserLoginDto;
import service.booking.Registration.UserMessageSendDto;
import service.booking.Registration.UserServiceDto;

public interface UserDao {
	
	void insert(UserDto user);
	
	void insert(UserIdDto user);
	
	void insert(UserMessageSendDto user);

	UserLoginDto getUserDetails(String email);
	
	List<UserIssueDto> getUserAllDetails();
	
	UserLoginDto getAdminDetails(String email);
	
	UserIdDto getUserId(String email);
	
	UserServiceDto getUserServiceId(String email);
	
	List<UserServiceDto> getUserServiceDetails(String userId);
	
	List<UserIssueDto> getUserAllDetailsByCompletedStatus();
	
	List<UserIssueDto> getUserAllDetailsByPendingStatus();
	
	int getCount();
	
	int getCompletedCount();
	
	int getPendingCount();
}
