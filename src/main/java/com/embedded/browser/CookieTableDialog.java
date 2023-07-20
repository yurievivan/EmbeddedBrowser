package com.embedded.browser;

import java.util.Map;
import org.eclipse.jface.dialogs.Dialog;
import static org.eclipse.jface.window.Window.setDefaultImage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ControlEditor;
import org.eclipse.swt.custom.TableCursor;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

/**
 *
 * @author ivan.yuriev
 */
public class CookieTableDialog extends Dialog {

    private Map<String, String> data;

    public CookieTableDialog(Shell parentShell, Map<String, String> data) {
        super(parentShell);
        setDefaultImage(new Image(parentShell.getDisplay(), getClass().getResourceAsStream("cookie.png")));
        this.data = data;
    }

    @Override
    protected Control createDialogArea(final Composite parent) {
        Composite body = (Composite) super.createDialogArea(parent);
        Table table = createTable(body);
        TableCursor cursor = new TableCursor(table, SWT.NONE);
        createTableColumn(table, "Cookie Name");
        createTableColumn(table, "Cookie Value");

        ControlEditor editor = new ControlEditor(cursor);
        editor.grabHorizontal = true;
        editor.grabVertical = true;

        cursor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent e) {
                final Text text = new Text(cursor, SWT.NONE);
                TableItem row = cursor.getRow();
                int column = cursor.getColumn();
                text.setText(row.getText(column));
                text.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        // close the text editor and copy the data over
                        // when the user hits "ENTER"
                        if (e.character == SWT.CR) {
                            TableItem row = cursor.getRow();
                            int column = cursor.getColumn();
                            row.setText(column, text.getText());
                            text.dispose();
                        }
                        // close the text editor when the user hits "ESC"
                        if (e.character == SWT.ESC) {
                            text.dispose();
                        }
                    }
                });
                // close the text editor when the user clicks away
                text.addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusLost(FocusEvent e) {
                        text.dispose();
                    }
                });
                editor.setEditor(text);
                text.setFocus();
            }
        });

        for (Map.Entry<String, String> entry : data.entrySet()) {
            new TableItem(table, SWT.NONE).setText(new String[]{entry.getKey(), entry.getValue()});
        }

        return body;
    }

    private TableColumn createTableColumn(Table table, String columnName) {
        TableColumn column = new TableColumn(table, SWT.LEFT);
        column.setText(columnName);
        column.setMoveable(true);
        column.setResizable(true);
        column.setWidth(360);
        return column;
    }

    private Table createTable(Composite body) {
        Table table = new Table(body, SWT.BORDER | SWT.FULL_SELECTION | SWT.VIRTUAL);
        table.setEnabled(true);
        table.setRedraw(true);
        table.setVisible(true);
        table.setLinesVisible(true);
        table.setHeaderVisible(true);
        return table;
    }

    @Override
    protected Point getInitialSize() {
        return new Point(800, 500);
    }

    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        // Do nothing
    }

    @Override
    protected void setShellStyle(int newShellStyle) {
        super.setShellStyle(SWT.CLOSE | SWT.BORDER | SWT.TITLE | SWT.RESIZE);
        setBlockOnOpen(false);
    }

}
