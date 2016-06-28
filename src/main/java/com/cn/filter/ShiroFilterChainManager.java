package com.cn.filter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.NamedFilterList;
import org.springframework.util.StringUtils;

import com.cn.pojo.Permission;

public class ShiroFilterChainManager {

	private DefaultFilterChainManager filterChainManager;

	private Map<String, NamedFilterList> defaultFilterChains;

	public void init() {
		defaultFilterChains = new HashMap<String, NamedFilterList>(
				filterChainManager.getFilterChains());
	}

	public void initFilterChains(List<Permission> permissions) {
		// 1、首先删除以前老的的filter chain并注册默认的
		filterChainManager.getFilterChains().clear();
		if (defaultFilterChains != null) {
			filterChainManager.getFilterChains().putAll(defaultFilterChains);
		}
		// 2.循环注册权限标识
		for (Permission permission : permissions) {
			// 注册permission filter
			if (!StringUtils.isEmpty(permission.getPkey())
					&& !StringUtils.isEmpty(permission.getAction())) {
				filterChainManager.addToChain(permission.getAction(), "permission",
						permission.getPkey());
			}
		}
	}

	public DefaultFilterChainManager getFilterChainManager() {
		return filterChainManager;
	}

	public void setFilterChainManager(
			DefaultFilterChainManager filterChainManager) {
		this.filterChainManager = filterChainManager;
	}

	public Map<String, NamedFilterList> getDefaultFilterChains() {
		return defaultFilterChains;
	}

	public void setDefaultFilterChains(
			Map<String, NamedFilterList> defaultFilterChains) {
		this.defaultFilterChains = defaultFilterChains;
	}

}
