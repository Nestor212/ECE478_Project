package test_client;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class PartnersFrame extends JFrame implements ActionListener
{
	JLabel titleLabel = new JLabel("Partners: ");
	JLabel supplierLabel = new JLabel("Suppliers: ");
	JLabel transportLabel = new JLabel("Transpo: ");

    ArrayList<JLabel> partnerLabels;
    ArrayList<JButton> editButton;
    
    // PartnerID stored to be able to find partner for editing
    private ArrayList<Integer> partnerID;

            
    Container container = getContentPane();
  
	public PartnersFrame()
	{
		partnerLabels = new ArrayList<JLabel>(20);
		partnerID= new ArrayList<Integer>(20);
        editButton = new ArrayList<JButton>();
        
    	populateArrays();
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    }
	
	public void populateArrays()
	{
		for(int i = 0; i < GUI.session.getCompany().getSupplierList().size(); i++)
		{
			partnerLabels.add(new JLabel(GUI.session.getCompany().getSupplierList().get(i).getName()));
			partnerID.add(GUI.session.getCompany().getSupplierList().get(i).getPartnerID());
		}
		for(int i = 0; i < GUI.session.getCompany().getTransportList().size(); i++)
		{
			partnerLabels.add(new JLabel(GUI.session.getCompany().getTransportList().get(i).getName()));
			partnerID.add(GUI.session.getCompany().getTransportList().get(i).getPartnerID());
		}
	}
	
	
    public void setLayoutManager() 
    {
        container.setLayout(null);
    }
    
    public void setLocationAndSize() 
    {	
    	titleLabel.setBounds(100, 80,  200, 20);
    	titleLabel.setFont(new Font("Lucida", Font.BOLD, 22));

    	for(int i = 1; i <= partnerLabels.size(); i++)
    	{
    		partnerLabels.get(i - 1).setBounds(200, (i * 20) + 120,  600, 20);
    		    		
    		editButton.add(new JButton("Edit"));
    		editButton.get(i - 1).setBounds(100, (i * 20) + 120, 50, 20);
    	}
    }

    public void addComponentsToContainer() 
    {
    	container.add(titleLabel);
    	
    	for(int i = 0; i < partnerLabels.size(); i++)
    	{
   	     	container.add(partnerLabels.get(i));
   	     	container.add(editButton.get(i));
    	}
    }

    public void addActionEvent() 
    {
    	for(int i = 0; i < editButton.size(); i++)
    	{
    		editButton.get(i).addActionListener(this);
    	}
    }
    
    
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		for(int i = 0; i < editButton.size(); i++)
		{
			if(e.getSource() == editButton.get(i))
			{
				editAccount(partnerID.get(i));
			}
		}
		
	}
	
	public void editAccount(int aPartnerID)
	{
		System.out.println("TO DO: Find partner with ID: " + aPartnerID + ", and allow for edits.");
	}

}
