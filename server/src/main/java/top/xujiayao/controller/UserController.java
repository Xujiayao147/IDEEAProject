package top.xujiayao.controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import top.xujiayao.bean.RespBean;
import top.xujiayao.service.UserService;
import top.xujiayao.utils.Util;

import java.util.List;

/**
 * @author Xujiayao
 */
@RestController
public class UserController {

	final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping("/currentUserName")
	public String currentUserName() {
		return Util.getCurrentUser().getNickname();
	}

	@RequestMapping("/currentUserId")
	public Long currentUserId() {
		return Util.getCurrentUser().getId();
	}

	@RequestMapping("/currentUserEmail")
	public String currentUserEmail() {
		return Util.getCurrentUser().getEmail();
	}

	@RequestMapping("/isAdmin")
	public Boolean isAdmin() {
		List<GrantedAuthority> authorities = Util.getCurrentUser().getAuthorities();
		for (GrantedAuthority authority : authorities) {
			if (authority.getAuthority().contains("超级管理员")) {
				return true;
			}
		}
		return false;
	}

	@RequestMapping(value = "/updateUserEmail", method = RequestMethod.PUT)
	public RespBean updateUserEmail(String email) {
		if (userService.updateUserEmail(email) == 1) {
			return new RespBean("success", "开启成功!");
		}
		return new RespBean("error", "开启失败!");
	}
}
