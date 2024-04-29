package zad1;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.io.*;
import java.util.Date;

public class CountryTable {

    JTable jTable;

    String path = "C:\\Users\\jacke\\IdeaProjects\\UTP1_KK_S24911\\UTP1_KK_S24911\\data\\countries.txt";


    public CountryTable(String countriesFileName) {
        File file = new File(countriesFileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public JTable create() {
         jTable = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0 || column == 1 || column == 3 || column == 4) {
                    return false;
                }
                return true;
            }
        };
        DefaultTableModel model = new DefaultTableModel();
        Dimension dimension = new Dimension(800,500);
        Object[] columns = {"Name", "Capital", "Population", "Flagi", "Date"};
        model.setColumnIdentifiers(columns);
        jTable.setPreferredScrollableViewportSize(dimension);
        jTable.setRowHeight(60);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                    String[] data = line.split("    ");
                    model.addRow(new String[]{data[0], data[1], data[2]});
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        jTable.setModel(model);
        jTable.getColumnModel().getColumn(2).setCellEditor(new NumericCellEditor());
        ImageIcon imageIcon1 = new ImageIcon("data/1511075586_11.jpg");
        ImageIcon imageIcon2 = new ImageIcon("data/a529b950995a58edf81188eaff6160a3.jpeg");
        ImageIcon imageIcon3 = new ImageIcon("data/Spain-Flag.png");
        jTable.getColumnModel().getColumn(3).setCellRenderer(new ImageRender());
        jTable.setValueAt(imageIcon1, 0, 3);
        jTable.setValueAt(imageIcon2, 1, 3);
        jTable.setValueAt(imageIcon3, 2, 3);
        jTable.getColumnModel().getColumn(2).setCellRenderer(new ColoredTextRender());

        jTable.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                if(column == 2){
                    String currentDate = String.valueOf(new Date());
                    jTable.getModel().setValueAt(currentDate, row,4);
                }
            }
        });


        return jTable;
    }

     class NumericCellEditor extends DefaultCellEditor {
        public NumericCellEditor() {
            super(new JTextField());
            JTextField textField = (JTextField) getComponent();
            textField.setDocument(new NumericDocument());
        }
    }

    class NumericDocument extends PlainDocument {
        @Override
        public void insertString(int offs, String str, AttributeSet a) {
            if (str.matches("\\d+")) {
                try {
                    super.insertString(offs, str, a);
                } catch (BadLocationException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

     class ImageRender extends DefaultTableCellRenderer {
         public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
             super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
             ImageIcon imageIcon = (ImageIcon) value;
             if (imageIcon instanceof ImageIcon) {
                 int width = jTable.getColumnModel().getColumn(column).getWidth();
                 int height = jTable.getRowHeight(column);
                 Image image = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
                 ImageIcon imageIcon1 = new ImageIcon(image);
                 setIcon(imageIcon1);
                 setText("");
             }
             return this;
         }
    }

    class ColoredTextRender extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            try {
            String str = value.toString();
            if(!str.equals("")) {
                String result = str.replaceAll("[^0-9]", "");
                int count = Integer.parseInt(result);
                if (count > 20000) {
                    component.setForeground(Color.RED);
                } else {
                    component.setForeground(table.getForeground());
                }
            } else {
                component.setForeground(table.getForeground());
            }
            } catch (Exception e){
                e.printStackTrace();
            }


            return component;
        }

    }


}
