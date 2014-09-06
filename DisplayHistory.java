
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class DisplayHistory extends JFrame
{
	JFreeChart c;
	int type;
	
	public DisplayHistory(Company comp, int lap, int type)
	{
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setTitle("Company List");
        setResizable(false);
        setVisible(true);
        
        this.type = type;
        final XYDataset dataset = createDataSet(comp, lap, type);
        createChart(dataset);
	}
	
	public void paint(Graphics g)
    {
    	super.paint(g);
    	Graphics2D g2d = (Graphics2D)g;
    	c.draw(g2d, new Rectangle(this.size()));
    }
	
	private XYDataset createDataSet(Company comp, int lap, int type)
	{
		final XYSeriesCollection dataset = new XYSeriesCollection();
		
		if (type == 1)
		{
			final XYSeries series1 = new XYSeries("Share Values");
			for (int i = 0; i < lap; i++)
				series1.add(i, comp.getH()[i]);
			
			dataset.addSeries(series1);
		}
		else
		{
			//final XYSeries empSeries = new XYSeries();
			final XYSeries series2 = new XYSeries("Share Value Changes (x100)");
	        for (int i = 1; i < lap; i++)
	        {
	        	series2.add(i, comp.change(i) * 100);
	        	System.out.println("LAP " + i + ": " + comp.change(lap));
	        	comp.ListChanges();
	        }
	        
	        dataset.addSeries(series2);
		}
        
        //dataset.addSeries(series3);
                
        return dataset;
	}
	
	private void createChart(final XYDataset dataset)
	{
		String y;
		if (type == 1)
			y="Share Value ($)";
		else
			y="Share Value Change (x100)";
		
        final JFreeChart chart = ChartFactory.createXYLineChart(
            "Company History",      // chart title
            "Time (15s lapse intervals)",                      // x axis label
            y,                      // y axis label
            dataset,                  // data
            PlotOrientation.VERTICAL,
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.white);

//        final StandardLegend legend = (StandardLegend) chart.getLegend();
  //      legend.setDisplaySeriesShapes(true);
        
        // get a reference to the plot for further customisation...
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE);
        
    //    plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        
        //final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        //renderer.setSeriesLinesVisible(0, false);
        //renderer.setSeriesShapesVisible(1, false);
        //plot.setRenderer(renderer);
        
        plot.getRenderer().setSeriesStroke(
                0, 
                new BasicStroke(
                    2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 
                    1.0f, new float[] {10.0f, 6.0f}, 0.0f
                )
            );

        // change the auto tick unit selection to integer units only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        // OPTIONAL CUSTOMISATION COMPLETED.
                
        c = chart;
    }
}
