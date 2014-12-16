package nocgen.ui;

//import javax.swing.plaf.basic.BasicInternalFrameTitlePane.CloseAction;

import javax.swing.*;

import java.awt.*;

import nocgen.NocGen;
import nocgen.util.DefaultParam;

/*
 * WindowPrincipal.java
 * @author Felipe Todeschini Bortolon
 * Created on Sep 29, 2011, 3:28:03 PM
 */

@SuppressWarnings("serial")
public class WindowPrincipal extends javax.swing.JFrame 
{

    private NewFileView newFileView; // browse path window
    // menu objects
    private JMenuBar mainMenu;
    private JMenu fileMenu;
    private JMenuItem newMenuOption;
    private JMenuItem exitMenuOption;
    private JMenu helpMenu;
    private JMenuItem aboutMenuOption;
    
    // dimension panel objects
    private JPanel dimensionsPanel;
    private JLabel versusLabel;
    private JSpinner spinnerX;
    private JSpinner spinnerY;
    private SpinnerModel spinnerModelX; // used to set spinner limits
    private SpinnerModel spinnerModelY; // used to set spinner limits
    private static int spinnerUpperBound = 16;
    
    // algorithm panel objects
    private JComboBox algorithmComboBox;
    private JPanel algorithmPanel;
    private String [] algorithmNames = {"XY", "SR"};
    private javax.swing.JCheckBox regionBasedSelector;
    
    // max regions panel objects
    private JPanel maxRegionsPanel;
    private JSpinner spinnerMaxRegions;
    private SpinnerModel spinnerModelMaxRegions;

    // main Panel objects
    private JButton generateButton;
    private JLabel nocGenLabel;
    
