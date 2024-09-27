package service.booking.controllers;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.qos.logback.classic.Logger;
import jakarta.validation.Valid;
import service.booking.DAO.UserDaoImpl;
import service.booking.Registration.UserDto;
import service.booking.Registration.UserEmailDto;
import service.booking.Registration.UserLoginDto;
import service.booking.servicelayer.GetOtp;
import service.booking.servicelayer.SendMessage;
import service.booking.servicelayer.ValidateOtp;

@Controller
public class RegisterController {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(RegisterController.class);
	
	@Autowired
	private UserDaoImpl userDaoImpl;
	
	@Autowired
	private GetOtp getOtp;
	
	@Autowired
	private ValidateOtp validateOtp;
	
	@Autowired
	private SendMessage message;
	
	private int otp;
	
	@RequestMapping("/")
	public String getLandingPage() {
		
		logger.info("Accessing landing page");
		return "landing-page";
	}
	
	@RequestMapping("/register")
	public String getRegister(@ModelAttribute("userRegistration") UserDto userDto) {
		
		return "register";
	}
	
	@RequestMapping("/send-otp")
	public String getRegisterPage(@ModelAttribute("userRegistration") UserDto userDto,UserEmailDto user,Model model) {
		
		UserLoginDto userLoginDto = userDaoImpl.getUserDetails(userDto.getEmail());
		
		if(userLoginDto!=null)
		{
			model.addAttribute("userExists","already registered with these email");
			return "register";
		}
		
		otp = getOtp.getOtp();
		
		String text = "Your Otp : " + otp;
		
		user.setTo(userDto.getEmail());	
		user.setBody(text);
		
		message.send(user);
		
		return "register-otp";
	}
	
	@RequestMapping("/process-register")
	public String registrationSuccess(@Valid @ModelAttribute("userRegistration") UserDto userDto,BindingResult result,Model model) {
		
		if(result.hasErrors())
		{
				
			return "register";
		}
		
		try {
			
			if(!validateOtp.valid(otp,userDto.getOtp()))
			{
				model.addAttribute("invalid","invalid otp");
				return "register";
			}
				userDaoImpl.insert(userDto);
			
		}
		catch(Exception e) {
			
			
		}
		
		return "registration-success";
	}
	
    @RequestMapping("/test")
    @ResponseBody
    public String test() {
    	
    	logger.info("Accessing Test page");
        return "Test successful";
    }
}
