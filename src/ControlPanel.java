import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class ControlPanel extends JPanel {
	private String[] data;
	protected String[] nameSortData;
	protected String[] rankSortData;

	public ControlPanel(ArrayList<String> names) {

		this.data = names.toArray(new String[names.size()]);
		names.sort(null);
		this.nameSortData = names.toArray(new String[names.size()]);
		names.sort(new rankCompare());
		this.rankSortData = names.toArray(new String[names.size()]);
		this.addDataPanel();
	}

	public void addDataPanel() {

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// ***************** Main Data List *******************
		JList<Object> dataList = new JList<>(this.nameSortData);
		dataList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dataList.setLayoutOrientation(JList.VERTICAL);
		dataList.setVisibleRowCount(5);
		dataList.setFont(new Font("Arial", Font.PLAIN, 18));
		dataList.setEnabled(false);

		JScrollPane dataListScroller = new JScrollPane(dataList);

		JLabel title = new JLabel("List of Values");
		title.setFont(new Font("Sans Serif", Font.BOLD, 24));

		// ************* Radio Buttons and Sorting ***************
		ButtonGroup sortOptions = new ButtonGroup();

		class RadioButtonListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				String action = e.getActionCommand();
				if (action.equals("sortByName"))
					dataList.setListData(ControlPanel.this.nameSortData);
				else
					dataList.setListData(ControlPanel.this.rankSortData);
			}
		}

		JRadioButton rank = new JRadioButton("Sort by ranking", false);
		rank.setActionCommand("sortByRank");
		rank.addActionListener(new RadioButtonListener());
		sortOptions.add(rank);

		JRadioButton name = new JRadioButton("Sort by name", true);
		name.setActionCommand("sortByName");
		name.addActionListener(new RadioButtonListener());
		sortOptions.add(name);

		// **************** Adding Components ******************
		this.add(title);
		this.add(dataListScroller);
		this.add(name);
		this.add(rank);

	}

	protected String[] sort(Comparator<? super String> c) {
		ArrayList<String> sortThis = new ArrayList<>();
		for (String s : this.data)
			sortThis.add(s);
		if (c == null)
			sortThis.sort(null);
		else
			sortThis.sort(c);

		return sortThis.toArray(new String[sortThis.size()]);
	}

	class rankCompare implements Comparator<String> {
		// TODO: replace with actual rank sorting
		@Override
		public int compare(String o1, String o2) {
			if (o1.length() == o2.length())
				return 0;
			if (o1.length() < o2.length())
				return 1;
			return -1;
		}

	}

}
