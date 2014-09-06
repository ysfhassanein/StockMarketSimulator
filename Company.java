
import java.util.Random;

public class Company implements Comparable
{
	private static String[] names = new String[1000];
	private static int namePos = 0;
	private static boolean byValue = false;
	
	private String name;
	private short ind; //Industry
	private double[] history = new double[Market.MAX_LAPS + 1];
	private int lap;
	
	public static void setNames()
	{
		if (namePos == 0)
			for (int i = 0; i < 10; i++)
				for (int j = 0; j < 10; j++)
					for (int k = 0; k < 10; k++)
						names[(i * 100) + (j * 10) + k] = String.valueOf((char)(i + 65)) + "" + String.valueOf((char)(j + 76)) + "" + String.valueOf((char)(k + 68));
					
		//for (int i = 0; i < 1000; i++)
			//System.out.print(names[i] + " ");
	}
	
	public static void setByValue(boolean b)
	{
		byValue = b;
	}
	
	public int compareTo(Object o2)
	{
		if (byValue)
		{
			if (this.history[lap] == ((Company) o2).history[lap])
	            return 0;
	        else if ((this.history[lap]) > ((Company) o2).history[lap])
	            return 1;
	        else
	            return -1;
		}
		else
		{
			if (this.change() == ((Company) o2).change())
	            return 0;
	        else if ((this.change()) > ((Company) o2).change())
	            return 1;
	        else
	            return -1;
		}
    }
	
	public String getName() {
		return name;
	}

	public Company(int curLap)
	{
		
		Random gen = new Random();
		
		name = names[namePos];
		namePos++;
		
		lap = curLap;
		ind = (short) gen.nextInt(10);
		history[lap-1] = gen.nextDouble() * 40 + 20;
		history[lap] = history[lap-1];
	}
	public void ListHist()
	{
		for (int i = 0; i < 150; i++)
			System.out.println(history[i]);
	}
	public void ListChanges()
	{
		for (int i = 1; i < 150; i++)
			System.out.println(change(i));
	}
	public double change()
	{
		return history[lap]-history[lap-1];
	}
	public double change(int lap)
	{
		return history[lap]-history[lap-1];
	}
	
	//Returns true if alive, false if bankrupt
	public boolean updateChange(double change)
	{
		lap++;
		history[lap] = history[lap-1] + change;
		if (history[lap] >= 0)
			return true;
		return false;
	}
	
	public double[] getH()
	{
		return history;
	}
	public double getV()
	{
		return history[lap];
	}
	public short getI()
	{
		return ind;
	}
	
	public static void main(String[] args)
	{
		//setNames();
		//for (int i = 0; i < 100; i++)
			//System.out.println(new Company(1).getH()[0]);
	}
}
