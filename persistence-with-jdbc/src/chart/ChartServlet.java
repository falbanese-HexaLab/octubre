package chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import com.sap.cloud.sample.persistence.Person;
import com.sap.cloud.sample.persistence.PersonDAO;
import com.sap.security.core.server.csi.IXSSEncoder;
import com.sap.security.core.server.csi.XSSEncoder;
import com.sap.cloud.sample.persistence.Person;
import com.sap.cloud.sample.persistence.PersonDAO;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ChartServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private PersonDAO personDAO;
	
	 /** {@inheritDoc} */
   @Override
   public void init() throws ServletException {
       try {
           InitialContext ctx = new InitialContext();
           
           DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DefaultDB");
           personDAO = new PersonDAO(ds);
       
       } catch (SQLException e) {
           throw new ServletException(e);
       } catch (NamingException e) {
           throw new ServletException(e);
       }
   }
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("image/png");
		OutputStream out = response.getOutputStream();
		JFreeChart chart = getPersonPieChart();
		int width = 500;
		int height = 350;
		ChartUtilities.writeChartAsPNG(out, chart, width, height);
		
		// DROP DOWN LIST: Select and redirect to required url
	}
	
public JFreeChart getPersonPieChart() {
		
		DefaultPieDataset dataset = new DefaultPieDataset();
		boolean legend = true;
		boolean tooltips = false;
		boolean urls = false;
		JFreeChart chart = ChartFactory.createPieChart("Persons", dataset, legend, tooltips, urls);
		chart.setBorderPaint(Color.GREEN);
		chart.setBorderStroke(new BasicStroke(5.0f));
		chart.setBorderVisible(true);
		
		try {
			List<Person> resultList;
			resultList = personDAO.selectAllPersons();
			Float percentage_for_each_person = new Float(resultList.size());
			for (Person p : resultList) {
				dataset.setValue(p.getFirstName(), 
						percentage_for_each_person);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return chart;
	}
   
}