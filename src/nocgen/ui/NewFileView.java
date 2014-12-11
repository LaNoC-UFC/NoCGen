/*
 * NewJDialog.java
 * @author Felipe Todeschini Bortolon
 * Created on Sep 30, 2011, 4:20:11 PM
 */
package nocgen.ui;
import javax.swing.JFileChooser;

@SuppressWarnings("serial")
public class NewFileView extends javax.swing.JDialog 
{

    private static JFileChooser pathChooser;
    private javax.swing.JButton browseButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel folderPathLabel;
    private javax.swing.JButton okButton;
    private javax.swing.JTextField projectFolderPath;
    private javax.swing.JTextField projectName;
    private javax.swing.JLabel projectNameLabel;
    private javax.swing.JPanel viewWindowPanel;
    public String finalPath;
    
    /** Creates new form NewJDialog */
    public NewFileView(java.awt.Frame parent, boolean modal) 
    {
        super(parent, modal);
        initComponents();
        pathChooser = new JFileChooser();
        pathChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }

    public String getFinalPath()
    {
    	return finalPath;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */  
    private void initComponents() 
    {

        viewWindowPanel = new javax.swing.JPanel();
        projectNameLabel = new javax.swing.JLabel();
        projectName = new javax.swing.JTextField();
        folderPathLabel = new javax.swing.JLabel();
        projectFolderPath = new javax.swing.JTextField();
        browseButton = new javax.swing.JButton();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        // Window Properties
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.CardLayout());
        setTitle("NoCGen");
        setLocation(350, 350);
        setResizable(false);
        
        projectNameLabel.setText("Project Name:");

        folderPathLabel.setText("Folder Path:");

        browseButton.setText("Browse");
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseButtonActionPerformed(evt);
            }
        });

        okButton.setText("Ok");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout viewWindowPanelLayout = new javax.swing.GroupLayout(viewWindowPanel);
        viewWindowPanel.setLayout(viewWindowPanelLayout);
        viewWindowPanelLayout.setHorizontalGroup(
            viewWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewWindowPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(viewWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(viewWindowPanelLayout.createSequentialGroup()
                        .addGroup(viewWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(projectNameLabel)
                            .addComponent(folderPathLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(viewWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(projectName, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewWindowPanelLayout.createSequentialGroup()
                                .addComponent(projectFolderPath, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(browseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewWindowPanelLayout.createSequentialGroup()
                        .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        viewWindowPanelLayout.setVerticalGroup(
            viewWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewWindowPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(viewWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(projectNameLabel)
                    .addComponent(projectName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(viewWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(folderPathLabel)
                    .addComponent(projectFolderPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseButton))
                .addGap(18, 18, 18)
                .addGroup(viewWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(okButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(viewWindowPanel, "card2");

        pack();
    }

    private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) 
    {
    	int op = pathChooser.showOpenDialog(this);
    	if (op == JFileChooser.APPROVE_OPTION) 
    	{
    		projectFolderPath.setText(pathChooser.getSelectedFile().getAbsolutePath());
    	}
    }

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) 
    {
    	// hide the window
    	this.setVisible(false);
    }

    private void resetTextFields()
    {
    	projectName.setText("");
	    projectFolderPath.setText("");
    }
    
	private void okButtonActionPerformed(java.awt.event.ActionEvent evt) 
	{
	    finalPath = projectFolderPath.getText() + java.io.File.separatorChar + projectName.getText();
	    
	    // clear de text fields and hides the window
	    resetTextFields();
	    this.setVisible(false);
	}

    /**
     * @param args the command line arguments
     */
    public static void main() 
    {
        try 
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) 
            {
                if ("Nimbus".equals(info.getName())) 
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } 
        catch (ClassNotFoundException ex) 
        {
            java.util.logging.Logger.getLogger(NewFileView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } 
        catch (InstantiationException ex) 
        {
            java.util.logging.Logger.getLogger(NewFileView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } 
        catch (IllegalAccessException ex) 
        {
            java.util.logging.Logger.getLogger(NewFileView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } 
        catch (javax.swing.UnsupportedLookAndFeelException ex) 
        {
            java.util.logging.Logger.getLogger(NewFileView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                NewFileView dialog = new NewFileView(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
}
