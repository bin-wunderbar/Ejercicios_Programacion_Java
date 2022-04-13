package cli;

// ------------------------------------------------------------------------------------------------
class Cliente
{
	private String nombre, dni;
	
	public Cliente (String nombre, String dni)
	{
		this.nombre = nombre;
		this.dni = dni;
	}
	
	@Override
	public boolean equals (Object otro)
	{
		return dni.equals(((Cliente)otro).dni);
	}
}

//------------------------------------------------------------------------------------------------
class Material
{
	private String nombre, cod;
	
	public Material (String nombre, String cod)
	{
		this.nombre = nombre;
		this.cod = cod;
	}
	
	@Override
	public boolean equals (Object otro)
	{
		return cod.equals(((Material)otro).cod);
	}
}

//------------------------------------------------------------------------------------------------
public class Main {

	// ------------------------------------------------------------------------------------------------
	public boolean existeCliente (Cliente[] arrayClientes, Cliente clienteABuscar)
	{
		for (Cliente cliente : arrayClientes)
		{
			if (cliente.equals(clienteABuscar)) return true;
		}
		
		return false;
	}
	
	// ------------------------------------------------------------------------------------------------
	public boolean existeMaterial (Material[] arrayMateriales, Material materialABuscar)
	{
		for (Material material : arrayMateriales)
		{
			if (material.equals(materialABuscar)) return true;
		}
		
		return false;
	}

	// ------------------------------------------------------------------------------------------------
	public <T> boolean existe (T[] array, T elementoABuscar)
	{
		for (T elemento : array)
		{
			if (elemento.equals(elementoABuscar)) return true;
		}
		
		return false;
	}
	
	public Main ()
	{
		System.out.println ("Existe el cliente: " +
				existe (
						new Cliente[] {
								new Cliente ("a", "a"), 
								new Cliente ("b", "b")
								}, 
						new Cliente ("a", "a"))
		);
	}
	
	public static void main(String[] args) {

		new Main ();
	}

}
