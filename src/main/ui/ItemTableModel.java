package ui;

import model.Item;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

// represents the model that builds the table
public class ItemTableModel extends AbstractTableModel {
    private String[] columnNames = {
            "ItemId", "Period", "Item Rent Per Unit", "Item Name"};

    private List<Item> items;
    private List<Item> filteredItems;
    private List<Item> originalItems = new ArrayList<>();

    public ItemTableModel() {
        items = new ArrayList<Item>();
    }

    public ItemTableModel(List<Item> items) {
        this.items = items;
        this.originalItems = items;
    }

    // EFFECTS : gives the number of columns
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    // EFFECTS : gives out the column name from column number
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }


    // EFFECTS : gives the number of items in the table
    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public Class getColumnClass(int column) {
        switch (column) {
            case 3: return String.class;
            default: return Integer.class;
        }
    }

    // EFFECT : tells whether the cell is editable.
    @Override
    public boolean isCellEditable(int row, int column) {
        switch (column) {
            case 1: return true; // only the Period is editable
            default: return false;
        }
    }

    // EFFECTS : gives out a value at a cell
    @Override
    public Object getValueAt(int row, int column) {
        Item item = getItem(row);

        switch (column) {
            case 0: return item.getItemId();
            case 1: return item.getperiod();
            case 2: return item.getrent();
            case 3: return item.getname();
            default: return null;
        }
    }

    // MODIFIES : table
    // EFFECTS : sets the value in a cell
    @Override
    public void setValueAt(Object value, int row, int column) {
        Item item = getItem(row);

        switch (column) {
            case 0: item.setItemId((Integer)value);
            break;
            case 1: item.setPeriod((Integer)value);
            break;
            case 2: item.setName((String)value);
            break;
            case 3: item.setRent((Integer)value);
            break;
        }

        fireTableCellUpdated(row, column);
    }

    public Item getItem(int row) {
        return items.get(row);
    }

    public void addItem(Item item) {
        insertItem(getRowCount(), item);
    }

    // MODIFIES : items
    // EFFECTS : inserts an item at an index
    public void insertItem(int row, Item item) {
        items.add(row, item);
        fireTableRowsInserted(row, row);
    }

    // MODIFIES : items
    // EFFECTS : removes an item at an index
    public void removeItem(int row) {
        items.remove(row);
        fireTableRowsDeleted(row, row);
    }

    // MODIFIES : Table
    // EFFECTS : filters the items in the table
    public void filterItems(String itemType) {
        this.items = originalItems;
        filteredItems = new ArrayList<>();
        for (Item item : items) {
            if (itemType.equals(item.getname())) {
                filteredItems.add(item);
            }
            this.items = filteredItems;
        }


    }

}
