package com.centfor.frame.shiro;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import com.centfor.frame.cached.ICached;

public class ShiroRedisCacheManager extends AbstractCacheManager {
	private ICached cached;
	@Override
	protected Cache createCache(String cacheName) throws CacheException {
		return new ShiroRedisCache<String, Object>(cacheName,cached);
	}
	public ICached getCached() {
		return cached;
	}
	public void setCached(ICached cached) {
		this.cached = cached;
	}

}
