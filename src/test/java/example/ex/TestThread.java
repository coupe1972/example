package example.ex;

import java.util.ArrayList;
import java.util.List;

import example.ex.Dispatcher;
import example.ex.Operador;
import example.ex.Supervisor;

public class TestThread extends Thread{

	private static Dispatcher dispatcher= new Dispatcher();
	
	public void run() {
		dispatcher.dispatcherCall();
	}
	
	public static void setearDatosDispatcher() {
		List<Operador> operadores= new ArrayList<>();
		List<Supervisor> supervisores= new ArrayList<>();
		
		Operador op= new Operador();
		op.setNombre("Operador - Pepe");
		op.setEstaLibre(true);		
		operadores.add(op);
		
		Operador op2= new Operador();
		op2.setNombre("Operador - Pablo");
		op2.setEstaLibre(true);		
		operadores.add(op2);
		
		Supervisor sup1= new Supervisor();
        sup1.setNombre("Supervisor - Agus");
		sup1.setEstaLibre(true);
		supervisores.add(sup1);
				
		dispatcher.setOperadores(operadores);
		dispatcher.setSupervisores(supervisores);
	
	}
	
	public static void main(String[] args) {		
		setearDatosDispatcher();
		
		// test con 10 Threads
		testDiezThreads();		
		
		// test con mas de 10 Threads
//		testMasDiezThreads();
		
		
	}
	
	// testeando 10 llamadas concurrentes
	private static void testDiezThreads() {
		
		TestThread thread1= new TestThread();
		TestThread thread2= new TestThread();
		TestThread thread3= new TestThread();
		TestThread thread4= new TestThread();
		TestThread thread5= new TestThread();
		TestThread thread6= new TestThread();
		TestThread thread7= new TestThread();
		TestThread thread8= new TestThread();
		TestThread thread9= new TestThread();
		TestThread thread10= new TestThread();
			
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		thread5.start();
		thread6.start();
		thread7.start();
		thread8.start();
		thread9.start();
		thread10.start();
	}
	
	// testeando mas de 10 llamadas concurrentes
	private static void testMasDiezThreads() {
		
		TestThread thread1= new TestThread();
		TestThread thread2= new TestThread();
		TestThread thread3= new TestThread();
		TestThread thread4= new TestThread();
		TestThread thread5= new TestThread();
		TestThread thread6= new TestThread();
		TestThread thread7= new TestThread();
		TestThread thread8= new TestThread();
		TestThread thread9= new TestThread();
		TestThread thread10= new TestThread();
		
		TestThread thread11= new TestThread();
		TestThread thread12= new TestThread();
		TestThread thread13= new TestThread();
		
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		thread5.start();
		thread6.start();
		thread7.start();
		thread8.start();
		thread9.start();
		thread10.start();
		thread11.start();
		thread12.start();
		thread13.start();		
	}
	
	
}
