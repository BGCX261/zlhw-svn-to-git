package com.ZLHW.base.factory;

import java.io.File;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import com.ZLHW.base.table.BaseTable;
import com.ZLHW.util.propertiesOperator;
/**
 * Configures and provides access to Hibernate sessions, tied to the
 * current thread of execution.  Follows the Thread Local Session
 * pattern, see {@link http://hibernate.org/42.html }.
 */
public class HibernateSessionFactory {

    /** 
     * Location of hibernate.cfg.xml file.
     * Location should be on the classpath as Hibernate uses  
     * #resourceAsStream style lookup for its configuration file. 
     * The default classpath location of the hibernate config file is 
     * in the default package. Use #setConfigFile() to update 
     * the location of the configuration file for the current session.   
     */
    private static String CONFIG_FILE_LOCATION = "WebRoot/WEB-INF/hibernate.cfg.xml";
	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
    private  static Configuration configuration = new Configuration();    
    private static org.hibernate.SessionFactory sessionFactory;
    private static String configFile = CONFIG_FILE_LOCATION;

	static {
    	try {
    		File file=new File(CONFIG_FILE_LOCATION);
			configuration.configure(file);
			
			Document document = DocumentHelper.createDocument();
			document.addDocType("hibernate-mapping ",
					"-//Hibernate/Hibernate Mapping DTD 3.0//EN",
					"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd");
			Element mapping = document.addElement("hibernate-mapping");
			propertiesOperator rc = new propertiesOperator("conf/beans.properties");// 相对路径
			Set<Entry<Object, Object>> set = rc.getAll();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				Entry<String, String> e=(Entry<String, String>) iterator.next();
				try {
					Class clazz=Class.forName(e.getKey());
					BaseTable tb=(BaseTable)clazz.newInstance();
					mapping.add(tb.getElement());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			configuration.addXML(document.asXML());
			
			sessionFactory = configuration.buildSessionFactory();
		} catch (Exception e) {
			System.err
					.println("%%%% Error Creating SessionFactory %%%%");
			e.printStackTrace();
		}
    }
    private HibernateSessionFactory() {
    }
	
	/**
     * Returns the ThreadLocal Session instance.  Lazy initialize
     * the <code>SessionFactory</code> if needed.
     *
     *  @return Session
     *  @throws HibernateException
     */
    public static Session getSession() throws HibernateException {
        Session session = (Session) threadLocal.get();

		if (session == null || !session.isOpen()) {
			if (sessionFactory == null) {
				rebuildSessionFactory();
			}
			session = (sessionFactory != null) ? sessionFactory.openSession()
					: null;
			threadLocal.set(session);
		}

        return session;
    }

	/**
     *  Rebuild hibernate session factory
     *
     */
	public static void rebuildSessionFactory() {
		try {
			configuration.configure(configFile);
			sessionFactory = configuration.buildSessionFactory();
		} catch (Exception e) {
			System.err
					.println("%%%% Error Creating SessionFactory %%%%");
			e.printStackTrace();
		}
	}

	/**
     *  Close the single hibernate session instance.
     *
     *  @throws HibernateException
     */
    public static void closeSession() throws HibernateException {
        Session session = (Session) threadLocal.get();
        threadLocal.set(null);

        if (session != null) {
            session.close();
        }
    }

	/**
     *  return session factory
     *
     */
	public static org.hibernate.SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
     *  return session factory
     *
     *	session factory will be rebuilded in the next call
     */
	public static void setConfigFile(String configFile) {
		HibernateSessionFactory.configFile = configFile;
		sessionFactory = null;
	}

	/**
     *  return hibernate configuration
     *
     */
	public static Configuration getConfiguration() {
		return configuration;
	}
	
//	public static void Print(){
//		Company c=(Company)getSession().get(Company.class, 1);
//		System.out.println(c.getCatalogs().size());
//	}
//	
//	public static void main(String[] args) {
//		HibernateSessionFactory.Print();
//	}
	

}