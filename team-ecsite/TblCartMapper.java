package jp.co.internous.kabuki.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import jp.co.internous.kabuki.model.domain.TblCart;
import jp.co.internous.kabuki.model.domain.dto.CartDto;

@Mapper
public interface TblCartMapper {
	List<CartDto> findByUserId(@Param("userId") long userId);
	
	@Insert("INSERT INTO tbl_cart ("
			+ "user_id, product_id, product_count "
			+ ") "
			+ "VALUES ("
			+ "#{userId}, #{productId}, #{productCount} "
			+ ")")
	@Options(useGeneratedKeys=true, keyProperty="id")
	long insert(TblCart cart);
	
	@Update("UPDATE tbl_cart SET product_count = product_count + #{productCount}, updated_at = now() WHERE user_id = #{userId} AND product_id = #{productId}")
	long update(TblCart cart);
	
	@Select("SELECT count(user_id) FROM tbl_cart WHERE user_id = #{userId}")
	long findCountByUserId(@Param("userId") long userId);
	
	@Update("UPDATE tbl_cart SET user_id = #{userId}, updated_at = now() WHERE user_id = #{temporaryUserId}")
	void updateUserId(@Param("userId") long userId, @Param("temporaryUserId") long temporaryUserId);
	
	@Select("SELECT count(id) FROM tbl_cart WHERE user_id = #{userId} AND product_id = #{productId}")
	long findCountByUserIdAndProuductId(@Param("userId") long userId, @Param("productId") long productId);
	
	
	long deleteById(@Param("checkedIds") List<String> checkedIds);
	
	@Delete("DELETE FROM tbl_cart WHERE user_id = #{userId}")
	long deleteByUserId(@Param("userId") long userId);
}