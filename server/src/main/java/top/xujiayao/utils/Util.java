package top.xujiayao.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import top.xujiayao.bean.User;

/**
 * @author Xujiayao
 */
public class Util {

	public static User getCurrentUser() {
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
