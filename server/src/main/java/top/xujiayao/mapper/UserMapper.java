package top.xujiayao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.xujiayao.bean.Role;
import top.xujiayao.bean.User;

import java.util.List;

/**
 * @author Xujiayao
 */
@Mapper
public interface UserMapper {

	User loadUserByUsername(@Param("username") String username);

	long reg(User user);

	int updateUserEmail(@Param("email") String email, @Param("id") Long id);

	List<User> getUserByNickname(@Param("nickname") String nickname);

	List<Role> getAllRole();

	int updateUserEnabled(@Param("enabled") Boolean enabled, @Param("uid") Long uid);

	int deleteUserById(Long uid);

	void deleteUserRolesByUid(Long id);

	int setUserRoles(@Param("rids") Long[] rids, @Param("id") Long id);

	User getUserById(@Param("id") Long id);
}
