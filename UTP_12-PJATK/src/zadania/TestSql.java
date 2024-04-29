package zadania;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class TestSql extends JFrame implements ActionListener {

    private Connection con = null;
    private Statement stmt;
    private ResultSet rs = null;
    private String query;
    private JTable table = new JTable();
    private JTextArea ta = new JTextArea(3,40);
    private DefaultListModel history = new DefaultListModel();
    private JList hlis = new JList(history);
    private JWindow wh = new JWindow();


    public TestSql(String URL, String driver, String user,
                   String passwd) {
        super("Baza danych książki");
        setDefaultCloseOperation(3);

        try {
            Class.forName(driver);
            con = DriverManager.getConnection(URL);
            stmt = con.createStatement();
        } catch (Exception exc)  {
            System.out.println(exc.getMessage());
            System.exit(1);
        }

        JScrollPane scrollpane = new JScrollPane(table);
        scrollpane.setPreferredSize(new Dimension(600, 400));
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        ta.setLineWrap(true);

        JScrollPane tsp = new JScrollPane(ta);
        p.add(tsp, "Center");
        JButton b = new JButton("Execute");
        b.setMnemonic('E');
        b.addActionListener(this);

        p.add(b, "East");
        p.setBorder(BorderFactory.createLineBorder(Color.blue));
        getContentPane().add(scrollpane, "Center");
        getContentPane().add(p, "South");

        createHistoryList();

        pack();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e)  {
        String new_query = ta.getText();
        if (new_query.equals(query)) return;
        query = new_query;
        if (!history.contains(query)) history.addElement(query);
        execute(query);
    }

    void execute(String query)  {
        try  {
            rs = stmt.executeQuery(query);
            DbTable dbt = new DbTable(con, query, rs, true);
            table.setModel(dbt);
        } catch(SQLException exc)  {
            System.out.println(exc.getMessage());
        }
    }

    void createHistoryList()  {

        ta.addMouseListener(new MouseAdapter()  {
            public void mouseReleased(MouseEvent e)  {
                if (e.isPopupTrigger())  {
                    wh.pack();
                    wh.show();
                }
            }
        });

        hlis.addMouseListener(new MouseAdapter()  {
            public void mouseClicked(MouseEvent e)  {
                if (e.getClickCount() == 2)   {
                    String s = (String) hlis.getSelectedValue();
                    if (s != null) ta.setText(s);
                    wh.setVisible(false);
                }
            }
        });

        JScrollPane hsp = new JScrollPane(hlis);
        hsp.setPreferredSize(new Dimension(200, 300));
        JPanel hp = new JPanel(new BorderLayout());
        hp.setBorder(BorderFactory.createLoweredBevelBorder());
        hp.add(hsp, "Center");
        JPanel bhp = new JPanel();

        ActionListener hlHandler = new ActionListener()  {
            public void actionPerformed(ActionEvent e)  {
                String cmd = e.getActionCommand();
                if (cmd.equals("Cancel")) wh.setVisible(false);
                else if (cmd.equals("Clear all")) history.clear();
                else  {
                    int index = hlis.getSelectedIndex();
                    if (index == -1) return;
                    if (cmd.equals("Clear")) history.remove(index);
                    else if (cmd.equals("Execute"))  {
                        String new_query = (String) hlis.getSelectedValue();
                        if (new_query.equals(query)) return;
                        query = new_query;
                        wh.setVisible(false);
                        execute(query);
                        ta.setText(query);
                    }
                }
            }
        };

        JButton  b = new JButton("Cancel");
        b.addActionListener(hlHandler);
        bhp.add(b);
        b = new JButton("Clear");
        b.addActionListener(hlHandler);
        bhp.add(b);
        b = new JButton("Clear all");
        b.addActionListener(hlHandler);
        bhp.add(b);
        b = new JButton("Execute");
        b.addActionListener(hlHandler);
        bhp.add(b);
        hp.add(bhp, "South");

        wh.getContentPane().add(hp);
        ta.addMouseListener(new MouseAdapter()  {
            public void mouseReleased(MouseEvent e)  {
                if (e.isPopupTrigger())  {
                    wh.setLocation( getX()+10, getY()+50);
                    wh.pack();
                    wh.show();
                }
            }
        });
    }


    public static void main(String[] args) {
        String driverName = "org.apache.derby.jdbc.ClientDriver";
        String url = "jdbc:derby://localhost/ksidb";
        String uid = "pies";
        String pwd = "kuba";
        new TestSql(url, driverName, uid, pwd);
    }
}