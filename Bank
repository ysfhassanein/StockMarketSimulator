
import java.util.Random;

public class Bank
{
	private double intRate = 3;
	private int lap = 1;
	private Random gen = new Random();
	
	public double getIntRate() {
		return intRate;
	}

	/*
	 * Returns true if player borrows successfully from bank, 
	 * 		false otherwise (due to amt being more than player credit permits, or player already has debt owed)
	 */
	public boolean borrow(int amt, Player p)
	{
		if ((p.getDebt()>0.001) || (amt > (p.getCredit()*100)))
				return false;
		else
		{
			p.setDebt(amt);
			p.setIntRate(intRate);
			return true;
		}
	}
	
	public void takeInt(Player p)
	{
		double interest = p.getDebt() * p.getIntRate();
		p.setCapital(interest * -1);
	}
	
	public void Lapse()
	{
		lap++;
		if (gen.nextInt(2) == 1)
		{
			intRate += gen.nextInt(100) * 0.015;
		}
		else
		{
			intRate += gen.nextInt(100) * 0.015 * -1;
		}
	}
}
