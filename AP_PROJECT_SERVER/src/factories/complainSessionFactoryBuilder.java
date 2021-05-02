package factories;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import domain.Complain;

public class complainSessionFactoryBuilder {
	public static SessionFactory SessionFactory = null;

	public static SessionFactory getSessionFactory() {
		if(SessionFactory == null)
			SessionFactory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Complain.class).buildSessionFactory();
		return SessionFactory;
	}
	
	public static void closeSessionFactory() {
		if(SessionFactory != null)
			SessionFactory.close();	
	}
}