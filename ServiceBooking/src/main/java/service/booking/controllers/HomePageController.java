package service.booking.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import service.booking.DAO.UserDaoImpl;
import service.booking.Registration.UserBookDto;
import service.booking.Registration.UserIdDto;
import service.booking.Registration.UserMessageDto;
import service.booking.Registration.UserMessageSendDto;
import service.booking.Registration.UserServiceDto;

@Controller
public class HomePageController {

	@Autowired
	private UserDaoImpl userDaoImpl;
	
	@RequestMapping("/about-us")
	public String getAboutPage()
	{
		return "about-us";
	}
	
	@RequestMapping("/contact-us")
	public String getContactPage(HttpSession session,Model model,UserMessageDto user)
	{
		String email = (String) session.getAttribute("email");
		String userName = (String) session.getAttribute("userName");
		
		user.setEmail(email);
		user.setUserName(userName);
		user.setMessage("Enter Your Message");
		
		model.addAttribute("user",user);
		
		return "contact";
	}
	
	@RequestMapping("/contact-success")
	public String getContactSuccessPage(UserMessageSendDto userMessageSendDto,@RequestParam("email") String email)
	{
		
		System.out.println(email);
		
		UserIdDto userId = userDaoImpl.getUserId(email);
		
		userMessageSendDto.setUserId(userId.getId());
		
		userDaoImpl.insert(userMessageSendDto);
		
		return "contact-success";
	}
	
	@RequestMapping("/homepage")
	public String getHomePage()
	{
		return "homepage";
	}
	
	@RequestMapping("/services")
	public String getServicePage()
	{
		return "services";
	}
	
	@RequestMapping("/book-now")
	public String getBookPage(@RequestParam("Service") String serviceName,@ModelAttribute("UserBookDto") UserBookDto user,HttpSession session)
	{
		String email = (String) session.getAttribute("email");
		String userName = (String) session.getAttribute("userName");
		String issue = serviceName;
		String description = serviceName;
		
		if(email==null || userName==null)
		{
			return "landing-page";
		}
		
		user.setEmail(email);
		user.setUserName(userName);
		user.setIssue(issue);
		user.setDescription(description);
		
		return "book-form";
	}
	
	@RequestMapping("/process-book-form")
	public String getSuccessPage(@Valid @ModelAttribute("UserBookDto") UserBookDto user,BindingResult result)
	{
		if(result.hasErrors())
		{
			return "book-form";
		}
			
		UserIdDto userIdDto = userDaoImpl.getUserId(user.getEmail());
		
		userIdDto.setIssue(user.getIssue());
		
		userIdDto.setDescription(user.getDescription());
		
		userDaoImpl.insert(userIdDto);
		
		return "service-request-successful";
	}
	
	
	@RequestMapping("/service-now")
	public String getServiceNowPage(Model model,HttpSession session)
	{
		
		String email = (String) session.getAttribute("email");
		
		if(email==null)
		{
			return "landing-page";
		}		
		
		UserServiceDto userServiceDto = userDaoImpl.getUserServiceId(email);
		
		List<UserServiceDto> userServiceDetails = (ArrayList<UserServiceDto>) userDaoImpl.getUserServiceDetails(userServiceDto.getId());
		
		model.addAttribute("userServiceDetails",userServiceDetails);
		
		System.out.println(userServiceDetails);
		
		return "service-now";
	}
	
}
