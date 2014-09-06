
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.UIManager;

import java.awt.Image;

import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Game extends JFrame
{
	Market market;
	Bank bank = new Bank();
	Player player;
	
	public Game() throws Exception
	{
		String s = (String)prompt("Enter Player Name");
		if (s == null || s.compareTo("") == 0)
			s = "Player";
		player = new Player(s);
		Company.setNames();
		market = new Market();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 650);
        setLocationRelativeTo(null);
        setTitle("Stock Market Game");
        setResizable(false);
        
        JPanel p = new GamePanel(this);
        System.out.println(p.getSize());
		add(p);
        
        setVisible(true);
	}
	
	public static void main(String[] args) throws Exception
	{
		new Game();
	}
	
	public Object prompt(String q)
	{
		//Object[] possibilities = null;
		return JOptionPane.showInputDialog(
		                    this,
		                    q,
		                    "Prompt",
		                    JOptionPane.PLAIN_MESSAGE,
		                    null,
		                    null,
		                    "Player");
	}
}

class GamePanel extends JPanel
{
	private Game game;
	private Random gen = new Random();
	
	public Object prompt(String q)
	{
		//Object[] possibilities = null;
		return JOptionPane.showInputDialog(
		                    this,
		                    q,
		                    "Prompt",
		                    JOptionPane.PLAIN_MESSAGE,
		                    null,
		                    null,
		                    null);
	}
	public void message(String q)
	{
		//Object[] possibilities = null;
		JOptionPane.showMessageDialog(this, q);
	}

	class TimePanel extends JPanel
	{
		private ClockPanel p;
		public ClockPanel getClockPanel()
		{
			return p;
		}
		public void giveClock(ClockPanel p)
		{
			this.p = p;
		}
		class Left extends JPanel
		{
			public Left() throws IOException
			{
				setLayout(new GridLayout(5,0));
				add(new exitB());
				
				//Add empty Text Areas to get to fifth place
				JTextArea a1=new JTextArea(), a2=new JTextArea(), a3=new JTextArea();
				a1.setVisible(false);
				a2.setVisible(false);
				a3.setVisible(false);
				add(a1);
				add(a2);
				add(a3);
				
				add(new timeB());
				
				/*
				JPanel bContainer = new JPanel();
				bContainer.setLayout(new GridLayout(0,2));
				JTextArea left = new JTextArea("Click to Play/Stop Clock");
				left.setEditable(false);
				bContainer.add(left);
				
				JPanel bContainer2 = new JPanel();
				BufferedImage image = ImageIO.read(new File("pauseButton.JPG"));
				//BufferedImage bImage = image.getScaledInstance(100, 40, Image.SCALE_AREA_AVERAGING);
				JButton pauseB = new JButton(new ImageIcon(image.getScaledInstance(70, 50, Image.SCALE_AREA_AVERAGING)));
				pauseB.setPreferredSize(new Dimension(40,40));
				pauseB.addActionListener(new pauseActionListener());
				pauseB.setBorder(BorderFactory.createEmptyBorder());
				pauseB.setContentAreaFilled(false);
				bContainer2.add(pauseB);
				bContainer.add(bContainer2);
				add(bContainer);
				*/
			}
			class timeB extends JButton
			{
				public timeB()
				{
					super("Timer Rate");
					addActionListener(new ActionListener()
					{
			            public void actionPerformed(ActionEvent e)
			            {
			            	String s = (String)prompt("Current Rate: " + tRate + "\n\nEnter New Rate (s/lap 3-180)");
			            	if (s == null || s.compareTo("") == 0)
			            	{
			            		message("Invalid Input");
			                	return;
			            	}
			                int rate = Integer.valueOf(s);
			                if (rate < 3 || rate > 180)
			                {
			                	message("Invalid Input");
			                	return;
			                }
			                
			                int actualRate = (int)(rate * 16.66666667);
			                
			                setTimerRate(actualRate);
			            }
			        });  
				}
			}
			class exitB extends JButton
			{
				public exitB()
				{
					super("Exit");
					addActionListener(new ActionListener()
					{
			            public void actionPerformed(ActionEvent e)
			            {
			                System.exit(0);
			            }
			        });  
				}
			}
			class pauseActionListener implements ActionListener
			{

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
			}

			//private JTextArea day, timeLeft;
			//private JButton exit = new exitB();
			
