package net.minetopix.library.main.network;

import java.lang.reflect.Method;

import net.minetopix.library.main.network.listener.ServerListener;

public class ListenerFactory {

	public static void performListener(Server s , ServerListener action) {
		
		for(Object listener : s.getAllListeners()) {
			
			for(Method methode : listener.getClass().getMethods()) {
				
				if(methode.getReturnType() != void.class) {
					continue;
				}
				
				if(methode.getParameterTypes().length != 1 && methode.getParameterTypes()[0].isAssignableFrom(action.getClass())) {
					continue;
				}
			
				
				try {
					methode.invoke(listener, action);
				} catch (Exception e) {
					
				} 
				
			}
			
		}
		
	}
	
}
