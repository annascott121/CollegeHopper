import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;
import javax.swing.ListSelectionModel;

public class ListPanel extends ControlPanel {

	public ListPanel(ArrayList<String> data) {
		super(data);
	}

	@Override
	public void addDataPanel() {
		// ********* Panel Setup Section **********
		this.nameSortData = this.sort(null);
		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateContainerGaps(true);
		this.setLayout(layout);

		// ********* Start Location Section **********
		JList<String> startList = new JList<>(this.nameSortData);
		startList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		startList.setLayoutOrientation(JList.VERTICAL);
		startList.setVisibleRowCount(5);
		startList.setFont(new Font("Arial", Font.PLAIN, 18));
		startList.setSelectedIndex(0);

		JScrollPane startListScroller = new JScrollPane(startList);

		JLabel start = new JLabel("Pick a Start Location");
		start.setFont(new Font("Sans Serif", Font.BOLD, 24));

		// ********** End Location Section ***********
		JList<String> endList = new JList<>(this.nameSortData);
		endList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		endList.setLayoutOrientation(JList.VERTICAL);
		endList.setVisibleRowCount(5);
		endList.setFont(new Font("Arial", Font.PLAIN, 18));
		endList.setSelectedIndex(0);

		JScrollPane endListScroller = new JScrollPane(endList);

		JLabel end = new JLabel("Pick an End Location");
		end.setFont(new Font("Sans Serif", Font.BOLD, 24));

		// ********** Cost Function Section **********
		JLabel cost = new JLabel("Show routes with:");
		cost.setFont(new Font("Sans Serif", Font.BOLD, 24));
		JPanel costFuncSect = new JPanel();
		costFuncSect
				.setLayout(new BoxLayout(costFuncSect, BoxLayout.PAGE_AXIS));
		JCheckBox distance = new JCheckBox("Shortest Distance");
		costFuncSect.add(distance);
		JCheckBox time = new JCheckBox("Shortest Time");
		costFuncSect.add(time);
		JCheckBox avgRating = new JCheckBox("Show average Ratings");
		costFuncSect.add(avgRating);

		// ********** Go/Start Button Section **********
		JButton go = new JButton("GO!");
		go.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: change to JLabels on ResultsPanel
				// TODO: add checkbox values/functions
				String startValue = startList.getSelectedValue();
				String endValue = endList.getSelectedValue();
				if (startValue == null || endValue == null) // Shouldn't happen
					System.out.println("Please make a selection!");
				else
					System.out.println(startValue + " to " + endValue);
			}
		});

// ******************* Adding and Formatting **********************************
		layout.setHorizontalGroup(layout
				.createSequentialGroup()
				.addGroup(
						layout.createParallelGroup(GroupLayout.Alignment.CENTER)
								.addComponent(start)
								.addComponent(startListScroller))
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(
						layout.createParallelGroup(GroupLayout.Alignment.CENTER)
								.addComponent(end)
								.addComponent(endListScroller))
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(
						layout.createParallelGroup(GroupLayout.Alignment.CENTER)
								.addComponent(cost).addComponent(costFuncSect))
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(go));
		layout.setVerticalGroup(layout
				.createSequentialGroup()
				.addGroup(
						layout.createParallelGroup(GroupLayout.Alignment.CENTER)
								.addComponent(start).addComponent(end)
								.addComponent(cost))
				.addGroup(
						layout.createParallelGroup(GroupLayout.Alignment.CENTER)
								.addComponent(startListScroller)
								.addComponent(endListScroller)
								.addComponent(costFuncSect).addComponent(go)));
	}

}
