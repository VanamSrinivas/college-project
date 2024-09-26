package service.booking.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import service.booking.DAO.UserDaoImpl;
import service.booking.Registration.UserEmailDto;
import service.booking.Registration.UserIssueDto;
import service.booking.Registration.UserLoginDto;
import service.booking.servicelayer.SendMessage;

@Controller
public class EHomePageController {

	@Autowired
	private UserDaoImpl userDaoImpl;
	
	@Autowired
	private SendMessage message;
	
	@RequestMapping("/e-homepage")
	public String getHomePage(Model model)
	{
		
		return "e-homepage";
	}
	
	@RequestMapping("/view-all-issues")
	public String getAllIssue(Model model)
	{
		
		List<UserIssueDto> users = userDaoImpl.getUserAllDetails();
		
		model.addAttribute("users",users);
		
		return "issues";
	}
	
	@RequestMapping("/view-completed-issues")
	public String getCompletedIssue(Model model)
	{
		List<UserIssueDto> users = userDaoImpl.getUserAllDetailsByCompletedStatus();
		
		model.addAttribute("users",users);
		
		return "issues";
	}
	
	@RequestMapping("/view-pending-issues")
	public String getPendingIssue(Model model)
	{
		
		List<UserIssueDto> users = userDaoImpl.getUserAllDetailsByPendingStatus();
		
		model.addAttribute("users",users);
		
		return "issues";
	}
	
	@RequestMapping("/send-email")
	public String getEmailPage()
	{
	
		return "email";
	}
	
	@RequestMapping("/send-email-success")
	public String getEmailSuccessPage(UserEmailDto user)
	{
		
		message.send(user);
		
		return "email-successful";
	}
	
	@RequestMapping("/profile-page")
	public String getPerformancePage(Model model,HttpSession session)
	{
		String email = (String) session.getAttribute("email");
		
		UserLoginDto user = userDaoImpl.getUserDetails(email);
		
		model.addAttribute("users",user);
		
		return "profile";
	}
	
	@RequestMapping("/edit-page")
	public String getEditPage()
	{
		
		return "editpage";
	}

}
