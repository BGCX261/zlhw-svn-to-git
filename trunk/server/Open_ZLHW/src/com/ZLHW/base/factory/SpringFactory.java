package com.ZLHW.base.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import flex.messaging.FactoryInstance;
import flex.messaging.FlexFactory;
import flex.messaging.config.ConfigMap;
import flex.messaging.services.ServiceException;

public class SpringFactory implements FlexFactory {

	private static final String SOURCE = "source";

	public void initialize(String arg0, ConfigMap arg1) {
	}

	public FactoryInstance createFactoryInstance(String id, ConfigMap properties) {
		SpringFactoryInstance instance = new SpringFactoryInstance(this, id,
				properties);
		instance.setSource(properties.getPropertyAsString(SOURCE, instance
				.getId()));
		return instance;
	}

	public Object lookup(FactoryInstance inst) {
		SpringFactoryInstance factoryInstance = (SpringFactoryInstance) inst;
		return factoryInstance.lookup();
	}

	static class SpringFactoryInstance extends FactoryInstance {

		public SpringFactoryInstance(FlexFactory factory, String id,
				ConfigMap properties) {
			super(factory, id, properties);
			// TODO Auto-generated constructor stub
		}

		public String toString() {
			return "SpringFactory instance for id=" + getId() + " source="
					+ getSource() + " scope=" + getScope();
		}

		public Object lookup() {
			String beanName = getSource();
			try{
				Object obj = BeanFactory.LookUp(beanName);
//				if(ProductRecordService.class.isInstance(obj)){
//					ProductRecordService.test();
//					ProductRecordService productRecordService
//						= (ProductRecordService) BeanFactory.LookUp("ProductRecordService");
//					System.out.println(productRecordService.getLuzc());
//					return productRecordService;
//				}else
				return obj;
			}catch(NoSuchBeanDefinitionException nexc){
				ServiceException e = new ServiceException();
				String msg = "Spring service named '" + beanName + "' does not exist.";
				e.setMessage(msg);
				e.setRootCause(nexc);
				e.setDetails(msg);
				e.setCode("Server.Processing");
				throw e;
			}catch (BeansException bexc){
				ServiceException e = new ServiceException();
				String msg = "Unable to create Spring service named '" + beanName + "' ";
				e.setMessage(msg);
				e.setRootCause(bexc);
				e.setDetails(msg);
				e.setCode("Server.Processing");
				throw e;
			}
		}
	}

}