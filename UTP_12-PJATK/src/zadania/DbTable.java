package zadania;

import javax.swing.table.AbstractTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class DbTable extends AbstractTableModel {
        private Connection con;
        private ResultSet rs;
        private String[] columnNames;
        private int[] columnTypes;
        private boolean[] readOnly;
        private String tableName = "";
        private List rows;
        private ResultSetMetaData md;
        private boolean editable = false;

        public DbTable(Connection conn, String query, ResultSet resultSet, boolean ed) {
            rs = resultSet;
            editable = ed;
            con = conn;
            tableName = getTableName(query);
            try {
                md = rs.getMetaData();
                int cc = md.getColumnCount();
                columnNames = new String[cc];
                columnTypes = new int[cc];
                readOnly = new boolean[cc];
                for (int col = 0; col < cc; col++) {
                    columnNames[col] = md.getColumnName(col + 1);
                    columnTypes[col] = md.getColumnType(col + 1);
                    readOnly[col] = md.isReadOnly(col + 1);
                }

                rows = new ArrayList();
                while (rs.next()) {
                    List row = new ArrayList();
                    for (int i = 1; i <= getColumnCount(); i++) {
                        row.add(rs.getObject(i));
                    }
                    rows.add(row);
                }
                rs.close();
                fireTableChanged(null); // Nowa tabela
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

        // Niedoskonala wersja
        private String getTableName(String q) {
            if (q == null || q.equals("")) return "";
            StringTokenizer st = new StringTokenizer(q);
            while (st.hasMoreTokens()) {
                String w = st.nextToken();
                w = w.toUpperCase();
                if (w.equals("FROM")) {
                    String t = st.nextToken();
                    if (t.indexOf(',') == -1) return t;
                    break;
                }
            }
            return "";
        }

        // Obowiązkowe metody interfejsu TableModel
        public String getColumnName(int column) {
            if (columnNames[column] != null) return columnNames[column];
            else return "";
        }

        public Class getColumnClass(int column) {
            String type;
            Class c = null;
            try {
                type = md.getColumnClassName(column + 1);
                c = Class.forName(type);
            } catch (Exception e) {
                return super.getColumnClass(column);
            }
            return c;
        }

        public boolean isCellEditable(int row, int column) {
            if (!editable) return false;
            if (tableName.equals("")) return false;
            return !readOnly[column];
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return rows.size();
        }

        public Object getValueAt(int r, int c) {
            List row = (List) rows.get(r);
            return row.get(c);
        }

        public String dbValue(int col, Object value) {
            int type;
            if (value == null) return "null";
            type = columnTypes[col];

            switch (type) {
                case Types.CHAR:
                case Types.VARCHAR:
                case Types.LONGVARCHAR:
                    return "\'" + value.toString() + "\'";
                case Types.BIT:
                    return ((Boolean) value).booleanValue() ? "1" : "0";
                default:
                    return value.toString();
            }
        }


        public void setValueAt(Object value, int r, int c) {
            List row = (List) rows.get(r);
            String oldval = row.get(c).toString();
            if (oldval.equals(value.toString())) return;
            String colName = getColumnName(c);
            String query = " update " + tableName +
                    " set " + colName + " = " + dbValue(c, value) +
                    " where ";
            for (int j = 0; j < getColumnCount(); j++) {
                colName = getColumnName(j);
                if (colName.equals("")) continue;
                if (j != 0) query += " and ";
                query += colName + " = " + dbValue(j, getValueAt(r, j));
            }
            query += ";";
            try {
                Statement s = con.createStatement();
                int updCount = s.executeUpdate(query);
                row.set(c, value);
                System.out.println("Zmieniono rekordów: " + updCount);
            } catch (SQLException e) {
                System.out.println(query);
                System.out.println(e.getMessage());
            }
        }
}
