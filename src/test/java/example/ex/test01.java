package example.ex;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import example.ex.Dispatcher;
import example.ex.Empleado;
import example.ex.Operador;
import example.ex.Supervisor;


public class test01{

	/**
	 *  Test q prueba la elecci�n de un operador sobre un supervisor
	 */
	
	@Test
	public void testOperadores() {
		Dispatcher dispatcher= new Dispatcher();
		
		List<Operador> operadores= new ArrayList<>();
		List<Supervisor> supervisores= new ArrayList<>();
		
		Operador op= new Operador();
		op.setNombre("op - Daniel");
		op.setDni(11111);
		op.setEstaLibre(true);						
		
		operadores.add(op);
		
		Supervisor sup= new Supervisor();
		sup.setNombre("sup - Maria");
		sup.setDni(22222);
		sup.setEstaLibre(true);
		supervisores.add(sup);
				
		dispatcher.setOperadores(operadores);
		dispatcher.setSupervisores(supervisores);
		
		Empleado empleado= dispatcher.dispatcherCall();

		assertTrue(empleado.getDni() == 11111);	

	}
		

}
