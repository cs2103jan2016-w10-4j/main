package main;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class UserInterface{

	Parser p = new Parser();
	
	public static void main(String[] args){
		new UserInterface();
	}
	
	public UserInterface() {
		JFrame f = new JFrame("Docket");
		
		// Random icon image grabbed from web. Should change
		String icon = ".\\main\\icon\\d.jpg";
		ImageIcon img = new ImageIcon(icon);
		f.setIconImage(img.getImage());

		f.setSize(750, 760);
		f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
               System.exit(0);
            }
        });
		
		Panel panel = new Panel(); 
		panel.setLayout(new FlowLayout());
		
		TextArea t1 = new TextArea("", 30, 100);
		t1.setEditable(false);
		t1.setBackground(Color.white);
		panel.add(t1);
		
		TextArea t2 = new TextArea("", 10, 100);
		t2.setEditable(false);
		t2.setBackground(Color.black);
		t2.setForeground(Color.white);
		panel.add(t2);
		
		Label l1 = new Label();
		l1.setText("command:");
		panel.add(l1);
		
		TextField textfield = new TextField(85);
		panel.add(textfield);
		
		f.add(panel);
		
		f.setVisible(true);
		
		textfield.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String s = textfield.getText();
				textfield.setText("");
				t2.append("> " + s + "\n");
				Font font = t2.getFont();
				t2.setFont(font.deriveFont(Font.BOLD));
				String output = p.parse(s);
				//t1.append("> " +output+"\n");
			}
		});
	}
}
