package service.booking.DAO;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import service.booking.Registration.UserDto;
import service.booking.Registration.UserIdDto;
import service.booking.Registration.UserIssueDto;
import service.booking.Registration.UserLoginDto;
import service.booking.Registration.UserMessageSendDto;
import service.booking.Registration.UserServiceDto;

@Repository
public class UserDaoImpl implements UserDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void insert(UserDto user) {
		
		
		String sql = "INSERT INTO USERS(id,email,userName,passWord) VALUES(?,?,?,?)";
		
		Object object[] = {user.getId(),user.getEmail(),user.getUserName(),user.getPassWord()};
		
		jdbcTemplate.update(sql,object);
	}
	
	@Override
	public void insert(UserIdDto user) {
		
		
		String sql = "INSERT INTO issues(userid,issue,description) VALUES(?,?,?)";
		
		Object object[] = {user.getId(),user.getIssue(),user.getDescription()};
		
		jdbcTemplate.update(sql,object);
	}
	
	@Override
	public void insert(UserMessageSendDto user) {
		
		
		String sql = "INSERT INTO messages(userid,message) VALUES(?,?)";
		
		Object object[] = {user.getUserId(),user.getMessage()};
		
		jdbcTemplate.update(sql,object);
	}

	@Override
	public UserLoginDto getUserDetails(String email) {
		
		String sql = "select * from USERS where email = ? ";
		
		UserLoginDto userLoginDto = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<UserLoginDto>(UserLoginDto.class),email);
		
		return userLoginDto;
		
	}
	
	@Override
	public UserLoginDto getAdminDetails(String email) {
		
		String sql = "select * from ausers where email = ? ";
		
		UserLoginDto userLoginDto = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<UserLoginDto>(UserLoginDto.class),email);
		
		return userLoginDto;
		
	}

	@Override
	public UserIdDto getUserId(String email) {
		
		
		String sql = "select id from USERS where email = ? ";
		
		UserIdDto userIdDto = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<UserIdDto>(UserIdDto.class),email);
		
		return userIdDto;
		
	}
	
	@Override
	public UserServiceDto getUserServiceId(String email) {
		
		
		String sql = "select id from USERS where email = ? ";
		
		UserServiceDto userServiceDto = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<UserServiceDto>(UserServiceDto.class),email);
		
		return userServiceDto;
		
	}
	
	@Override
	public List<UserServiceDto> getUserServiceDetails(String userId) {
		
		String sql = "select userid as id,issue,description,createdAt,status from issues where userid = ? ";
		
		List<UserServiceDto> user = jdbcTemplate.query(sql,new BeanPropertyRowMapper<UserServiceDto>(UserServiceDto.class),userId);
		
		return user;
		
	}

	@Override
	public List<UserIssueDto> getUserAllDetails() {
		
		String sql = "select i.id,u.email,u.userName,i.issue,i.description,i.createdAt,i.status from issues i INNER JOIN users u on i.userid = u.id";
		
		List<UserIssueDto> userIssueDto = jdbcTemplate.query(sql,new BeanPropertyRowMapper<UserIssueDto>(UserIssueDto.class));
		
		return userIssueDto;
	}
	
	@Override
	public List<UserIssueDto> getUserAllDetailsByCompletedStatus() {
		
		String sql = "select i.id,u.email,u.userName,i.issue,i.description,i.createdAt,i.status from issues i INNER JOIN users u on i.userid = u.id where i.status = ?";
		
		List<UserIssueDto> userIssueDto = jdbcTemplate.query(sql,new BeanPropertyRowMapper<UserIssueDto>(UserIssueDto.class),"completed");
		
		return userIssueDto;
	}
	
	@Override
	public List<UserIssueDto> getUserAllDetailsByPendingStatus() {
		
		String sql = "select i.id,u.email,u.userName,i.issue,i.description,i.createdAt,i.status from issues i INNER JOIN users u on i.userid = u.id where i.status = ?";
		
		List<UserIssueDto> userIssueDto = jdbcTemplate.query(sql,new BeanPropertyRowMapper<UserIssueDto>(UserIssueDto.class),"pending");
		
		return userIssueDto;
	}

	@Override
	public int getCount() {
		
		String sql = "select count(*) from issues";
		
		int count = jdbcTemplate.queryForObject(sql,Integer.class);
		
		return count;
	}

	@Override
	public int getCompletedCount() {
		
		String sql = "select count(*) from issues where status = ?";
		
		int count = jdbcTemplate.queryForObject(sql,Integer.class,"completed");
		
		return count;
	}

	@Override
	public int getPendingCount() {
		
		String sql = "select count(*) from issues where status = ?";
		
		int count = jdbcTemplate.queryForObject(sql,Integer.class,"pending");
		
		return count;
	}
	
	

}
