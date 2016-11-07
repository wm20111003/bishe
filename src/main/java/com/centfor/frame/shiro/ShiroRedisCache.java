package com.centfor.frame.shiro;

import java.util.Collection;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import com.centfor.frame.cached.ICached;
import com.centfor.frame.common.BaseLogger;
import com.centfor.frame.util.SerializeUtil;

public class ShiroRedisCache<K,V> extends BaseLogger implements Cache<K,V>    {
private String name;
private ICached cached;

public ShiroRedisCache(String name,ICached cached){
	this.name=name;
	this.cached=cached;
}


/**
 * 获得byte[]型的key
 * @param key
 * @return
 */
private byte[] getByteKey(K key){
	if(key instanceof String){
		String preKey = key.toString();
		return preKey.getBytes();
	}else{
		return SerializeUtil.serialize(key);
	}
}


private byte[] getByteName(){
		return name.getBytes();
	
}
	
@Override
public V get(K key) throws CacheException {
	logger.debug("根据key从Redis中获取对象 key [" + key + "]");
	try {
		if (key == null) {
            return null;
        }else{
        	V value= (V) cached.getHashCached(getByteName(),getByteKey(key));
        	return value;
        }
	} catch (Throwable t) {
		throw new CacheException(t);
	}

}

@Override
public V put(K key, V value) throws CacheException {
	logger.debug("根据key从存储 key [" + key + "]");
	 try {
		 	cached.updateHashCached(getByteName(),getByteKey(key), SerializeUtil.serialize(value),null);
            return value;
        } catch (Throwable t) {
            throw new CacheException(t);
        }
}

@Override
public V remove(K key) throws CacheException {
	logger.debug("从redis中删除 key [" + key + "]");
	try {
        V previous = get(key);
        cached.deleteHashCached(getByteName(),getByteKey(key));
        return previous;
    } catch (Throwable t) {
        throw new CacheException(t);
    }
}

@Override
public void clear() throws CacheException {
	logger.debug("从redis中删除所有元素");
	try {
        cached.deleteCached(getByteName());
    } catch (Throwable t) {
        throw new CacheException(t);
    }
}

@Override
public int size() {
	try {
		Long longSize = new Long(cached.getHashSize(getByteName()));
        return longSize.intValue();
    } catch (Throwable t) {
        throw new CacheException(t);
    }
}

@SuppressWarnings("unchecked")
@Override
public Set<K> keys() {
	try {
        Set<K> keys = cached.getHashKeys(getByteName());
      return keys;
    } catch (Throwable t) {
        throw new CacheException(t);
    }
}

@Override
public Collection<V> values() {
	try {
		Collection<V> values = cached.getHashValues(getByteName());
      return values;
    } catch (Throwable t) {
        throw new CacheException(t);
    }
}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public ICached getCached() {
		return cached;
	}


	public void setCached(ICached cached) {
		this.cached = cached;
	}

}
