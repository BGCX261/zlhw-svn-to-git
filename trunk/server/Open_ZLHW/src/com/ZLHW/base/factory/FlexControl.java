package com.ZLHW.base.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.CannotCreateTransactionException;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.common.model.Admin;

import flex.messaging.FactoryInstance;
import flex.messaging.FlexContext;
import flex.messaging.HttpFlexSession;
import flex.messaging.MessageException;
import flex.messaging.messages.Message;
import flex.messaging.messages.RemotingMessage;
import flex.messaging.services.remoting.RemotingDestination;
import flex.messaging.services.remoting.adapters.JavaAdapter;

public class FlexControl extends JavaAdapter {
	static Logger logger = Logger.getLogger(JavaAdapter.class);
	static String datasource = "DataSource1";
	public static Map<String, String> userMap = new HashMap<String, String>();

	@SuppressWarnings("unchecked")
	public Object invoke(Message message) {
		Map map = new HashMap();
		try {
			CustomerContextHolder.setCustomerType(datasource);// 设置为另一个数据源
			RemotingDestination remotingDestination = (RemotingDestination) getDestination();
			RemotingMessage remotingMessage = (RemotingMessage) message;
			FactoryInstance factoryInstance = remotingDestination
					.getFactoryInstance();
			String className = factoryInstance.getId();
			remotingMessage.setSource(className);
			String methodName = remotingMessage.getOperation();
			// 重写方法传入的参数
			List list = new ArrayList();

			Object[] p1 = (Object[]) remotingMessage.getParameters().get(0);
			if (p1.length > 0) {
				for (Object o : p1) {
					list.add(o);
				}
			}
			remotingMessage.setParameters(list);

			try {
				 if((!className.equals("AdminService"))&&(!methodName.equals("login"))){
				 HttpFlexSession session=(HttpFlexSession)
				 FlexContext.getFlexSession();
				 Admin admin=(Admin)session.getAttribute("Admin");
				 if(admin==null)
				 throw new BaseErrorModel("会话超时,请重新登录","0000");
				 if(!userMap.get(admin.getAccount()).equals(session.getId()))
				 throw new BaseErrorModel("用户在其他地方登陆,请重新登录","0000");
				 }
				 System.out.println(FlexContext.getHttpRequest().getContextPath());
				Object o = super.invoke(message);
				if (o == null) {
					map.put("success", "执行操作成功");
					return map;
				} else if (Map.class.isAssignableFrom(o.getClass())) {
					return o;
				} else {
					return o;
				}
			} catch (Exception e) {
				logger.error("flexControl.invoke异常", e);
				if (e.getClass() == BaseErrorModel.class) {
					map.put("error", e);
				} else if (e.getClass() == MessageException.class) {
					if (e.getCause().getClass() == MessageException.class) {
						map.put("error", new BaseErrorModel("违反唯一性约束条件",
								"viewname"));
					} else if (e.getCause().getClass() == BaseErrorModel.class) {
						map.put("error", e.getCause());
					} else if (e.getCause().getClass() == CannotCreateTransactionException.class) {
						datasource = "DataSource2";// 设置为另一个数据源
					} else if (e.getCause().getClass() == DataIntegrityViolationException.class) {
						map.put("error", new BaseErrorModel("数据库操作异常，请确认目标数据没有外键关联",
						"viewname"));
					} else {
						map.put("error", new BaseErrorModel("后台未知异常",
								"viewname"));
					}
				}
				return map;
			}
		} catch (RuntimeException e) {
			logger.error("flexControl参数异常", e);
			throw e;
		}
	}
}