
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

public class Market
{
	public static final int MAX_LAPS = 150;
	
	private ArrayList<Company> comps = new ArrayList<Company>(); //All companies
	private int lap = 1;
	private boolean GAME_OVER = false;
	private Random gen = new Random();
	
	public Market()
	{
		for (int i = 0; i < 30; i++)
		{
			comps.add(new Company(lap));
			System.out.println(comps.get(i).getName());
		}
		
		for (int i = 0; i < 100; i++)
			Lapse();
			
	}
	
	public void sortCompsByValue()
	{
		Company.setByValue(true);
		Collections.sort(comps);
	}
	public void sortCompsByChange()
	{
		Company.setByValue(false);
		Collections.sort(comps);
	}
	
	public void sortDisplay()
	{
		//Collections.sort(comps);
		for (int i = 0; i < comps.size(); i++)
			System.out.println(i + " Value. " + comps.get(i).getV() + "\tChange: " + comps.get(i).change());
		System.out.println();
	}
	
	/*
	 * Name: Field Influences
	 * 
	 * The following are the relative degrees of 
	 * influence of each industry on each other industry
	 * List:
	 * 
	 * 0. Manufacturing
	 * 1. Retail
	 * 2. Professional/Scientific
	 * 3. Agriculture/Petroleum/Mining
	 * 4. Construction
	 * 5. Wholesale
	 * 6. Information
	 * 7. Education
	 * 8. Arts/Entertainment
	 * 9. Finance
	 * 
	 * Degrees range from 0-100
	 * -1 signifies the current field
	 */
	public static final int[][] FINF = {{-1,10,5,25,10,75,5,5,0,5},
										{10,-1,20,10,5,50,15,5,50,15},
										{5,5,-1,20,15,5,25,35,10,5},
										{70,10,5,-1,60,10,5,5,5,5},
										{5,10,0,15,-1,5,5,5,10,5},
										{35,80,10,10,40,-1,5,5,5,5},
										{5,5,60,5,5,5,-1,70,50,40},
										{5,5,15,5,5,5,25,-1,15,10},
										{5,50,5,0,5,40,10,5,-1,5},
										{10,80,50,15,20,50,50,30,75,-1}};
	
	public void Lapse()
	{
		lap++;
		
		
		/*
		 * Step 1: Create random event in market
		 * Step 2: Create changes array -- array holding changes for each company
		 * Step 3: Apply event flux to companies
		 */
		double[] changes = new double[comps.size()];
		for (int i = 0; i < changes.length; i++)
			changes[i] = 0;
		
		/*
		 * Step 6: Apply effect of previous history on changes array
		 */
		for (int i = 0; i < changes.length; i++)
		{
			double[] hist = comps.get(i).getH();
			changes[i] += (hist[lap-1]-hist[lap-2])/1.333;
		}
		
		/*
		 * Step 4: Apply random fluctuations to changes array
		 * 	Randomly add flux to changes array, randomly positive or negative
		 */
		for (int i = 0; i < changes.length; i++)
		{
			if (gen.nextInt(2) == 1)
			{
				changes[i] += gen.nextInt(100) * 0.015;
			}
			else
			{
				changes[i] += gen.nextInt(100) * 0.015 * -1;
			}
		}
		
		/*
		 * Step 7: Update all companies, adding changes to share values
		 */
		for (int i = 0; i < changes.length; i++)
			comps.get(i).updateChange(changes[i]);
		//for (int i = 0; i < changes.length; i++)
			//System.out.println("Diff " + i + ": " + changes[i]);
		//System.out.println();
		
		sortCompsByValue();
		for (int i = 0; i < comps.size(); i++)
		{
			if (comps.get(0) == null || comps.get(0).getV() > 0)
				break;
			else
			{
				System.out.println(comps.get(0).getName() + " died at value: " + comps.get(0).getV());
				comps.remove(0);
				i--;
			}
		}
		
		/*
		 * Step 8: Randomly add new company, if randomly decided
		 */
		if (gen.nextInt(20) == 0)
			comps.add(new Company(lap));
		
		if (lap > MAX_LAPS)
			GAME_OVER = true;
	}
	public boolean gameOver()
	{
		return GAME_OVER;
	}
	
	public Company searchComp(String name)
	{
		for (int i = 0; i < comps.size(); i++)
			if (comps.get(i).getName().compareTo(name) == 0)
				return comps.get(i);
		return null;
	}
	
	public static void main(String[] args)
	{
		Company.setNames();
		Market m = new Market();
		//m.sortCompsByChange();
		//m.sortDisplay();
		//m.sortCompsByValue();
		//m.sortDisplay();
		/*for (int i = 0; i < m.getComps().size(); i++)
		{
			System.out.println(m.getComps().get(i).getName() + " Change Value: " + m.getComps().get(i).change());
		}*/
		//System.out.println("\n" + m.getComps().get(0).getName());
		
		
		//Checking length of each FINF array is 10
		/*
		for (int i = 0; i < 10; i++)
			System.out.println("Array" + i + ": " + FINF[i].length);
			*/
		
		//Checking MEvent works correctly
		/*for (int i = 0; i < 100; i++)
		{
			MEvent e = new MEvent();
			System.out.println("TEST " + i + ": " + "ind: " + e.ind + " mag: " + e.mag);
		}*/
		
		//Check market constructor
		/*
		Market m = new Market();
		for (int i = 0; i < 10; i++)
			m.Lapse();
			*/
		//for (Company e: m.comps)
			//System.out.println(e.getH()[0] + " " + e.getH()[1] + " " + e.getH()[2] + " " + e.getH()[3]);
	}
	public ArrayList<Company> getComps() {
		return comps;
	}
	public int getLap() {
		return lap;
	}
}

class MEvent
{
	Random gen = new Random();
	short ind;
	int mag = -1; //Magnitude of effect
	
	public MEvent()
	{
		if (gen.nextInt(2) == 1)
		{
			ind = (short) gen.nextInt(10);
			mag = gen.nextInt(100);
			if (gen.nextInt(2) == 1)
				mag*=-1;
		}
		else
			ind = -1;
	}
}



/*MEvent ev = new MEvent();

if (ev.ind != -1)
{
	for (int i = 0; i < changes.length; i++)
		if (comps.get(i).getI() == ev.ind)
			changes[i] += ev.mag * 0.2;
}




/*
 * Step 5: Apply field influence flux based on existing changes values
 * 	For all change values per company, multiply change of that company
 * 		by field influence of all other array locations in FINF and update FFLUX array
 * 		 in every other location
 
int[] FFLUX = new int[10]; //Field flux
for (int i = 0; i < 10; i++)
	FFLUX[i] = 0;

for (int i = 0; i < changes.length; i++)
{
	short curInd = comps.get(i).getI();
	int[] match = FINF[curInd];
	for (int j = 0; j < 10; j++)
	{
		if (match[j] == -1)
			continue;
		else
			FFLUX[curInd] += match[j] * changes[i];
	}
}

System.out.println("FFLUX VALUES");
for (int i = 0; i < 10; i++)
	System.out.println("For Industry " + i + ": " + FFLUX[i]);
for (int i = 0; i < changes.length; i++)
	changes[i] += FFLUX[comps.get(i).getI()];



*/
