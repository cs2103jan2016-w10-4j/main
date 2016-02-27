package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

public class UserInterface{
	
	public static void main(String[] args){
		initComponents();
	}
    private static void initComponents() {
    	Parser p = new Parser();
		JFrame f = new JFrame("Docket");
		JTextField cmdEntry = new JTextField();
		JTextPane outputDisplay = new JTextPane();
		JTextArea cmdDisplay = new JTextArea();
		JLabel status = new JLabel();
		JLabel jLabel1 = new JLabel();
		
		setIcon(f);

		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		textAreaSettings(cmdDisplay);
		textPaneSettings(outputDisplay);
		
		JScrollPane jScrollPane1 = new JScrollPane(cmdDisplay);
		JScrollPane jScrollPane2 = new JScrollPane(outputDisplay);
		jLabel1.setText("command: ");

		GroupLayout layout = new GroupLayout(f.getContentPane());
		f.getContentPane().setLayout(layout);

		// Create a sequential group for horizontal axis
		setHoriGroup(layout, jLabel1, cmdEntry, jScrollPane1, jScrollPane2, status);

		// Create a parallel group for the vertical axis
		setVertGroup(layout, jScrollPane1, jScrollPane2, jLabel1, status, cmdEntry);
		
		setWelcomeMessage(outputDisplay);
		
		action(p, cmdEntry, cmdDisplay, outputDisplay);
		
		f.pack();
		f.setVisible(true);
    }
    
	private static void setIcon(JFrame f) {
		// Random icon image grabbed from web. Should change
				String icon = ".\\main\\icon\\d.jpg";
				ImageIcon img = new ImageIcon(icon);
				f.setIconImage(img.getImage());
	}
    
    public static void textAreaSettings(JTextArea cmdDisplay){
    	cmdDisplay.setColumns(20);
    	cmdDisplay.setLineWrap(true);
    	cmdDisplay.setRows(5);
    	cmdDisplay.setWrapStyleWord(true);
    	cmdDisplay.setEditable(false);
    	cmdDisplay.setFocusable(false);
    	cmdDisplay.setBackground(Color.BLACK);
    	cmdDisplay.setForeground(Color.WHITE);
    	cmdDisplay.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
    }
    
    public static void textPaneSettings(JTextPane outputDisplay){
    	outputDisplay.setEditable(false);
    	outputDisplay.setFocusable(false);
    	outputDisplay.setContentType("text/html");
    }
    
    public static void horiGroup3(GroupLayout.SequentialGroup h3, GroupLayout layout, JLabel jLabel1, JTextField cmdEntry){
		// Create a sequential group h3
		h3.addComponent(jLabel1);
		h3.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
		h3.addComponent(cmdEntry, GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE);
    }
    
    public static void horiGroupHp(GroupLayout.ParallelGroup hp, JScrollPane jScrollPane2, JScrollPane jScrollPane1, JLabel status){
		// Add a scroll pane and a label to the parallel group hp
		hp.addComponent(jScrollPane2, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE);
		hp.addComponent(status, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE);
		hp.addComponent(jScrollPane1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE);    	
    }
    
    public static void setHoriGroup(GroupLayout layout, JLabel jLabel1, JTextField cmdEntry, JScrollPane jScrollPane1, JScrollPane jScrollPane2, JLabel status){
		// Create a parallel group for the horizontal axis
		GroupLayout.ParallelGroup hGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
		
    	// Create a sequential and a parallel groups
		GroupLayout.SequentialGroup hs = layout.createSequentialGroup();
		GroupLayout.ParallelGroup hp = layout.createParallelGroup(GroupLayout.Alignment.TRAILING);

		// Add a container gap to the sequential group h1
		hs.addContainerGap();

		// Create a sequential group h3
		GroupLayout.SequentialGroup h3 = layout.createSequentialGroup();
		horiGroup3(h3, layout, jLabel1, cmdEntry);

		// Add the group h3 to the group hp
		hp.addGroup(h3);
		
		// Add a scroll pane and a label to the parallel group hp
		horiGroupHp(hp, jScrollPane2, jScrollPane1, status);
		
		// Add the group h2 to the group h1
		hs.addGroup(hp);

		hs.addContainerGap();

		// Add the group h1 to the hGroup
		hGroup.addGroup(GroupLayout.Alignment.TRAILING, hs);
		// Create the horizontal group
		layout.setHorizontalGroup(hGroup);
    }
    
    public static GroupLayout.SequentialGroup vertGroup1(GroupLayout layout, JScrollPane jScrollPane2, JScrollPane jScrollPane1, JLabel status, JLabel jLabel1, JTextField cmdEntry){
		GroupLayout.SequentialGroup v1 = layout.createSequentialGroup();
		v1.addContainerGap();
		
		GroupLayout.ParallelGroup v2 = vertGroup2(layout, jLabel1, cmdEntry);
		
		v1.addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE);
		v1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
		v1.addComponent(status);
		v1.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE);
		v1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
		v1.addGroup(v2);
		v1.addContainerGap();
		
		return v1;
    }
    
    public static GroupLayout.ParallelGroup vertGroup2(GroupLayout layout, JLabel jLabel1, JTextField cmdEntry){
		GroupLayout.ParallelGroup v2 = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
		v2.addComponent(jLabel1);
		v2.addComponent(cmdEntry, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
		return v2;
    }
    
    public static void setVertGroup(GroupLayout layout, JScrollPane jScrollPane1, JScrollPane jScrollPane2, JLabel jLabel1, JLabel status, JTextField cmdEntry){
    	// Create a parallel group for the vertical axis
		GroupLayout.ParallelGroup vGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
		
		GroupLayout.SequentialGroup v1 = vertGroup1(layout, jScrollPane2, jScrollPane1, status, jLabel1, cmdEntry);

		// Add the group v1 to the group vGroup
		vGroup.addGroup(v1);
		// Create the vertical group
		layout.setVerticalGroup(vGroup);
	}
    
    public static void setWelcomeMessage(JTextPane displayOutput){
    	displayOutput.setText("<center style=\"font-size:24px\"><b>Welcome to Docket! </b></center><br> "
    			+ "Docket is a simple command line Windows application that allows you to manage your events and deadlines effectively. <br><br>"
    			+ "To start, enter a task in the command field below.");
    }
    
    public static void action(Parser p, JTextField cmdEntry, JTextArea cmdDisplay, JTextPane displayOutput){
    	cmdEntry.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String s = cmdEntry.getText();
				cmdEntry.setText("");
				printInCommandDisplay(cmdDisplay, "> " + s);
				String output = p.parse(s);
				if (isDisplay(output)){
					printInDisplayOutput(displayOutput, output.substring(1));
				} else {
					printInCommandDisplay(cmdDisplay, output.substring(1));
				}
			}
		});
    }
    
    public static void printInCommandDisplay(JTextArea cmdDisplay, String content){
    	cmdDisplay.append(content + "\n");
    }
    
    public static void printInDisplayOutput(JTextPane displayOutput, String s) {
    	displayOutput.setText(s);
    }
    
    public static boolean isDisplay(String s){
    	return s.substring(0, 1).equals("0");
    }
}
