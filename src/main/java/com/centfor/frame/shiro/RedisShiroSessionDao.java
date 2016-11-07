package com.centfor.frame.shiro;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.centfor.frame.cached.ICached;
import com.centfor.frame.util.SerializeUtil;
public class RedisShiroSessionDao extends AbstractSessionDAO {
	public Logger logger = LoggerFactory.getLogger(getClass());
	private String sessionprefix="ss-";
	public RedisShiroSessionDao (){
	}
	private ICached cached;
	@Override
	public void update(Session session) throws UnknownSessionException {
		try {
			cached.updateCached(session.getId().toString().getBytes(),SerializeUtil.serialize(session),session.getTimeout()/1000);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
	}

	@Override
	public void delete(Session session) {
		try {
			cached.deleteCached(session.getId().toString().getBytes());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
	}

	@Override
	public Collection<Session> getActiveSessions() {
		String keys=sessionprefix+"*";
		List<Session> list=null;
		try {
		 list=	(List<Session>) cached.getKeys(keys.getBytes());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return list;
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId=session.getId();
		try {
			super.assignSessionId(session,sessionprefix+ super.generateSessionId(session));
			update(session);
			sessionId=session.getId();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return sessionId;
		
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		Session session=null;
		try {
			session=	(Session) cached.getCached(sessionId.toString().getBytes());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return session;
		
	
	}

	public ICached getCached() {
		return cached;
	}

	public void setCached(ICached cached) {
		this.cached = cached;
	}

	public String getSessionprefix() {
		return sessionprefix;
	}

	public void setSessionprefix(String sessionprefix) {
		this.sessionprefix = sessionprefix;
	}
	
}
