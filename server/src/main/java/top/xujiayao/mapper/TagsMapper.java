package top.xujiayao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Xujiayao
 */
@Mapper
public interface TagsMapper {

	void deleteTagsByAid(Long aid);

	int saveTags(@Param("tags") String[] tags);

	List<Long> getTagsIdByTagName(@Param("tagNames") String[] tagNames);

	int saveTags2ArticleTags(@Param("tagIds") List<Long> tagIds, @Param("aid") Long aid);
}
