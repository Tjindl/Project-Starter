package ui;

import model.Item;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

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

    @Override
    public boolean isCellEditable(int row, int column) {
        switch (column) {
            case 1: return true; // only the Period is editable
            default: return false;
        }
    }

    @Override
    public Object getValueAt(int row, int column) {
        Item item = getItem(row);

        switch (column) {
            case 0: return item.getItemId();
            case 1: return item.getPeriod();
            case 2: return item.getRent();
            case 3: return item.getName();
            default: return null;
        }
    }

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

    public void insertItem(int row, Item item) {
        items.add(row, item);
        fireTableRowsInserted(row, row);
    }

    public void removeItem(int row) {
        items.remove(row);
        fireTableRowsDeleted(row, row);
    }

    public void filterItems(String itemType) {
        System.out.println(itemType + "inside filter method");
        this.items = originalItems;
        filteredItems = new ArrayList<>();
//        int count = 0;
//        ArrayList<Integer> indexList = new ArrayList<>();
        for (Item item : items) {
//            System.out.println(item.getPeriod() + "inside for loop");
//            System.out.println(itemType);
//            System.out.println(item.getName());
//            System.out.println(itemType.equals(item.getName()));
            if (itemType.equals(item.getName())) {
//                System.out.println(itemType + "in if statement");
                //removeItem(count);
//                indexList.add(count);
//                System.out.println(itemType + "deleted");
                filteredItems.add(item);
            }
//            count++;
//            for (int i : indexList) {
//
//            }
            this.items = filteredItems;
        }


    }

}