			public void paintComponent(Graphics g)
			{
				g.setColor(Color.WHITE);
				g.fillRect(0,0,300,300);
				g.setColor(Color.BLACK);
				g.setFont(new Font("Verdana", Font.BOLD, 18));
				g.drawString("Day " + game.market.getLap(), 30, 85);
				String secs = String.valueOf((int)((60-(p.getMinutes()%60))*((double)t.getDelay()/1000)));
				if (Integer.valueOf(secs) < 5)
					g.setColor(Color.RED);
				else if (Integer.valueOf(secs) < 10)
					g.setColor(Color.GRAY);
				else
					g.setColor(new Color(0,150,0));
				g.drawString("Seconds Left: " + secs, 30, 135);
			}
		}
		public TimePanel() throws IOException
		{
			setBackground(Color.WHITE);
			setLayout(new GridLayout(0,2));
			
			Left left = new Left();
			ClockPanel p = new ClockPanel(game.market, left, GamePanel.this);
			giveClock(p);
			p.setTime(0);
			add(left);
			add(p);
			
			
			/*
			JPanel left = new JPanel();
			left.setLayout(new GridLayout(4,0));
			
			JTextArea bankLabel = new JTextArea("  Seconds Left\n  Before Lapse: " + (60-p.getMinutes())/4);
			bankLabel.setFont(new Font("Verdana", Font.BOLD, 18));
			left.add(bankLabel);
			
			class updateSeconds implements ActionListener
			{
				JTextArea p; JPanel cont;
				int minutes;
				public updateSeconds(JPanel cont, JTextArea p, ClockPanel clock)
				{
					this.p = p; this.cont = cont;
					minutes = clock.getMinutes();
				}
				public void actionPerformed(ActionEvent e)
				{
					p.setText("  Seconds Left\n  Before Lapse: " + (60-minutes)/4);
					System.out.println(minutes);
					cont.repaint();
				}
			}
			Timer t = new Timer(250, new updateSeconds(left, bankLabel, p));
			t.start();
			/*
			JTextArea IRLabel = new JTextArea("    Current Interest Rate: " + game.bank.getIntRate() + "%");
			IRLabel.setFont(new Font("Verdana", Font.BOLD, 18));
			add(bankLabel);
			add(IRLabel);
			JTextArea DOLabel = new JTextArea("    Debt Owed: " + game.player.getDebt());
			DOLabel.setFont(new Font("Verdana", Font.BOLD, 18));
			*/
			
			//add(left);
		}
	}
	class InvPanel extends JPanel
	{
		public InvPanel()
		{
			setBackground(Color.WHITE);
			add(new JTextArea("Investors"));
		}
	}
	class BankPanel extends JPanel
	{
		/*
		private java.awt.Image backgroundImage;
		  public void paintComponent(Graphics g)
		  {
		    super.paintComponent(g);
		    // Draw the background image.
		    g.drawImage(backgroundImage, 0, 0, null);
		    System.out.println(this.size().width);
		  }
		 */
		
