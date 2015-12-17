package es.udc.vvs.testautomation;

import static es.udc.vvs.model.util.servidorutil.ModelConstants.MASTER_TOKEN;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import org.graphwalker.core.condition.EdgeCoverage;
import org.graphwalker.core.condition.ReachedVertex;
import org.graphwalker.core.condition.TimeDuration;
import org.graphwalker.core.generator.AStarPath;
import org.graphwalker.core.generator.RandomPath;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.test.TestBuilder;
import org.junit.Test;

import static org.junit.Assert.*;
import es.udc.vvs.model.contenido.cancionimpl.ImplementacionCancion;
import es.udc.vvs.model.servidor.servidorimpl.ImplementacionServidor;
import es.udc.vvs.model.util.exceptions.TokenInvalidoException;

public class TestGraphWalker extends ExecutionContext implements Servidor {

    public final static Path MODEL_PATH = Paths.get("es/udc/vvs/testautomation/Servidor.graphml");

    private ImplementacionServidor servidor; 
    private String token;
    
    @Override
    public void Ready() 
    {
    	System.out.println("Ready");
    }
    
    
    @Override
	public void iniciar_servidor() {
    	servidor = new ImplementacionServidor("ServidorPrueba");
    	assertTrue(servidor.getContenidos().isEmpty());
    	assertTrue(servidor.getUsers().isEmpty());
    	
    	System.out.println("iniciar_servidor");
	}


	@Override
	public void Servidor_con_contenido() {
		assertFalse(servidor.getContenidos().isEmpty());
		assertTrue(servidor.getUsers().isEmpty());
		
		System.out.println("Servidor_con_contenido");
	}


	@Override
	public void eliminar() {
		ImplementacionCancion cancion = new ImplementacionCancion("cancion1",5);
		
		boolean eliminado = true;
		try {
			servidor.eliminar(cancion, MASTER_TOKEN);
		} catch (TokenInvalidoException e) {
			eliminado = false;
		}
		assertTrue(eliminado);
		
		System.out.println("eliminar");
	}


	@Override
	public void Servidor_con_contenido_y_token() {
		assertFalse(servidor.getContenidos().isEmpty());
		//assertFalse(servidor.getUsers().isEmpty());
		
		System.out.println("Servidor_con_contenido_y_token");
	}


	@Override
	public void alta() {
		//token = servidor.alta();
		
		System.out.println("alta");
	}


	@Override
	public void baja() {
		
		/*try {
			servidor.baja(token);
		} catch (TokenInvalidoException e) {
		}*/
		
		System.out.println("baja");
	}


	@Override
	public void Servidor_con_token() {
		//assertTrue(servidor.getContenidos().isEmpty());
		//assertFalse(servidor.getUsers().isEmpty());
		
		System.out.println("Servidor_con_token");
	}


	@Override
	public void agregar() {
		ImplementacionCancion cancion = new ImplementacionCancion("cancion1",5);
		
		boolean agregado = true;
		try {
			servidor.agregar(cancion, MASTER_TOKEN);
		} catch (TokenInvalidoException e) {
			agregado = false;
		}
		assertTrue(agregado);
		
		System.out.println("agregar");
	}
	
	
	@Test
    public void runSmokeTest() {
        new TestBuilder()
            .setModel(MODEL_PATH)
            .setContext(new TestGraphWalker())
            .setPathGenerator(new AStarPath(new ReachedVertex("Servidor_con_contenido_y_token"))) // xeramos un test que chegue a este estado
            .setStart("iniciar_servidor") // primeira chamada
            .execute();
    }

    @Test
    public void runFunctionalTest() {
        new TestBuilder()
            .setModel(MODEL_PATH)
            .setContext(new TestGraphWalker())
            .setPathGenerator(new RandomPath(new EdgeCoverage(100))) // xera tantos test como sexan necesarios para pasar alomenos unha vez por cada transición
            .setStart("iniciar_servidor") // primeira chamada
            .execute();
    }

    @Test
    public void runStabilityTest() {
        new TestBuilder()
            .setModel(MODEL_PATH)
            .setContext(new TestGraphWalker())
            .setPathGenerator(new RandomPath(new TimeDuration(15, TimeUnit.SECONDS))) // atravesamos aleatoriamente o grafo durante 3 minutos
            .setStart("iniciar_servidor") // primeira chamada
            .execute();
    }


	
}