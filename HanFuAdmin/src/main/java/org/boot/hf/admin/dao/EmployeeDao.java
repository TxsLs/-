package org.boot.hf.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.boot.hf.admin.Dao;
import org.boot.hf.admin.entity.Employee;
import org.boot.hf.admin.entity.Photo;
import org.quincy.rock.core.dao.DaoUtil;
import org.quincy.rock.core.util.MapUtil;


@Mapper
public interface EmployeeDao extends Dao<Employee> {

	/**
	 * <b>更新个人信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 根据用户id更新用户信息，值对象的id必须有效。
	 * 可以修改自己少部分个人信息。
	 * @param vo　个人用户信息
	 * @return　更新数据条数
	 */
	public int updateSelfInfo(Employee vo);

	/**
	 * <b>修改密码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param code 用户登录名
	 * @param newPassword 加密后的新密码
	 * @return 更新数据条数
	 */
	default int changePassword(String code, String newPassword) {
		return updateMap(MapUtil.asMap("password", newPassword), DaoUtil.and().equal("code", code));
	}

	/**
	 * <b>获得照片。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 返回的照片数据存放在二进制数组里。
	 * @param id 主键id
	 * @return Photo
	 */
	public Photo getPhoto(long id);

	/**
	 * <b>更新照片。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param photo 照片实体对象
	 * @return 更新数据条数
	 */
	public int updatePhoto(Photo photo);
	

}
