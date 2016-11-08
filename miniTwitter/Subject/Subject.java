package miniTwitter.Subject;

import java.util.ArrayList;
import java.util.List;

import miniTwitter.Observer.Observer;

public abstract class Subject {
	
	//Field
	protected List<Observer> observers = new ArrayList<Observer>(); 
	
	//Observer pattern, subject abstract class methods
	public void attach(Observer observer) { 
		observers.add(observer); 
	} 
		
	public void detach(Observer observer) { 
		observers.remove(observer); 
	} 
 	 
	public void notifyObservers() { 
		for(Observer ob : observers) { 
			ob.update(this); 
		}
	}
} 

