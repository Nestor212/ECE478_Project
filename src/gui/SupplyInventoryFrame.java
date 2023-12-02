package gui;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import hardware.Item;
import hardware.Supplier;

public class SupplyInventoryFrame extends JFrame implements ActionListener 
{
	private static final long serialVersionUID = -2465285598196994748L;
	SupplyManagerGUI session;
	Supplier thisSupplier;
	
	Container container = getContentPane();
	JScrollPane scrollPane;
	
   	JLabel titleLabel = new JLabel();
	JLabel itemNumLabel = new JLabel("Item Number");
	JLabel itemNameLabel = new JLabel("Name");
	JLabel QtyLabel = new JLabel("Qty");
	JLabel supplierPriceLabel = new JLabel("Supplier Price");

	ArrayList<JLabel> itemNumbers;
	ArrayList<JLabel> itemNames;
	ArrayList<JLabel> itemQtys;
	ArrayList<JLabel> supplierPrices;
	
	private JMenuBar menuBar;		//the horizontal container
	
	//File Menu Declarations
	private JMenu fileMenu;
	private JMenuItem fileAddNewItem;
	private JMenuItem fileDeleteItem;


	public SupplyInventoryFrame(SupplyManagerGUI aSession)
	{      
		session = aSession;
		thisSupplier = (Supplier) session.sessionAccount.getPartner();
		
		titleLabel = new JLabel("Inventory - " + session.sessionAccount.getPartner().toString());
		itemNumbers = new ArrayList<JLabel>(100);
		itemNames = new ArrayList<JLabel>(100);
		itemQtys = new ArrayList<JLabel>(100);
		supplierPrices = new ArrayList<JLabel>(100);
		
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		fileAddNewItem = new JMenuItem("Add New Item");
		fileDeleteItem = new JMenuItem("Delete Item");
		fileAddNewItem.addActionListener(this);
		fileDeleteItem.addActionListener(this);
		fileMenu.add(fileAddNewItem);
		fileMenu.add(fileDeleteItem);
		
	    menuBar.add(fileMenu);
		setJMenuBar(menuBar);		

		populateArrays();
		
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
    }
	
	public void clearArrays()
	{
		itemNumbers.clear();
		itemNames.clear();
		itemQtys.clear();
		supplierPrices.clear();	
	}
	
	
	public void populateArrays()
	{
		for(int i = 0; i < thisSupplier.getItemList().size(); i++)
		{
			itemNumbers.add(new JLabel(String.valueOf(thisSupplier.getItemList().get(i).getItemNum())));
			itemNames.add(new JLabel(thisSupplier.getItemList().get(i).getName()));
			itemQtys.add(new JLabel(String.valueOf(thisSupplier.getItemList().get(i).getQty())));
			supplierPrices.add(new JLabel("$" + thisSupplier.getItemList().get(i).getSupplierPrice()));
		}
	}
    public void setLayoutManager() 
    {
        container.setLayout(null);
    }
    
    public void setLocationAndSize() 
    {
    	titleLabel.setBounds(50, 20,  1000, 30);
    	titleLabel.setFont(new Font("Lucida", Font.BOLD, 22));
    	
    	itemNumLabel.setBounds(50, 80,  200, 20);
    	itemNumLabel.setFont(new Font("Lucida", Font.BOLD, 18));
    	itemNameLabel.setBounds(250, 80,  200, 20);
    	itemNameLabel.setFont(new Font("Lucida", Font.BOLD, 18));
    	QtyLabel.setBounds(500, 80,  200, 20);
    	QtyLabel.setFont(new Font("Lucida", Font.BOLD, 18));
    	supplierPriceLabel.setBounds(600, 80,  200, 20);
    	supplierPriceLabel.setFont(new Font("Lucida", Font.BOLD, 18));
    	
		for(int i = 1; i <= thisSupplier.getItemList().size(); i++)
		{
			itemNumbers.get(i - 1).setBounds(50, (i * 20) + 100,  600, 20);
			itemNames.get(i - 1).setBounds(250, (i * 20) + 100,  600, 20);
			itemQtys.get(i - 1).setBounds(500, (i * 20) + 100,  600, 20);
			supplierPrices.get(i - 1).setBounds(600, (i * 20) + 100,  600, 20);		
		}
    }
    
