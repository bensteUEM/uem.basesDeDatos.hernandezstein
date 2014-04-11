import java.awt.Component;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
public class GuiDefaultTableCellRenderer extends DefaultTableCellRenderer 
{
	private static final long serialVersionUID = 7046443391892013587L;

	public Component getTableCellRendererComponent
       (JTable table, Object value, boolean isSelected,
       boolean hasFocus, int row, int column) 
    {
        Component cell = super.getTableCellRendererComponent
           (table, value, isSelected, hasFocus, row, column);
        
        System.out.println("DEBUG: drawing for value: "+value);//TODO DEBUG
        if (value instanceof String){
        	setOpaque(true);
        	if (value.equals("n/a")){
        		cell.setBackground( Color.GRAY );
        	}
        	else if (((String) value).startsWith("*")){
        		cell.setBackground( Color.GREEN );
        	}
        }
        
        else if( value instanceof Integer )
        {
            Integer amount = (Integer) value;
            if( amount.intValue() < 0 )
            {
                cell.setBackground( Color.red );
                // You can also customize the Font and Foreground this way
                // cell.setForeground();
                // cell.setFont();
            }
            else
            {
                cell.setBackground( Color.white );
            }
        }
        return cell;
    }
}