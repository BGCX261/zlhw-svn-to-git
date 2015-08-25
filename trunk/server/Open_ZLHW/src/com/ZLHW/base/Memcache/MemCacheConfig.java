/**
 * Copyright (c) 2008 Greg Whalin
 * All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the BSD license
 *
 * This library is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.
 *
 * You should have received a copy of the BSD License along with this
 * library.
 *
 * @author greg whalin <greg@meetup.com> 
 */
package com.ZLHW.base.Memcache;

import java.io.Serializable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.ZLHW.base.table.BaseTable;
import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

import org.apache.log4j.*;

public class MemCacheConfig  extends HttpServlet{  
	   static {
		   BasicConfigurator.configure();
			String[] servers = { "localhost:11211" };
			SockIOPool pool = SockIOPool.getInstance();
			pool.setServers( servers );
			pool.setFailover( true );
			pool.setInitConn( 10 ); 
			pool.setMinConn( 5 );
			pool.setMaxConn( 250 );
			//pool.setMaintSleep( 30 );
			pool.setNagle( false );
			pool.setSocketTO( 3000 );
			pool.setAliveCheck( true );
			pool.initialize();
	   }
	   public void init(ServletConfig config) throws ServletException
	    {
	        super.init(config);
	    //此处放置需要自动执行的代码
	        MemCachedClient mcc = new MemCachedClient();
	        mcc.flushAll();//清空所有缓存数据
	        System.out.println("加载memcache成功，并清空所有缓存数据");
	    }
	   
}
