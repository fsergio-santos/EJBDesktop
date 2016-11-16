package com.desktopejb.tester;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.ejb.client.EJBClientContext;
import org.jboss.ejb.client.PropertiesBasedEJBClientConfiguration;
import org.jboss.ejb.client.remoting.ConfigBasedEJBClientContextSelector;

import com.ejbservice.service.SendMessageRemote;


public class EJBTester {
    
	SendMessageRemote sendMessageRemote;
	Context context;
	
	public void runObjeto(){
		getContexto();
		sendMessageRemote = (SendMessageRemote) getObjeto();
		for (int i=0; i <= 10; i ++){
		    sendMessageRemote.mensagem("Teste valido "+Integer.toString(i));
		}
	}

	public void getContexto() {
		
		 Properties clientProperties = new Properties();
	     clientProperties.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED","false");
	     clientProperties.put("remote.connections", "default");
	     clientProperties.put("remote.connection.default.port", "8080");
	     clientProperties.put("remote.connection.default.host", "localhost");
	     //clientProperties.put("remote.connection.default.username", "eder");
	     //clientProperties.put("remote.connection.default.password", "@eder1");
	     clientProperties.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS","false");
	     EJBClientContext.setSelector(new ConfigBasedEJBClientContextSelector(new PropertiesBasedEJBClientConfiguration(clientProperties)));
         Properties properties = new Properties();
         properties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
	     try {
			context = new InitialContext(properties);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Object getObjeto(){
		Object objeto = null;
		try {
			objeto = context.lookup("ejb:/EJBService/SendMessage!com.ejbservice.service.SendMessageRemote");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return objeto;
	}

	public static void main(String args[]) {

		EJBTester teste = new EJBTester();
		teste.runObjeto();

	}

}
