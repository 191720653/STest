package com.cn.service;  
  
import java.util.List;  
  
import javax.inject.Inject;  
  
import org.apache.shiro.authc.AuthenticationException;  
import org.apache.shiro.authc.AuthenticationInfo;  
import org.apache.shiro.authc.AuthenticationToken;  
import org.apache.shiro.authc.SimpleAuthenticationInfo;  
import org.apache.shiro.authc.UsernamePasswordToken;  
import org.apache.shiro.authz.AuthorizationInfo;  
import org.apache.shiro.authz.SimpleAuthorizationInfo;  
import org.apache.shiro.realm.AuthorizingRealm;  
import org.apache.shiro.subject.PrincipalCollection;  
import org.springframework.stereotype.Service;  
  
import com.cn.pojo.Role;  
import com.cn.pojo.User;  
  
@Service 
public class MyShiro extends AuthorizingRealm{  
  
    @Inject  
    private UserService userService;  
    /** 
     * 权限认证 
     */  
    @Override  
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {  
        System.out.println("doGetAuthorizationInfo.......");
    	//获取登录时输入的用户名  
        String loginName=(String) principalCollection.fromRealm(getName()).iterator().next();  
        //到数据库查是否有此对象  
        User user=userService.findByName(loginName);  
        if(user!=null){  
            //权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）  
            SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();  
            //用户的角色集合  
            info.setRoles(user.getRolesName());  
            //用户的角色对应的所有权限，如果只使用角色定义访问权限，下面的四行可以不要  
            List<Role> roleList=user.getRoleList();  
            for (Role role : roleList) {  
            	System.out.println(role.toString());
                info.addStringPermissions(role.getPermissionsName());  
            }  
            System.out.println("doGetAuthorizationInfo.......info");
            return info;  
        }  
        System.out.println("doGetAuthorizationInfo......null");
        return null;  
    }  
  
    /** 
     * 登录认证
     */  
    @Override  
    protected AuthenticationInfo doGetAuthenticationInfo(  
            AuthenticationToken authenticationToken) throws AuthenticationException {
    	System.out.println("doGetAuthenticationInfo......");
        //UsernamePasswordToken对象用来存放提交的登录信息  
        UsernamePasswordToken token=(UsernamePasswordToken) authenticationToken;  
        //查出是否有此用户  
        User user=userService.findByName(token.getUsername());  
        System.out.println(token.getUsername());
        if(user!=null){  
        	System.out.println("doGetAuthenticationInfo......user!=null ");
            //若存在，将此用户存放到登录认证info中  
            return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());  
        }  
    	System.out.println("doGetAuthenticationInfo......null");
        return null;  
    }  
  
}