package be.esthoril.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class Menu extends JMenuBar {

	private static final long serialVersionUID = 1L;

	private String		filename = "";
	
	public Menu(){
		setBounds(0,0,1100,24);
	
		JMenu file = new JMenu("File");
		file.setBounds(0,0,45,24);
		
		JMenuItem load = new JMenuItem("Load World");
		load.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				final JFrame loadwindow = new JFrame("Load Map");
				loadwindow.setSize(190,140);
				loadwindow.setLayout(null);
				loadwindow.setLocationRelativeTo(null);
				
				JLabel title = new JLabel("Pick filename");
				title.setBounds(10, 4, 160, 20);
				loadwindow.add(title);
				
				final JComboBox<String> combobox = new JComboBox<String>();
				combobox.setBounds(10,34, 160, 20);
				File folder = new File("worlds/");
				File[] listOfFiles = folder.listFiles();

				for (int i = 0; i < listOfFiles.length; i++) {
					if (listOfFiles[i].isFile()) {
						filename = listOfFiles[i].getName();
						combobox.addItem(filename);
					}
				}

			    System.out.println("selected: " + filename);

				JButton okayButton = new JButton("Okay");
				okayButton.setBounds(40,60,100,30);
				okayButton.addActionListener(new ActionListener(){

					public void actionPerformed(ActionEvent e){
						filename = (String) combobox.getSelectedItem();
						System.out.println("loading " + filename);
						Screen.load.loadGridSer(filename);					// load as serial
						loadwindow.dispose();
					}
				});

				loadwindow.add(combobox);
				loadwindow.add(okayButton);
				loadwindow.setResizable(false);
				loadwindow.setVisible(true);

			}
		});
		
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println("Closing Game...");
				System.exit(0);
			}
		});
		
		file.add(load);
		file.add(exit);
		add(file);
		
	}
}