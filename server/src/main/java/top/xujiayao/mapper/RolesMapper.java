package top.xujiayao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.xujiayao.bean.Role;

import java.util.List;

/**
 * @author Xujiayao
 */
@Mapper
public interface RolesMapper {

	int addRoles(@Param("roles") String[] roles, @Param("uid") Long uid);

	List<Role> getRolesByUid(Long uid);
}