		public BankPanel() throws IOException
		{
			//backgroundImage = ImageIO.read(new File("bank_image.jpeg"));
			
			setLayout(new GridLayout(0,2));
			setBackground(Color.WHITE);
			JPanel left = new JPanel();
			left.setBackground(Color.WHITE);
			left.setLayout(new GridLayout(4, 0));
			
			String s = String.format("    Bank Credit: %4.2f\n\n    Current Interest Rate: %2.3f%s", game.player.getCredit(), game.bank.getIntRate(), "%");
			//JTextArea bankLabel = new JTextArea("    Bank Credit: " + game.player.getCredit() + "\n\n    Current Interest Rate: " + game.bank.getIntRate() + "%");
			JTextArea bankLabel = new JTextArea(s);
			bankLabel.setFont(new Font("Verdana", Font.BOLD, 12));
			left.add(bankLabel);
			//JTextArea IRLabel = new JTextArea("    Current Interest Rate: " + game.bank.getIntRate() + "%");
			//IRLabel.setFont(new Font("Verdana", Font.BOLD, 12));
			//left.add(bankLabel);
			//left.add(IRLabel);
			JTextArea DOLabel = new JTextArea("\n    Interest Payed Next Round: " + game.player.getDebt() * game.player.getIntRate());
			DOLabel.setFont(new Font("Verdana", Font.BOLD, 12));
			left.add(DOLabel);
			
			left.add(new payDebtB());
			left.add(new borrowB());
			add(left);
			
			JPanel right = new BRight();
			add(right);
			//add(bankLabel);
			//add(IRLabel);
			//add(DOLabel);
			//add(p1);
			//add(p2);
		}
		class BRight extends JPanel
		{
			private java.awt.Image backgroundImage;
			public BRight() throws IOException
			{
				backgroundImage = ImageIO.read(new File("banks_image.gif"));
				setBackground(Color.WHITE);
			}
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.drawImage(backgroundImage.getScaledInstance(200, 150, Image.SCALE_AREA_AVERAGING), 50, 25, null);
			}
		}
		class payDebtB extends JButton
		{
			public payDebtB()
			{
				super("Pay Debt");
				//this.setSize(300, 100);
				addActionListener(new ActionListener()
				{
		            public void actionPerformed(ActionEvent e)
		            {
		                String s = (String)prompt("Enter Amount to Repay (enter ALL to payback all debt)");
		                if (s == null || s.compareTo("") == 0)
		                {
		                	message("Invalid Input");
		                	return;
		                }
		                if (s.compareTo("ALL") == 0 && game.player.getDebt() > game.player.getCapital())
		                {
		                	message("Cannot pay this amount.");
		                	return;
		                }
		                if (s.compareTo("ALL") != 0 && Double.valueOf(s) > game.player.getCapital())
		                {
		                	message("Cannot pay this amount.");
		                	return;
		                }
		                if (s.compareTo("ALL") == 0)
		                {
		                	double allDebt = game.player.getDebt();
		                	game.player.setDebt(0);
			                game.player.setCapital(-1 * allDebt);
			                game.player.setCredit(game.player.getCredit() + getChangeCredit(game.player));
			                game.player.setLoan(0);
			                game.player.setIntRate(0);
			                redrawLeftDebtsProPanel();
			                redrawRightProPanel();
			                message("Transaction Complete");
			                return;
		                }
		                double curDebt = game.player.getDebt();
		                double payBack = Double.valueOf(s);
		                if (payBack < 0 || payBack > curDebt)
		                {
		                	message("Invalid Input");
		                	return;
		                }
		                game.player.setDebt(curDebt-payBack);
		                game.player.setCapital(-1 * payBack);
		                if (game.player.getDebt() < 0.0000001)
		                	game.player.setCredit(game.player.getCredit() + getChangeCredit(game.player));
		                redrawLeftDebtsProPanel();
		                redrawRightProPanel();
		                message("Transaction Complete");
		            }
		        });
			}
			public double getChangeCredit(Player p)
			{
				double change = p.getLoan() * (1.0/(p.getDebtLaps()*10000 + 100));
				System.out.println("dEBTlAPS(): " + p.getDebtLaps());
				System.out.println("GetLoan(): " + p.getLoan());
				System.out.println("Change in credit: " + change);
				return change;
			}
		}
		class borrowB extends JButton
		{
			public borrowB()
			{
				super("Borrow");
				addActionListener(new ActionListener()
				{
		            public void actionPerformed(ActionEvent e)
		            {
		            	Player p = game.player;
		            	if (p.getDebt() > 0.0000001)
		            	{
		            		message("Debt Owed");
		            		return;
		            	}
		            	double maxLoan = (p.getCredit()-550) * 1000 + 1000;
		            	if (maxLoan < 0)
		            		maxLoan = 1000;
		            	String s = (String)prompt("Maximum Loan (based on credit): " + maxLoan + "\n\nEnter Amount to Borrow");
		                if (s == null || s.compareTo("") == 0)
		                {
		                	message("Invalid Input");
		                	return;
		                }
		                double loan = Double.valueOf(s);
		                if (loan < 0 || loan > maxLoan)
		                {
		                	message("Invalid Input");
		                	return;
		                }
		                p.setDebt(loan);
		                p.setDebtLaps(0);
		                p.setLoan(loan);
		                p.setIntRate(game.bank.getIntRate());
		                
		                redrawLeftDebtsProPanel();
		                redrawRightProPanel();
		                message("Transaction Complete");
		            }
		        });  
			}
		}
	}
	
	JTextArea[] markChangeAreas = new JTextArea[15];
	class MarkLeft extends JPanel
	{
		public MarkLeft()
		{
			setLayout(new GridLayout(6,3));
			this.setBackground(Color.WHITE);
			JTextArea labelTop = new JTextArea("Major Changes");
			labelTop.setFont(new Font("Verdana", Font.BOLD, 12));
			labelTop.setEditable(false);
			add(new JTextArea("")); add(labelTop); add(new JTextArea(""));
			//add(new JTextArea("Market"));
			game.market.sortCompsByChange();
			for (int i = 0; i < 7; i++)
			{
				Company c = game.market.getComps().get(i);
				Company c2 = game.market.getComps().get(game.market.getComps().size()-i-1);
				String s = String.format("%3s: %2.2f", c.getName(), c.change());
				String s2 = String.format("%3s: %2.2f", c2.getName(), c2.change());
				JTextArea label = new JTextArea(s);
				label.setFont(new Font("Verdana", Font.BOLD, 12));
				label.setEditable(false);
				markChangeAreas[i*2] = label;
				JTextArea label2 = new JTextArea(s2);
				label2.setFont(new Font("Verdana", Font.BOLD, 12));
				label2.setEditable(false);
				markChangeAreas[i*2 + 1] = label2;
				if (c.change() >= 0)
					label.setForeground(Color.GREEN);
				else
					label.setForeground(Color.RED);
				if (c2.change() >= 0)
					label2.setForeground(Color.GREEN);
				else
					label2.setForeground(Color.RED);
				add(label);
				add(label2);
			}
			//Last part is done for that one last label, as previous 14 are done in pairs
			Company c = game.market.getComps().get(8);
			String s = String.format("%3s: %2.2f", c.getName(), c.change());
			JTextArea flabel = new JTextArea(s);
			flabel.setFont(new Font("Verdana", Font.BOLD, 12));
			flabel.setEditable(false);
			markChangeAreas[14] = flabel;
			if (c.change() >= 0)
				flabel.setForeground(Color.GREEN);
			else
				flabel.setForeground(Color.RED);
			add(flabel);
		}
	}
	MarkLeft markLeft;
	public void redrawMarkLeft()
	{
		game.market.sortCompsByChange();
		for (int i = 0; i < 7; i++)
		{
			Company c = game.market.getComps().get(i);
			Company c2 = game.market.getComps().get(game.market.getComps().size()-i-1);
			String s = String.format("%3s: %2.2f", c.getName(), c.change());
			String s2 = String.format("%3s: %2.2f", c2.getName(), c2.change());
			JTextArea label = markChangeAreas[i*2];
			label.setEditable(false);
			label.setFont(new Font("Verdana", Font.BOLD, 12));
			JTextArea label2 = markChangeAreas[i*2 + 1];
			label2.setFont(new Font("Verdana", Font.BOLD, 12));
			label2.setEditable(false);
			if (c.change() >= 0)
				label.setForeground(Color.GREEN);
			else
				label.setForeground(Color.RED);
			if (c2.change() >= 0)
				label2.setForeground(Color.GREEN);
			else
				label2.setForeground(Color.RED);
			label.setText(s);
			label2.setText(s2);
			//add(label);
			//add(label2);
		}
		//Last part is done for that one last label, as previous 14 are done in pairs
		Company c = game.market.getComps().get(8);
		String s = String.format("%3s: %2.2f", c.getName(), c.change());
		JTextArea flabel = markChangeAreas[14];//; flabel.setEditable(false);
		flabel.setFont(new Font("Verdana", Font.BOLD, 12));
		if (c.change() >= 0)
			flabel.setForeground(Color.GREEN);
		else
			flabel.setForeground(Color.RED);
		flabel.setText(s);
		//add(flabel);
	}
	class MarkPanel extends JPanel
	{
		public MarkPanel()
		{
			setLayout(new GridLayout(0,2));
			setBackground(Color.WHITE);
			add(markLeft = new MarkLeft());
			add(new markRight());
		}
		
		class markRight extends JPanel
		{
			public markRight()
			{
				setLayout(new GridLayout(4,0));
				setBackground(Color.WHITE);
				add(new MHistB());
				add(new CHistB());
				add(new BuyB());
				add(new SellB());
			}
			class SellB extends JButton
			{
				public SellB()
				{
					super("Sell");
					addActionListener(new ActionListener()
					{
			            public void actionPerformed(ActionEvent e)
			            {
			            	String name = (String)prompt("From which company do you wish to sell? (case sensitive)");
			            	int index = game.player.getSharesIndex(name);
			            	if (index == -1)
			            	{
			            		message("Company shares are not in possession.");
			            		return;
			            	}
			            	Company c = game.market.searchComp(name);
			            	if (c == null)
			            	{
			            		message("Company has gone bankrupt.");
			            		return;
			            	}
			            	else
			            		message(c.getName() + " Share Value: " + c.getV());
			            	String s = (String)prompt("How many shares?");
			            	if ((s == null) || s.compareTo("") == 0)
			            	{
			            		message("Nothing Entered");
			            		return;
			            	}
			            	int numShares = Integer.valueOf(s);
			            	if (numShares > game.player.getShares(name))
			            	{
			            		message("You do not own these many shares.");
			            		return;
			            	}
			            	
			            	double dealV = c.getV() * numShares;

		            		game.player.setCapital(dealV);
		            		redrawLeftAssetsProPanel();
		            		redrawRightProPanel();
		            		game.player.subShares(index, numShares);
		            		//game.player.addShares(name, numShares);
		            		redrawOwnPanel();
		            		message("Transaction Complete");
		            		//message(String.valueOf(game.player.getShares().get(0)));
		            		System.out.println(game.player.getCapital());
			            }
			        }); 
				}
			}
			class MHistB extends JButton
			{
				public MHistB()
				{
					super("Market Changes");
					addActionListener(new ActionListener()
					{
			            public void actionPerformed(ActionEvent e)
			            {
			                new ListFrame(game.market);
			            }
			        });  
				}
			}
			class CHistB extends JButton
			{
				public CHistB()
				{
					super("Company History");
					addActionListener(new ActionListener()
					{
			            public void actionPerformed(ActionEvent e)
			            {
			                String s = (String)prompt("Enter Company Name");
			                String t = (String)prompt("Share Values or Changes in Value? (enter 1 or 2)");
			                if (s != null && t != null)
				                for (int i = 0; i < game.market.getComps().size(); i++)
				                {
			                		System.out.println(s + " " + game.market.getComps().get(i).getName());
				                	if (s.compareTo((game.market.getComps().get(i).getName())) == 0)
				                	{
				                		if (Integer.valueOf(t) == 1)
				                			new DisplayHistory(game.market.getComps().get(i), game.market.getLap(), 1);
				                		else
				                			new DisplayHistory(game.market.getComps().get(i), game.market.getLap(), 2);
				                		return;
				                		//System.out.println(s + " " + game.market.getComps().get(i).getName());
				                	}
				                }
			                message("Company Not Found, or Incorrect Input");
			            }
			        });
				}
			}
			class BuyB extends JButton
			{
				public BuyB()
				{
					super("Buy");
					addActionListener(new ActionListener()
					{
			            public void actionPerformed(ActionEvent e)
			            {
			            	String name = (String)prompt("From which company do you wish to buy? (case sensitive)");
			            	Company c = game.market.searchComp(name);
			            	if (c == null)
			            	{
			            		message("Company does not exist or has gone bankrupt.");
			            		return;
			            	}
			            	else
			            		message(c.getName() + " Share Value: " + c.getV());
			            	String s = (String)prompt("How many shares?");
			            	if ((s == null) || s.compareTo("") == 0)
			            	{
			            		message("Nothing Entered");
			            		return;
			            	}
			            	int numShares = Integer.valueOf(s);
			            	double dealV = c.getV() * numShares;
			            	if (dealV > game.player.getCapital())
			            	{
			            		message("You cannot afford this deal\n\nCapital: " + game.player.getCapital() + "\nDeal Price: " + dealV);
			            	}
			            	else
			            	{
			            		game.player.setCapital(-1 * dealV);
			            		redrawLeftAssetsProPanel();
			            		redrawRightProPanel();
			            		game.player.addShares(name, numShares);
			            		redrawOwnPanel();
			            		message("Transaction Complete");
			            		//message(String.valueOf(game.player.getShares().get(0)));
			            		System.out.println(game.player.getCapital());
			            	}
			            }
			        });
				}
			}
		}
	}
	
	//The textarea for OwnPanel
	JTextArea ownPanelText;
	
	public void redrawOwnPanel()
	{
		System.out.println("redrawText() is reached");
		ownPanelText.setFont(new Font("Verdana", Font.BOLD, 14));
		//removeAll();
		String s = "\n";
		for (int i = 0; i < game.player.getShares().size(); i++)
		{
			Company c = game.market.searchComp(game.player.getOwns().get(i));
			//if ((i%3) == 1)
				//s+="\n";
			String value = String.format("    Share Value: %4.2f", c.getV());
			s+="Company: " + c.getName() + value + "    Shares: " + game.player.getShares().get(i) + "\n";
		}
		System.out.println("String which should be displayed: " + s);
		ownPanelText.setText(s);
		//text.repaint();
	}
	
	class OwnPanel extends JPanel
	{
		//private int SHARE_SIZE = 45;
		
		public OwnPanel()
		{
			setBackground(Color.WHITE);
			setLayout(new GridLayout(1,0));
			String s = "";
			for (int i = 0; i < game.player.getShares().size(); i++)
			{
				Company c = game.market.searchComp(game.player.getOwns().get(i));
				//if ((i%3) == 1)
					//s+="\n";
				String value = String.format("    Share Value: %4.2f", c.getV());
				s+="Company: " + c.getName() + value + "    Shares: " + game.player.getShares().get(i) + "\n";
			}
			ownPanelText = new JTextArea(s);
			ownPanelText.setFont(new Font("Verdana", Font.BOLD, 24));
			ownPanelText.setEditable(false);
			add(ownPanelText);
			//for (int i = 0; i < 15; i++)
				//add(new Share(150000000, game.market.getComps().get(25),95, 60));
			//add(new Share(15, game.market.getComps().get(0),40, 50));
			//add(new SharesContainer(game.player));
			//p.add(new Share(game.market.getComps().get(0)));
			//JScrollBar scroll = new JScrollBar(JScrollBar.HORIZONTAL, 30, 20, 0, 300);
			//add(scroll);
			//add(new JTextArea("Shares"));
			//add(new Share(game.market.getComps().get(0)));
		}
		
		/*
		public void paint(Graphics g)
		{
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 598, 208);
			g.setColor(Color.BLACK);
			for (int j = 0; j < 3; j++)
				for (int i = 0; i < 6; i++)
					g.drawRect(i * 99, j * 68, 99, 68);
			g.setFont(new Font("Verdana", Font.BOLD, 18));
			g.drawString("Next", 522, 175);
			
			
			for (int j = 0; j < 3; j++)
				for (int i = 0; i < 6; i++)
				{
					//Dont draw in last box, the next box
					if (j * i == 10)
						return;
					//If index is greater than number of different companies invested in, stop
					if ((j*6 + i) >= game.player.getOwns().size())
						return;
					Company c = game.market.searchComp(game.player.getOwns().get((j*6) + i));
					g.setFont(new Font("Verdana", Font.BOLD, 14));
					g.drawString(c.getName(), 7, 18); //Draw Name
					String value = String.format("$%4.2f", c.getV());
					g.drawString(value, 7, 37); //Draw company value
					
					double change = c.change();
					if (change < 0)
						g.setColor(Color.RED);
					else
						g.setColor(new Color(0,150,0));
						//g.setColor(Color.GREEN);
					String change2 = String.format("%2.2f", c.change());
					g.drawString(change2, 7, 55); //Draw change
					
					g.setColor(Color.MAGENTA);
					g.setFont(new Font("Verdana", Font.BOLD, 14));
					g.drawString("x" + game.player.getShares(c.getName()), 50, 18);
				}
			//System.out.println("OwnPanel size: " + this.getSize());
		}
		*/
		/*
		class SharesContainer extends JPanel
		{
			Player p;
			private int scrL, scrR;
			public SharesContainer(Player p)
			{
				this.p = p;
				
				scrL = 0;
				scrR = p.getShares().size()/8 + 1;
				
				BorderLayout b = new BorderLayout();
				b.setHgap(0);
				b.setVgap(0);
				setLayout(new BorderLayout());
				JTextField a = new JTextField("Shares");
				a.setEditable(false);
				a.setBackground(Color.WHITE);
				a.setFont(new Font("Verdana", Font.BOLD, 16));
				a.setHorizontalAlignment(JTextField.CENTER);
				
				add(new Center(game.player), BorderLayout.CENTER);
				
				add(a, BorderLayout.NORTH);
				add(new Left(), BorderLayout.WEST);
				//add(new Right(), BorderLayout.EAST);
			}
			class Center extends JPanel
			{
				Player p;
				public Center(Player p)
				{
					this.p = p;
				}
				public void paint(Graphics g)
				{
					//Draw top 4 shares
					for (int i = 0; i < 4; i++)
					{
						int index = scrL * 8 + i;
						if (index >= p.getOwns().size())
							return;
						Company c = game.market.searchComp(p.getOwns().get(index));
						
						int w=95,h=60;
						
						g.setColor(Color.BLACK);
						g.drawRoundRect(0, 0, w, h, 5, 5);
						
						g.setFont(new Font("Verdana", Font.BOLD, 14));
						g.drawString(c.getName(), 7, 18); //Draw Name
						String value = String.format("$%4.2f", c.getV());
						g.drawString(value, 7, 37); //Draw company value
						
						double change = c.change();
						if (change < 0)
							g.setColor(Color.RED);
						else
							g.setColor(new Color(0,150,0));
							//g.setColor(Color.GREEN);
						String change2 = String.format("%2.2f", c.change());
						g.drawString(change2, 7, 55); //Draw change
						
						g.setColor(Color.MAGENTA);
						g.setFont(new Font("Verdana", Font.BOLD, 14));
						g.drawString("x" + p.getShares(c.getName()), 50, 18);
					}
					System.out.println("code reached");
					//Draw bottom 4 shares
					for (int i = 0; i < 4; i++)
					{
						int index = scrL * 8 + i + 4;
						Company c = game.market.searchComp(p.getOwns().get(index));
						if (c == null)
							return;
						
						int w=95,h=60;
						
						g.setColor(Color.BLACK);
						g.drawRoundRect(0, 0, w, h, 5, 5);
						
						g.setFont(new Font("Verdana", Font.BOLD, 14));
						g.drawString(c.getName(), 7, 18); //Draw Name
						String value = String.format("$%4.2f", c.getV());
						g.drawString(value, 7, 37); //Draw company value
						
						double change = c.change();
						if (change < 0)
							g.setColor(Color.RED);
						else
							g.setColor(new Color(0,150,0));
							//g.setColor(Color.GREEN);
						String change2 = String.format("%2.2f", c.change());
						g.drawString(change2, 7, 55); //Draw change
						
						g.setColor(Color.MAGENTA);
						g.setFont(new Font("Verdana", Font.BOLD, 14));
						g.drawString("x" + p.getShares(c.getName()), 50, 18);
					}
				}
			}
			class Left extends JPanel implements MouseListener
			{
				private int highlight = 0;
				public Left()
				{
					addMouseListener(this);
				}
				public void paint(Graphics g)
				{
					if (highlight == 0)
					{
						g.setColor(Color.WHITE);
						g.fillRect(0, 0, 15, 1000);
						
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, this.getWidth()-1, this.getHeight()-1);
						g.drawLine(7, 68, 1, 88);
						g.drawLine(1, 88, 7, 108);
						g.drawLine(7, 108, 7, 68);
					}
					else
					{
						g.setColor(new Color(248,248,248));
						g.fillRect(0, 0, 15, 1000);
						
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, this.getWidth()-1, this.getHeight()-1);
						g.drawLine(7, 68, 1, 88);
						g.drawLine(1, 88, 7, 108);
						g.drawLine(7, 108, 7, 68);
					}
				}
				
				@Override
				public void mouseClicked(MouseEvent arg0)
				{
					repaint();
				}

				@Override
				public void mouseEntered(MouseEvent arg0)
				{
					highlight = 1;
					repaint();
				}

				@Override
				public void mouseExited(MouseEvent arg0)
				{
					highlight = 0;
					repaint();
					
				}

				@Override
				public void mousePressed(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
			}
		}
		class Share extends JPanel implements MouseListener
		{
			private int numShares;
			private Company c;
			private int w,h;
			public Share(int numShares, Company c, int width, int height)
			{
				this.numShares = numShares;
				this.c = c;
				w = width;
				h = height;
				
				addMouseListener(this);
			}
			public void paint(Graphics g)
			{
				g.setColor(Color.BLACK);
				g.drawRoundRect(0, 0, w, h, 5, 5);
				
				g.setFont(new Font("Verdana", Font.BOLD, 14));
				g.drawString(c.getName(), 7, 18); //Draw Name
				String value = String.format("$%4.2f", c.getV());
				g.drawString(value, 7, 37); //Draw company value
				
				double change = c.change();
				if (change < 0)
					g.setColor(Color.RED);
				else
					g.setColor(new Color(0,150,0));
					//g.setColor(Color.GREEN);
				String change2 = String.format("%2.2f", c.change());
				g.drawString(change2, 7, 55); //Draw change
				
				g.setColor(Color.MAGENTA);
				g.setFont(new Font("Verdana", Font.BOLD, 14));
				g.drawString("x" + numShares, 50, 18); //Draw numShares
			}
			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				System.out.println("CLICKED");
				int num;
				try
				{
					num = Integer.valueOf((String)prompt("How many shares would you like to sell?"));
				}
				catch (Exception e)
				{
					num = -1;
				}
				if (num < 0 || num > numShares)
				{
					message("Invalid Entry");
					return;
				}
				else
				{
					message("Transaction Complete");
				}
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		}*/
		/*
		class SharesContainer extends JPanel
		{
			Share[] shares;
			Player p;
			public SharesContainer(Player p)
			{
				this.p = p;
				shares = new Share[p.getShares().size()];
				setBackground(Color.WHITE);
				setLayout(new GridLayout(3,p.getOwns().size()/3));
			}
			public void paint(Graphics g)
			{
				for (int i = 0; i < p.getOwns().size(); i++)
					shares[i] = new Share(p.getShares().get(i), game.market.searchComp(p.getOwns().get(i)), 95,60);
				for (int i = 0; i < shares.length; i++)
					super.add(shares[i]);
				asdfaef
			}
		}*/
		/*
		class Share extends JPanel
		{
			private Company c;
			public Share(Company c)
			{
				this.c = c;
				setMaximumSize(new Dimension(75, 100));
			}
			public void paintComponent(Graphics g)
			{
				g.drawRect(0, 0, 75, 100);
			}
		}
		class sharePanel extends JPanel
		{
			public void paintComponent(Graphics g)
			{
				for (int i = 0; i < game.market.getComps().size(); i++)
					add(new Share(game.market.getComps().get(i)));
			}
		}*/
	}
	
	//Represent the left and right sides of the ASSETS and DEBTS piles of coins, proRight is the actual "text" Right side of the ProPanel
	JPanel proAnimLeft, proAnimRight, proRight;
	//Represent the 5 text areas of the right side
	JTextArea proT1, proT2, proT3, proT4, proT5;
	//Redraw the coins
	public void redrawLeftAssetsProPanel()
	{
		proAnimLeft.repaint();
		//proAnimRight.repaint();
	}
	public void redrawLeftDebtsProPanel()
	{
		System.out.println("DEBTS REDRAW CALLED");
		proAnimRight.repaint();
	}
	public void redrawRightProPanel()
	{
		proT1.setText(" " + game.player.getName() + "'s Portfolio");
		proT2.setText("  Capital: " + game.player.getCapital());
		proT3.setText("  Net Value: " + game.player.getNetValue(game.market));
		proT4.setText("  Debt: " + game.player.getDebt());
		proT5.setText("  Interest Rate on Debt: " + game.player.getIntRate());
	}
	class ProPanel extends JPanel
	{
		class AssetDebtAnimation extends JPanel
		{
			Game game;
			public AssetDebtAnimation(Game game)
			{
				this.game = game;
				//this.setBackground(Color.black);
				setLayout(new GridLayout(0,2));
				add(proAnimLeft = new left());
				add(proAnimRight = new right());
			}
			class left extends JPanel
			{
				public void paintComponent(Graphics g)
				{
					System.out.println("DIMENSIONS: " + this.getSize());
					g.setColor(Color.WHITE);
					g.fillRect(0, 0, 250, 250);
					g.setColor(Color.BLACK);
					g.setFont(new Font("Verdana", Font.BOLD, 18));
					g.drawString("ASSETS", 40, 175);
					//System.out.println(game.player.sumAssets(game.market));
					int M =  (int) game.player.sumAssets(game.market)/1000;
					int maxIncX = (int) (M/1.2);
					if (maxIncX > 128)
						maxIncX = 127;
					int maxIncY = (int) (M/6);
					if (maxIncY > 135)
						maxIncY = 135;
					if (maxIncX < 2)
						return;
					for (int i = 0; i < M; i++)
					{
						g.setColor(Color.YELLOW);
						int x = 10 + gen.nextInt(maxIncX);
						int y = 135-gen.nextInt(maxIncY);
						g.fillOval(x, y, 10, 7);
						g.setColor(Color.BLACK);
						g.drawOval(x, y, 10, 7);
					}
				}
			}
			class right extends JPanel
			{
				public void paintComponent(Graphics g)
				{
					System.out.println("DIMENSIONS: " + this.getSize());
					g.setColor(Color.WHITE);
					g.fillRect(0, 0, 250, 250);
					g.setColor(Color.BLACK);
					g.setFont(new Font("Verdana", Font.BOLD, 18));
					g.drawString("DEBTS", 35, 175);
					//System.out.println(game.player.sumAssets(game.market));
					int M = (int) game.player.getDebt()/1000;
					System.out.println("M value: " + M);
					int maxIncX = (int) (M/1.2);
					if (maxIncX > 128)
						maxIncX = 127;
					int maxIncY = (int) (M/6);
					if (maxIncY > 135)
						maxIncY = 135;
					if (maxIncX < 2)
						return;
					for (int i = 0; i < M; i++)
					{
						g.setColor(Color.RED);
						int x = 10 + gen.nextInt(maxIncX);
						int y = 135-gen.nextInt(maxIncY);
						g.fillOval(x, y, 10, 7);
						g.setColor(Color.BLACK);
						g.drawOval(x, y, 10, 7);
					}
				}
			}
			
		}
		public ProPanel()
		{
			setBackground(Color.WHITE);
			setLayout(new GridLayout(0,2));
			
			AssetDebtAnimation left = new AssetDebtAnimation(game);
			left.setBackground(Color.BLACK);
			add(left);
			
			proRight = new JPanel();
			proRight.setLayout(new GridLayout(5,0));
			proRight.setBackground(Color.WHITE);
			
			proT1 = new JTextArea(" " + game.player.getName() + "'s Portfolio");
			proT1.setFont(new Font("Verdana", Font.BOLD, 18));
			proT1.setEditable(false);
			proRight.add(proT1);
			proT2 = new JTextArea("  Capital: " + game.player.getCapital());
			proT2.setFont(new Font("Verdana", Font.BOLD, 12));
			proT2.setEditable(false);
			//right.add(bankLabel);
			proRight.add(proT2);
			
			proT3 = new JTextArea("  Net Value: " + game.player.getNetValue(game.market));
			proT3.setFont(new Font("Verdana", Font.BOLD, 12));
			proT3.setEditable(false);
			proRight.add(proT3);
			
			proT4 = new JTextArea("  Debt: " + game.player.getDebt());
			proT4.setFont(new Font("Verdana", Font.BOLD, 12));
			proT4.setEditable(false);
			proRight.add(proT4);
			
			proT5 = new JTextArea("  Interest Rate on Debt: " + game.player.getIntRate());
			proT5.setFont(new Font("Verdana", Font.BOLD, 12));
			proT5.setEditable(false);
			proRight.add(proT5);
			
			add(proRight);
		}
	}
	
	private TimePanel TimeP;
	private JPanel Investors;
	private JPanel Bank;
	private JPanel Market;
	private OwnPanel Owns;
	private JPanel Profile;
	
	public TimePanel getTimeP() {
		return TimeP;
	}
	public JPanel getInvestors() {
		return Investors;
	}
	public JPanel getBank() {
		return Bank;
	}
	public JPanel getMarket() {
		return Market;
	}
	public OwnPanel getOwns() {
		return Owns;
	}
	public JPanel getProfile() {
		return Profile;
	}
	public GamePanel(Game g) throws IOException 
	{
		//UIManager.getDefaults().put("JTextArea.editable", false);
		//UIManager.put("TextField.inactiveBackground", false);
		//UIManager.put("TextField.editableBackground", false);
		//UIManager.put("JTextField., arg1)
		
		game = g;
		setSize(1200,650);
		//setBackground(Color.WHITE);
		setLayout(new GridLayout(3,2));
		
		
		TimeP = new TimePanel();
		Investors = new InvPanel();
		Bank = new BankPanel();
		Market = new MarkPanel();
		Owns = new OwnPanel();
		Profile = new ProPanel();
		
		add(TimeP);
		add(Profile);
		add(Bank);
		add(Market);
		add(Investors);
		add(Owns);
		
		
		
		//System.out.println(prompt("Enter Amount Desired"));
		
		/*
		 * 
		 * This is test code using SplitPane as primary
		 * Doesn't display right
		 * JPanel[] panels = new JPanel[6];
		for (int i = 0; i < 6; i++)
			add(panels[i] = new JPanel())
		JPanel left = new JPanel(new GridLayout(2,0));
		left.add(Advisors);
		left.add(Investors);
		left.add(Bank);
		left.setSize(500,500);
		
		//JPanel right = new JPanel(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT))
		JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left,new JTextArea("HELLO"));
		add(sp);
		System.out.println(this.getSize());
		*/
		
		/*
		 * This is test code using BorderLayout as primary
		setLayout(new BorderLayout(5,5));
		add(b, BorderLayout.PAGE_START);
		add(new JPanel(), BorderLayout.CENTER);
		add(new JPanel(), BorderLayout.LINE_START);
		add(new JPanel(), BorderLayout.PAGE_END);
		add(new JPanel(), BorderLayout.LINE_END);
		*/
	}
	 
	/*
	 * A runnable object is passed into a thread as the process to be executed.
	 * You can create the thread in the same class as the runnable, which
	 * is what usually happens.
	 */
	//private Thread animator;
	
	/*
	 * This determines the interval between redraws in the thread.
	 * For this game, the lapse interval will be 15 seconds
	 */
	//private final int DELAY = 1000;
	
	/*
	 * This function is called after the JPanel is added to the frame, and 
	 * is used to initialize the JPanel.
	public void addNotify()
	{
        super.addNotify();
        animator = new Thread(this);
        animator.start();
    }
	*/
	/*
	@Override
	public void run()
	{
		//game.market.Lapse();
		
		long beforeTime, timeDiff, sleep;
		
		//Start time
        beforeTime = System.currentTimeMillis();

        //The thread continues to run
        while (true)
        {
            //repaint();

            //Time lapse  between current time and start time
            timeDiff = System.currentTimeMillis() - beforeTime;
            //Amount of time the thread needs to sleep is delay - time lost already from previous draw
            sleep = DELAY - timeDiff;

            if (sleep < 0)
                sleep = 2;
            try
            {
            	//Thread waits until next execution
                Thread.sleep(sleep);
            }
            catch (InterruptedException e)
            {
                System.out.println("interrupted");
            }

            //Find new start time
            beforeTime = System.currentTimeMillis();
        }
	}*/
	
	private int tRate; //Represents the rate of time in milliseconds
	
	/*
	 * This class has be outside of other classes
	 * in order to be able to call repaint() on everything
	 */
	class updateClockAction implements ActionListener
	{
		GamePanel gameP;
		//ClockPanel p; //Original code, switched out for GamePanel to update everything
		public updateClockAction(GamePanel p)
		{
			this.gameP = p;
			//p.setTime((int)p.minutes+1);
		}
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			
			//The following is working code
			ClockPanel cP = gameP.getTimeP().getClockPanel();
			cP.setTime((int)cP.getMinutes()+1);
			cP.getPanel().repaint();
			if ((cP.getMinutes()%60) == 0)
			{
				//OwnPanel oP = gameP.getOwns();
				gameP.redrawOwnPanel();
				gameP.redrawMarkLeft();
				gameP.game.market.Lapse();
			}
			System.out.println(cP.getMinutes());
		}
		
	}

	Timer t; //This is the timer responsible for the update
	
	public void setTimerRate(int rate)
	{
		t.setDelay(rate);
	}
	
	class ClockPanel extends JPanel
	{
		private java.awt.Image backgroundImage;
		private double minutes = 0;
		private int RADIUS = 100;
		private double MINUTE_HAND_LENGTH = 0.8 * RADIUS;
		private Market m;
		private JPanel panel;
		//private double HOUR_HAND_LENGTH = 0.6 * RADIUS;
		public JPanel getPanel()
		{
			return panel;
		}
		public int getMinutes()
		{
			System.out.println("HERE " + minutes);
			return (int)minutes;
		}
		
		public ClockPanel(Market m, JPanel p, GamePanel gamePanel) throws IOException {
			this.m = m;
			panel = p; //Represents the left half of the TimePanel, which has the "Seconds Left: " text
			this.setBackground(Color.WHITE);
			setPreferredSize(new Dimension(2 * RADIUS + 1, 2 * RADIUS + 1));
			backgroundImage = ImageIO.read(new File("big-analog-clock.gif"));
			
			ActionListener l = new updateClockAction(gamePanel);
			//tRate = 250;
			t = new Timer(250, l);
			t.start();
		}
		

		public void paintComponent(Graphics g) {
			// draw the circular boundary

			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.drawImage(backgroundImage.getScaledInstance(2 * RADIUS + 1, 2 * RADIUS + 1, Image.SCALE_AREA_AVERAGING), 50, 0, null);
			Ellipse2D circle = new Ellipse2D.Double(50, 0, 2 * RADIUS, 2 * RADIUS);
			g2.draw(circle);

			// draw the hour hand

			//double hourAngle = Math.toRadians(90 - 360 * minutes / (12 * 60));
			//drawHand(g2, hourAngle, HOUR_HAND_LENGTH);

			// draw the minute hand

			double minuteAngle = Math.toRadians(90 - 360 * minutes / 60);
			drawHand(g2, minuteAngle, MINUTE_HAND_LENGTH);
		}

		public void drawHand(Graphics2D g2, double angle, double handLength) {
			Point2D end = new Point2D.Double(RADIUS + 50 + handLength * Math.cos(angle),
					RADIUS - handLength * Math.sin(angle));
			Point2D center = new Point2D.Double(RADIUS + 50, RADIUS);
			g2.draw(new Line2D.Double(center, end));
		}

		/**
		 * Set the time to be displayed on the clock
		 * Also redraws this panel
		 * 
		 * @param h hours
		 * @param m minutes
		 */
		public void setTime(int m) {
			minutes = m;
			repaint();
		}


	}
}


