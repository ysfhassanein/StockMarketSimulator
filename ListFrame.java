
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ListFrame extends JFrame
{
	private Market m;
	public ListFrame(Market m)
	{
		this.m = m;
		
		/*Object[] a = m.getComps().toArray();
		int[] values = new int[a.length];
		for (int i = 0; i < a.length; i++)
			values[i] = Integer.valueOf(String.valueOf(a[i]));
		Sort(values);
		Company[] c = m.getComps();
		*/
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 700);
        setLocationRelativeTo(null);
        setTitle("Company List");
        setResizable(false);
        setVisible(true);
        
        JTextArea area = Display();
        JScrollPane scrollPane = new JScrollPane(area);
        add(scrollPane);
	}
	private JTextArea Display()
	{
		JTextArea a;
		int rows = m.getComps().size();
		String text = "  \n Companies\n\n Shows Name, Current Value, Change\n\n";
		for (int i = 0; i < rows; i++)
		{
			text+=String.format("  %3s\t%3.2f\t%3.2f\n", m.getComps().get(i).getName(),m.getComps().get(i).getV(),m.getComps().get(i).change());
			text+="_______________________________________\n";
		}
		a = new JTextArea(text);
		return a;
	}
	
	/*
	private static int[] newArr;
	private static int[] orig;
	public static int[] Sort(int[] a)
	{
		orig = a;
	    newArr = new int[a.length];
	    mergesort(0, a.length - 1);
	    return newArr;
	}
	private static void mergesort(int low, int high)
	{
	    if (low < high)
	    {
	      int middle = low + (high - low) / 2;
	      mergesort(low, middle);
	      mergesort(middle + 1, high);
	      merge(low, middle, high);
	    }
	  }

	  private static void merge(int low, int middle, int high)
	  {
	    // Copy both parts into the helper array
	    for (int i = low; i <= high; i++)
	    {
	      newArr[i] = orig[i];
	    }
	
	    int i = low;
	    int j = middle + 1;
	    int k = low;

	    while (i <= middle && j <= high)
	    {
	      if (newArr[i] <= newArr[j])
	      {
	        orig[k] = newArr[i];
	        i++;
	      }
	      else
	      {
	        orig[k] = newArr[j];
	        j++;
	      }
	      k++;
	    }
	    
	    while (i <= middle)
	    {
	      orig[k] = newArr[i];
	      k++;
	      i++;
	    }
	
	  }
	  */
}
