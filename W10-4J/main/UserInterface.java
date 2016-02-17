package main;

import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class UserInterface{

	Parser p = new Parser();
	
	public static void main(String[] args){
		new UserInterface();
	}
	
	public UserInterface() {
		JFrame f = new JFrame();
		f.setSize(750, 760);
		f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
               System.exit(0);
            }
        });
		
		Panel panel = new Panel(); 
		panel.setLayout(new FlowLayout());
		
		Label l1 = new Label();
		l1.setText("Docket");
		panel.add(l1);
		
		TextArea t1 = new TextArea("", 20, 100);
		panel.add(t1);
		
		TextArea t2 = new TextArea("", 20, 100);
		panel.add(t2);
		
		Label l2 = new Label();
		l2.setText("command:");
		panel.add(l2);
		
		TextField textfield = new TextField(85);
		panel.add(textfield);
		
		f.add(panel);
		
		f.setVisible(true);
	}
}