    public void addComponentsToContainer() 
    {
    	//container.add(sb);
    	container.removeAll();
    	container.add(titleLabel);
        container.add(itemNumLabel);
        container.add(itemNameLabel);
        container.add(QtyLabel);
        container.add(supplierPriceLabel);
        
		for(int i = 0; i < thisSupplier.getItemList().size(); i++)
		{
	        container.add(itemNumbers.get(i));
	        container.add(itemNames.get(i));
	        container.add(itemQtys.get(i));
	        container.add(supplierPrices.get(i));	
		}
		super.update(getGraphics());
    }

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource().equals(fileAddNewItem))
		{
			handleAddNew();	
		}
		else if(e.getSource().equals(fileDeleteItem))
		{
			handleDelete();
		}		
	}
	
	public void handleAddNew()
	{
		boolean success = false;
		JTextField itemName = new JTextField();
		JTextField ohQty = new JTextField();
		JTextField unitPrice = new JTextField();
		
		Object[] fields = 
		{
			"Item Name:", itemName,
			"On Hand Qty:", ohQty,
			"Unit Price:", unitPrice,
		};
	
		int x = JOptionPane.showConfirmDialog(null, fields, "Add New Item" , JOptionPane.OK_CANCEL_OPTION);
		if(x == JOptionPane.OK_OPTION) 
		{
			if(itemName.getText().isBlank())
			{
                JOptionPane.showMessageDialog(null, "Item Name field is empty.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else if(ohQty.getText().isBlank())
			{
                JOptionPane.showMessageDialog(null, "On Hand Qty field is empty.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else if(unitPrice.getText().isBlank())
			{
                JOptionPane.showMessageDialog(null, "Unit Price field is empty.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				Item i1 = new Item();
				i1.setName(itemName.getText());
				i1.setQty(Integer.valueOf(ohQty.getText()));
				i1.setSupplierPrice(Double.valueOf(unitPrice.getText()));
				thisSupplier.addItem(i1);
				
                clearArrays();
                populateArrays();
                setLocationAndSize();
                addComponentsToContainer();
                success = true;
                
                JOptionPane.showMessageDialog(null, "Item has been added. Item Number is: " + i1.getItemNum(), "Success", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			if(!success)
			{
                JOptionPane.showMessageDialog(null, "Error Adding New Item", "Error", JOptionPane.ERROR_MESSAGE);
			}
	}
	public void handleDelete()
	{
		boolean success = false;
		JTextField itemNum = new JTextField();

		
		Object[] fields = 
		{
			"Item Number:", itemNum,

		};
	
		int x = JOptionPane.showConfirmDialog(null, fields, "Delete Item" , JOptionPane.OK_CANCEL_OPTION);
		if(x == JOptionPane.OK_OPTION) 
		{
			if(itemNum.getText().isBlank())
			{
                JOptionPane.showMessageDialog(null, "Item Name field is empty.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				for(int i =0; i < thisSupplier.getItemList().size(); i++)
				{
					if(thisSupplier.getItemList().get(i).getItemNum().equals(Integer.valueOf(itemNum.getText()))) 
					{
						Item i1 = thisSupplier.getItemList().get(i);
						int y = JOptionPane.showConfirmDialog(null, "Are you sure you wish to delete: " + i1.getName(), "Confirm Deletion" , JOptionPane.YES_NO_OPTION);
						if(y == JOptionPane.YES_OPTION) 
						{
							thisSupplier.getItemList().remove(i1);
							
			                clearArrays();
			                populateArrays();
			                setLocationAndSize();
			                addComponentsToContainer();
			                success = true;
			                
			                JOptionPane.showMessageDialog(null, "Item has been Successfully Deleted", "Success", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			}
		}
		if(!success)
		{
            JOptionPane.showMessageDialog(null, "Error Deleting Item", "Error", JOptionPane.ERROR_MESSAGE);

		}
	}
}
