package example.ex;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import example.excepciones.MaxCantThreadsException;


public class Dispatcher {
	
	List<Operador> operadores= new ArrayList();
	List<Supervisor> supervisores= new ArrayList();
	List<Director> directores= new ArrayList();
	
	// asegura atomicidad al momento de leer la variable
	static AtomicInteger cantThreads= new AtomicInteger(1);
	
	// cantidad de reintentos para asignar a un empleado la llamada
	static AtomicInteger cantReintentosAsignacionLLamada= new AtomicInteger(0);
	
	// cantidad de reintentos al superar los 10 Threads concurrentes
	static AtomicInteger cantReintentosThreads= new AtomicInteger(0);
	
	
	public Empleado  dispatcherCall() {
		Empleado emp= null;
		
		try {
			validarCantidadThreadsEjecutandose();				
			emp= asignarLLamadaEmpleado();			
						
		}catch(MaxCantThreadsException max) {
			System.out.println(MaxCantThreadsException.getMsj());			
			
		}catch(Exception e) {
			// otro tratamiento del flujo
			System.out.println("ERROR DESCONOCIDO "+e.getMessage());		
		}		
		return emp;
	}
	
	
	/**
	 * Este m�todo valida la cantidad de Threads en ejecuci�n, si es menor de 10 retorna el numero del Thread y continua con la 
	 * asignaci�n del empleado, de llegar a los 10 Threads en ejecuci�n el sistema realiza 3 reintentos en lapsos de 12 segundos
	 * cada uno, pasados estos reintentos lanza una excepcion, se imprime por consola un mensaje y termina la ejecuci�n      
	 */
	
	private synchronized int validarCantidadThreadsEjecutandose() throws InterruptedException, MaxCantThreadsException{
		int reintentosThreads= cantReintentosThreads.get();
		
		if (cantThreads.get() <= 10) {
			
			int threadEjecutandose= cantThreads.getAndIncrement();
		
			return threadEjecutandose;
			
		}else {			
		
			if (reintentosThreads < 3) {									
				System.out.println("Se excedi� la cantidad de hilos concurrentes, se reintentar� en 12 segundos");
				cantReintentosThreads.incrementAndGet();
				
				wait (12000);
				validarCantidadThreadsEjecutandose();
				
			}else {
				// se realizaron los 3 reintentos
				throw new MaxCantThreadsException();
			}
			
			return 0;
		}
		
	}
	
	
	/**
	 * Este m�todo obtiene un empleado libre y le asigna la llamada. De no haber nadie disponible el m�todo cuenta con un sistema de 
	 * reintentos en lapsos de 10 segundos 
	 * 
	 */
	
	private Empleado asignarLLamadaEmpleado() throws InterruptedException  {		
		// obtengo la cantidad de reintentos realizados
		int reintentos= cantReintentosAsignacionLLamada.get();
		
		Empleado e= obtenerEmpleadoDisponible();
		
		if (e != null) {
			atendiendoLLamada(e);
			
			// luego se libera el empleado 
			e.setEstaLibre(true);			
		
		}else {
			// reintenta como maximo 5 veces asignarle un empleado
			if (reintentos < 5) {
				cantReintentosAsignacionLLamada.incrementAndGet();
				
				System.out.println("Se reintentar� en 10 segundos");
				 
				Thread.sleep(10000);
				asignarLLamadaEmpleado();
				
			}
		}
		return e;				
	}
	
	/**
	 * Este m�todo simula un empleado atendiendo una llamada,al ingresar se le cambia su estado, para q no lo tome otro threads
	 */

	private void atendiendoLLamada(Empleado e) throws InterruptedException {	
		e.setEstaLibre(false);		
		
		// simulaci�n del tiempo de llamada entre 5 y 10 segundos
		int tiempoLLamada= ThreadLocalRandom.current().nextInt(5000, 10000);		
		
		//simulaci�n del empleado hablando por telefono
	    Thread.yield();
		
		System.out.println("Se le asign� la llamada al empleado: "+e.getNombre()+ " y tuvo una duraci�n de: "+ tiempoLLamada+ " milisegundos");
		
		
	}
	
	
	/**
	 * Este m�todo obtiene un empleado libre, primero comienza buscando por operadores libres, luego supervisores libres
	 * y por �ltimo directores libres, de encontrar retorna el empleado, caso contrario null
	 * 
	 */
	
	private synchronized Empleado obtenerEmpleadoDisponible() {		
		boolean existeOperador= operadores.stream().
		           filter(op -> op.isEstaLibre()).
		           findFirst().isPresent();
		
		if (existeOperador) {           
			return operadores.stream().
			           filter(op -> op.isEstaLibre()).
			           findFirst().get();
			
		}else {			
			boolean existeSupervisor= supervisores.stream().
			           filter(op -> op.isEstaLibre()).
			           findFirst().isPresent();					
			
			if (existeSupervisor) {				
				return supervisores.stream().
				           filter(op -> op.isEstaLibre()).
				           findFirst().get();
			
			}else {
				
				boolean existeDirector= directores.stream().
				           filter(op -> op.isEstaLibre()).
				           findFirst().isPresent();
				
				if (existeDirector) {					
					return directores.stream().
				           filter(op -> op.isEstaLibre()).
				           findFirst().get();
				
				}else {
					// no hay nadie disponible					
					return null;								
				}
			}
		}			
	}
	

	public List<Operador> getOperadores() {
		return operadores;
	}


	public void setOperadores(List<Operador> operadores) {
		this.operadores = operadores;
	}


	public List<Supervisor> getSupervisores() {
		return supervisores;
	}


	public void setSupervisores(List<Supervisor> supervisores) {
		this.supervisores = supervisores;
	}


	public List<Director> getDirectores() {
		return directores;
	}


	public void setDirectores(List<Director> directores) {
		this.directores = directores;
	}
	
	
}