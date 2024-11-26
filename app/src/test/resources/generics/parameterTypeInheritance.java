package generics;

import java.util.ArrayList ;

interface Animal {
	  public void animalSound(); // interface method (does not have a body)
	  public void run(); // interface method (does not have a body)
	}

interface Interface2 {
	
	}

public class GenericWithInterfaceType<T extends Animal>{
	
}

public class GenericWithMultipleInterfaceType<B extends String & Animal & Interface2>{
	
}