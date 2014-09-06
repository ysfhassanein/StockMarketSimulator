
import java.util.ArrayList;
import java.util.Collections;

public class Player
{
	private ArrayList<String> owns = new ArrayList<String>();
	private ArrayList<Integer> shares = new ArrayList<Integer>();
	
	private double loan = 0; //Loan is the initial loan taken from bank
	private double debt = 0; //Debt is how much currently owed to bank
	private int debtLaps = 0; //debtLaps is number of laps since taking loan
	private double intRate = 0;
	private double credit = 650;
	private String name;
	
	private double capital = 1000000;
	
	public Player(String s)
	{
		name = s;
	}
	
	//Adds new shares either to preexisting owns or adds new comp String and its shares just bought
	public void addShares(String comp, int shares)
	{
		for (int i = 0; i < owns.size(); i++)
			if (owns.get(i).compareTo(comp) == 0)
			{
				this.shares.set(i, new Integer(this.shares.get(i).intValue() + shares)); //Must do this to comply with Integer class immutability
				return;
			}
		//If company not found in list, add new comp and shares
		owns.add(comp);
		this.shares.add(new Integer(shares));
	}
	
	//int shares is a POSITIVE number to be subtracted
	public void subShares(int index, int shares)
	{
		int numRemaining = this.shares.get(index)-shares;
		if (numRemaining == 0)
		{
			this.shares.remove(index);
			owns.remove(index);
		}
		else
		{
			this.shares.set(index, new Integer(numRemaining));
		}
	}
	
	public int getSharesIndex(String comp)
	{
		for (int i = 0; i < owns.size(); i++)
			if (owns.get(i).compareTo(comp) == 0)
				return i;
		return -1;
	}
	public int getShares(String comp)
	{
		for (int i = 0; i < owns.size(); i++)
			if (owns.get(i).compareTo(comp) == 0)
				return shares.get(i);
		return -1;
	}
	public double getCapital(){
		return capital;
	}
	public double getNetValue(Market m)
	{
		//System.out.println(sumAssets(m));
		return sumAssets(m) - debt;
	}
	public double getShareValue(String name, Market m)
	{
		//Company[] comps = (Company[])m.getComps().toArray();
		for (Company c: m.getComps())
			if (name.compareTo(c.getName()) == 0)
				return c.getV();
		return 0;
	}
	public double sumAssets(Market m)
	{
		int sum = 0;
		for (int i = 0; i < owns.size(); i++)
			sum += getShareValue(owns.get(i), m);
		sum+=capital;
		return sum;
	}
	public void setCapital(double change) {
		capital += change;
	}
	public double getDebt() {
		return debt;
	}
	public void setDebt(double debt) {
		this.debt = debt;
	}
	public double getIntRate() {
		return intRate;
	}
	public void setIntRate(double intRate) {
		this.intRate = intRate;
	}
	public double getCredit() {
		return credit;
	}
	public void setCredit(double credit) {
		this.credit = credit;
	}

	public String getName() {
		return name;
	}

	public ArrayList<String> getOwns() {
		return owns;
	}

	public ArrayList<Integer> getShares() {
		return shares;
	}

	public int getDebtLaps() {
		return debtLaps;
	}

	public void setDebtLaps(int debtLaps) {
		this.debtLaps = debtLaps;
	}

	public double getLoan() {
		return loan;
	}

	public void setLoan(double loan) {
		this.loan = loan;
	}
	
	
}
