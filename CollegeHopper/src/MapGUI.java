import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class MapGUI {
//	MGraph g;

	public static void main(String[] args) {
	// TODO: split sections into methods?

		// Set main frame and properties
//		g = new MGraph();
		JFrame map = new JFrame("CollegeHopper");
		map.setSize(new Dimension(800, 500));
		map.setLayout(new GridBagLayout());
		GridBagConstraints layout = new GridBagConstraints();

		ImageIcon frameLogo = new ImageIcon("capIcon.png");
		Image logo = frameLogo.getImage();
		map.setIconImage(logo);

		// Get and store data from text file
		File file = new File("Data");
		Scanner s = null;
		String name = null;
		ArrayList<String> data = new ArrayList<>();
		double x = 0;
		double y = 0;
		// TODO: add ranking info for Graph

        try {
            s = new Scanner(file);
            s.useDelimiter(Pattern.compile("/"));

            while (s.hasNext()) {
                name = s.next();
                data.add(name);
            	x = Double.parseDouble(s.next());
            	y = Double.parseDouble(s.next());
//            	g.addPlace(name, x, y); TODO: add Place implementation
            	s.nextLine();
            	
            }
        } catch (FileNotFoundException exception) {
			System.out.println("File not found!");
		} finally {
            if (s != null)
                s.close();
        }
		
		// Format and add main list
		layout.fill = GridBagConstraints.HORIZONTAL;
		layout.gridx = 0;
		layout.gridy = 0;
		layout.ipady = 350;
		layout.gridheight = 2;
		map.add(new ControlPanel(data), layout);

		// Format and add options area
		layout.fill = GridBagConstraints.HORIZONTAL;
		layout.ipady = 0; // reset to default
		layout.weighty = 1.0; // request any extra vertical space
		layout.anchor = GridBagConstraints.PAGE_END; // bottom of space
		layout.gridx = 1; // aligned with end of main list
		layout.gridwidth = 3; // 3 columns wide
		layout.gridy = 1;
		map.add(new ListPanel(data), layout);

		// Format and add results area
		layout.fill = GridBagConstraints.HORIZONTAL;
		layout.gridx = 1;
		layout.gridy = 0;
		layout.ipady = 300;
		map.add(new ResultsPanel(), layout);

		// Final frame set-up
		map.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		map.pack();
		map.setResizable(false);
		map.setLocationRelativeTo(null);
		map.setVisible(true);
	}

}
