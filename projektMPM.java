import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


class EtapMPM
{
	public String nazwa;
	public int czasTrwania;
	public List<Integer> listaPoprzednikow;
	public List<Integer> listaNastepnikow;
	public int maxRozpoczecie;
	public int minRozpoczecie;
	boolean odwiedzony;
	
	public EtapMPM()
	{
		listaPoprzednikow = new LinkedList<Integer>() ;
		listaNastepnikow = new LinkedList<Integer>() ;
		odwiedzony = false;
	}
}

public class projektMPM
{
	
	private static void rekurencjaDoPrzodu(EtapMPM[] et, int idEtapu, int tempMin)
	{
		if (et[idEtapu].odwiedzony == false) 
		{
			et[idEtapu].minRozpoczecie = tempMin;
			et[idEtapu].odwiedzony = true;  // odwiedzając węzeł zaświecamy flagę odwiedzenia
		}
		else
		{
			if (tempMin > et[idEtapu].minRozpoczecie) et[idEtapu].minRozpoczecie = tempMin;
		}

		for(int j=0 ; j<et[idEtapu].listaNastepnikow.size() ; j++)
		{
			rekurencjaDoPrzodu( et, et[idEtapu].listaNastepnikow.get(j), tempMin + et[idEtapu].czasTrwania );
		}
		
	}
	
	
	private static void rekurencjaDoTylu(EtapMPM[] et, int idEtapu, int tempMax)
	{
		if (et[idEtapu].odwiedzony == true) 
		{
			et[idEtapu].maxRozpoczecie = tempMax;
			et[idEtapu].odwiedzony = false; // tutaj odwiedzajac węzeł gasimy flage odwiedzenia
		}
		else
		{
			if (tempMax < et[idEtapu].maxRozpoczecie) et[idEtapu].maxRozpoczecie = tempMax;
		}

		for(int j=0 ; j<et[idEtapu].listaPoprzednikow.size() ; j++)
		{
			rekurencjaDoTylu( et, et[idEtapu].listaPoprzednikow.get(j), tempMax - et[et[idEtapu].listaPoprzednikow.get(j)].czasTrwania );
		}
		
	}
	
	public static void main(String[] args) 
	{
		FileReader fr = null ;
		String linia = "" ;
		int liczbaEtapow=0;  // do refaktoringu do klasy EtapMPM
		int idEtapu;
		
		try 
		{ 
			System.out.println("Odczyt pliku "+args[0]);
			System.out.println("====================================================");
			fr = new FileReader(args[0]);
		} catch (FileNotFoundException e) 
		{
			System.out.println("Nie otwarto pliku");
			System.exit(1);
		}
		
		BufferedReader bfr = new BufferedReader(fr);
		
		try 
		{
			linia = bfr.readLine();
			Scanner s = new Scanner ( linia );
			liczbaEtapow = s.nextInt();
		} catch (IOException e) 
		{
			System.out.println("Błąd odczytu pliku.");
			System.exit(2);
		}
		
		EtapMPM[] etap = new EtapMPM[liczbaEtapow];	
		
		try 
		{		
			for (int i = 0 ; i<liczbaEtapow ; i++ )
			{
				linia = bfr.readLine() ;
				Scanner s = new Scanner ( linia );
				idEtapu = s.nextInt();
				etap[idEtapu] = new EtapMPM();
				etap[idEtapu].czasTrwania = s.nextInt();
				
				while( s.hasNextInt() ) 
				{
					etap[idEtapu].listaPoprzednikow.add( s.nextInt() );
				}
				
				etap[idEtapu].nazwa = bfr.readLine() ;
			}
		} catch (IOException e) 
		{
			System.out.println("Błąd odczytu pliku.");
			System.exit(2);
		}	
		
		
		try 
		{
			fr.close();
		} catch (IOException e) 
		{
			System.out.println("Błąd zamknięcia pliku.");
			System.exit(3);
		}

		
			//budowanie listy nastepnikow dla kazdego etapu
			for (int i = 0 ; i<liczbaEtapow ; i++ )
			{
				for(int j=0 ; j<etap[i].listaPoprzednikow.size() ; j++)
				{
					etap[ etap[i].listaPoprzednikow.get(j) ].listaNastepnikow.add( i );
				}
			}
			
			// rekurencyjne przejście grafu od początku do końca projektu
			// w celu wyznaczenia najwcześniejszych rozpoczęć etapów
			for (int i = 0 ; i<liczbaEtapow ; i++ )
			{
				if (etap[i].listaPoprzednikow.size() == 0) rekurencjaDoPrzodu(etap, i, 0);
			}
			
			// rekurencyjne przejście grafu od końca do początku projektu
			// w celu wyznaczenia najpóźniejszych rozpoczęć etapów			
			for (int i = 0 ; i<liczbaEtapow ; i++ )
			{
				if (etap[i].listaNastepnikow.size() == 0) rekurencjaDoTylu(etap, i, etap[i].minRozpoczecie);
			}
			
			/* // wyświetlenie wszystkich etapow
			for (int i = 0 ; i<liczbaEtapow ; i++ )
			{
				System.out.println(i);
				System.out.println(etap[i].czasTrwania);
	
				for(int j=0 ; j<etap[i].listaPoprzednikow.size() ; j++)
				{
					System.out.print(etap[i].listaPoprzednikow.get(j) );
					System.out.print("," );
				}
				System.out.println("");
				for(int j=0 ; j<etap[i].listaNastepnikow.size() ; j++)
				{
					System.out.print(etap[i].listaNastepnikow.get(j) );
					System.out.print("," );
				}
				
				System.out.println("");
				System.out.println(etap[i].nazwa);
				System.out.println("");
			}	
			
			*/
			
			 // wyświetlenie min i max Rozpoczec
			for (int i = 0 ; i<liczbaEtapow ; i++ )
			{
				System.out.println(etap[i].nazwa);
				System.out.print(etap[i].minRozpoczecie);
				System.out.print("  ");
				System.out.println(etap[i].maxRozpoczecie);
				System.out.println("");
			}	
			
			
				

		

		
	}
}

