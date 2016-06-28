package com.cn.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cn.pojo.User;

/**
 * 描述:用户Dao
 */
@Repository
public interface UserRepository extends BaseEntityManager<User,Long> {

	/**
	 * 根据账号更新密码
	 * @param password
	 * @param username
	 */
	@Modifying
	@Query(" update User u set u.password = ?1 where u.username = ?2 ")
	public int updatePassword(String password, String username);
	
	/**
	 * 根据账号更新密码
	 * @param password
	 * @param username
	 */
	@Query(" from User u where u.username = ?1 ")
	public User findByName(String username);

}