    /** Creates the NocGen window and initializes it*/
    public WindowPrincipal() 
    {
        initComponents();
        newFileView = new NewFileView(this, true);
       // comboBoxKeepTrack();
    }
    
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() 
    {
    	// main objects
        generateButton = new JButton();
        nocGenLabel = new JLabel();
        // dimension objects
        dimensionsPanel = new JPanel();
//        spinnerModelX = new SpinnerNumberModel(0,0,spinnerUpperBound,1);
//        spinnerModelY = new SpinnerNumberModel(0,0,spinnerUpperBound,1);
        spinnerModelX = new SpinnerNumberModel(2,2,spinnerUpperBound,1);
        spinnerModelY = new SpinnerNumberModel(2,2,spinnerUpperBound,1);
        spinnerX = new JSpinner(spinnerModelX);
        spinnerY = new JSpinner(spinnerModelY);
        versusLabel = new JLabel();
        // algorithm objects 
        algorithmPanel = new JPanel();
        algorithmComboBox = new JComboBox(algorithmNames);
        regionBasedSelector = new JCheckBox();
        // max regions objects
        maxRegionsPanel = new JPanel();
//        spinnerModelMaxRegions = new SpinnerNumberModel(0,0,spinnerUpperBound,1);
        //spinnerModelMaxRegions = new SpinnerNumberModel(100,100,100,0);
        spinnerModelMaxRegions = new SpinnerNumberModel(DefaultParam.maxRegion,DefaultParam.maxRegion,DefaultParam.maxRegion,0);
        spinnerMaxRegions = new JSpinner(spinnerModelMaxRegions);              
        // menu object
        mainMenu = new JMenuBar();
        fileMenu = new JMenu();
        newMenuOption = new JMenuItem();
        exitMenuOption = new JMenuItem();
        helpMenu = new JMenu();
        aboutMenuOption = new JMenuItem();

        
        // Window Properties
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("NoCGen");
        setLocation(400, 300);
        setResizable(false); 
        
        generateButton.setText("Generate");
        generateButton.setActionCommand("");
        generateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateButtonActionPerformed(evt);
            }
        });

        dimensionsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dimensions", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 10), new java.awt.Color(12, 144, 189))); // NOI18N

        versusLabel.setText("x");

        GroupLayout dimensionsPanelLayout = new GroupLayout(dimensionsPanel);
        dimensionsPanel.setLayout(dimensionsPanelLayout);
        dimensionsPanelLayout.setHorizontalGroup(
            dimensionsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, dimensionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spinnerX, GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(versusLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spinnerY, GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                .addContainerGap())
        );
        dimensionsPanelLayout.setVerticalGroup(
            dimensionsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(dimensionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dimensionsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(versusLabel)
                    .addComponent(spinnerY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(spinnerX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        nocGenLabel.setFont(new Font("Arial", 0, 24)); // NOI18N
        nocGenLabel.setForeground(new Color(12, 144, 189));
        nocGenLabel.setText(" NocGen");

        algorithmPanel.setBorder(BorderFactory.createTitledBorder(null, "Algorithm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new Font("Arial", 0, 10), new Color(12, 144, 189))); 
        algorithmComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                algorithmComboBoxActionPerformed(evt);
            }
        });
        
        regionBasedSelector.setText("Use region based routing");
//        regionBasedSelector.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                regionBasedSelectorActionPerformed(evt);
//            }
//        });
        regionBasedSelector.setSelected(DefaultParam.rbrOption);
        
        GroupLayout algorithmPanelLayout = new GroupLayout(algorithmPanel);
        algorithmPanel.setLayout(algorithmPanelLayout);
        algorithmPanelLayout.setHorizontalGroup(
            algorithmPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(algorithmPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(algorithmPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(algorithmComboBox, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
                        .addComponent(regionBasedSelector))
                    .addContainerGap(49, Short.MAX_VALUE))
        );
        algorithmPanelLayout.setVerticalGroup(
                algorithmPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(algorithmPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(algorithmComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(regionBasedSelector)
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );

        maxRegionsPanel.setBorder(BorderFactory.createTitledBorder(null, "Max Regions", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new Font("Arial", 0, 10), new Color(12, 144, 189))); 

        GroupLayout maxRegionsPanelLayout = new GroupLayout(maxRegionsPanel);
        maxRegionsPanel.setLayout(maxRegionsPanelLayout);
        maxRegionsPanelLayout.setHorizontalGroup(
            maxRegionsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(maxRegionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spinnerMaxRegions, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(131, Short.MAX_VALUE))
        );
        maxRegionsPanelLayout.setVerticalGroup(
            maxRegionsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(maxRegionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spinnerMaxRegions, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        fileMenu.setText("File");

        newMenuOption.setText("New");
        newMenuOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newMenuOptionActionPerformed(evt);
            }
        });
        fileMenu.add(newMenuOption);

        exitMenuOption.setText("Exit");
        exitMenuOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuOptionActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuOption);

        mainMenu.add(fileMenu);

        helpMenu.setText("Help");

        aboutMenuOption.setText("About NocGen");
        helpMenu.add(aboutMenuOption);

        mainMenu.add(helpMenu);

        setJMenuBar(mainMenu);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(dimensionsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(maxRegionsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(algorithmPanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(nocGenLabel, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
                    .addComponent(generateButton, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(algorithmPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(maxRegionsPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dimensionsPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(17, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nocGenLabel, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                        .addComponent(generateButton, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29))))
        );
        
     // diseable the button and spinners
        generateButton.setEnabled(false);
        spinnerX.setEnabled(false);
        spinnerY.setEnabled(false);
        spinnerMaxRegions.setEnabled(false);
        algorithmComboBox.setEnabled(false);
        regionBasedSelector.setEnabled(false);
        
        pack();
    }

    private void algorithmComboBoxActionPerformed(java.awt.event.ActionEvent evt) 
    {
    	/* COMMENTED IN ORDER TO PRESERVE THE FIRST NOC VERSION */
    	/**if( algorithmComboBox.getSelectedItem().equals("SR"))
    		spinnerMaxRegions.setEnabled(true);
    	else
    	{
    		spinnerMaxRegions.setEnabled(false);
    		// find a function which reset the spinner
    	}*/
    }
    
    private void closeWindowPrincipal()
    {
        System.exit(0);
    }
    
    private void exitMenuOptionActionPerformed(java.awt.event.ActionEvent evt) 
    {
    	closeWindowPrincipal();
    }

    private void generateButtonActionPerformed(java.awt.event.ActionEvent evt) 
    {
    	int x = (Integer)spinnerX. getValue(); // get X size
    	int y = (Integer)spinnerY.getValue();  // get Y size
    	int maxReg = (Integer)spinnerMaxRegions.getValue();  // get Y size

    	//String algorithm = (String)algorithmComboBox.getSelectedItem();
    	String algorithm = DefaultParam.algorithmCode;
    	String rbrOption;
    	if(regionBasedSelector.isSelected())
    		rbrOption = "true";
    	else
    		rbrOption = "false";
    	
    	if(checkRegionsNumber(maxReg, algorithm))
    	{
    		String args[] = new String[6];
        	args[0] = Integer.toString(x);
        	args[1] = Integer.toString(y);
        	args[2] = newFileView.getFinalPath();
        	args[3] = Integer.toString(maxReg); 
        	args[4] = algorithm;
        	args[5] = rbrOption;
        	
      
        	boolean showDialog = NocGen.main(args); // change it by method call
        	if(showDialog)
        	{
        		JOptionPane.showMessageDialog(null, "Process finished!");
        	}
        	else
        	{
        		JOptionPane.showMessageDialog(null, "Error:\nProject could not be created!");
        	}	
    	}
    	else
    		JOptionPane.showMessageDialog(null, "Error:\n  Maximum region number is not compatible with\nthe algorithm! Change its number and try again.");
    	
    }
    
    /**
     * This method checks if the selected algorithm can be created with
     * the selected number of regions
     * 
     * @param maxReg Number of maximum regions selected by the user.
     * @param algorithm Selected algorithm.
     * @return True if the algorithm checks with the maximum regions number.
     */
    private boolean checkRegionsNumber(int maxReg, String algorithm)
    {
    	if(algorithm.equals("XY") && maxReg < 4 && regionBasedSelector.isSelected())
    		return false;
    	
    	return true;
    }
    
    private void regionBasedSelectorActionPerformed(java.awt.event.ActionEvent evt) 
    {
    	if(regionBasedSelector.isSelected())
    		spinnerMaxRegions.setEnabled(true);
    	else
    		spinnerMaxRegions.setEnabled(false);
    }

    private void newMenuOptionActionPerformed(java.awt.event.ActionEvent evt) 
    {
    	// open file's path and project name selection
    	newFileView.setVisible(true);
    
    	// enable the button and spinners
    	generateButton.setEnabled(true);
    	spinnerX.setEnabled(true);
    	spinnerY.setEnabled(true);
//        algorithmComboBox.setEnabled(true);
//        regionBasedSelector.setEnabled(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args []) 
    {
        try 
        {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) 
            {
                if ("Nimbus".equals(info.getName())) 
                {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } 
        catch (ClassNotFoundException ex) 
        {
            java.util.logging.Logger.getLogger(WindowPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) 
        {
            java.util.logging.Logger.getLogger(WindowPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) 
        {
            java.util.logging.Logger.getLogger(WindowPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) 
        {
            java.util.logging.Logger.getLogger(WindowPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new WindowPrincipal().setVisible(true);
            }
        });
    }
    
}