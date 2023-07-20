package com.embedded.browser;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 *
 * @author ivan.yuriev
 */
public class CookieDialog extends TitleAreaDialog {

    private Text txtCookieValue;
    private String url;

    public CookieDialog(Shell parentShell, String url) {
        super(parentShell);
        setDefaultImage(new Image(parentShell.getDisplay(), getClass().getResourceAsStream("cookie.png")));
        this.url = url;
    }

    @Override
    public void create() {
        super.create();
        setTitle("Cookie Dialog");
        setMessage("HTTP cookie", IMessageProvider.INFORMATION);
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite area = (Composite) super.createDialogArea(parent);
        Composite container = new Composite(area, SWT.NONE);
        container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        GridLayout layout = new GridLayout(2, false);
        container.setLayout(layout);

        createCookieName(container);
        createCookieValue(container);

        return area;
    }

    private void createCookieName(Composite container) {
        Label lbtFirstName = new Label(container, SWT.NONE);
        lbtFirstName.setText("Cookie Name");

        GridData dataFirstName = new GridData();
        dataFirstName.grabExcessHorizontalSpace = true;
        dataFirstName.horizontalAlignment = GridData.FILL;

        Text txtCookieName = new Text(container, SWT.BORDER);
        txtCookieName.setLayoutData(dataFirstName);
        txtCookieName.addModifyListener((ModifyEvent e) -> {
            String cookie = Browser.getCookie(txtCookieName.getText(), url);
            txtCookieValue.setText(cookie != null ? cookie : "");
        });
    }

    private void createCookieValue(Composite container) {
        Label lbtLastName = new Label(container, SWT.NONE);
        lbtLastName.setText("Cookie Value");

        GridData dataLastName = new GridData();
        dataLastName.grabExcessHorizontalSpace = true;
        dataLastName.horizontalAlignment = GridData.FILL;
        txtCookieValue = new Text(container, SWT.BORDER);
        txtCookieValue.setEditable(false);
        txtCookieValue.setBackground(new Color(Display.getDefault(), 255, 255, 255));
        txtCookieValue.setLayoutData(dataLastName);
    }

    @Override
    protected Point getInitialSize() {
        return new Point(800, 310);
    }

    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        createButton(parent, IDialogConstants.CANCEL_ID,
                IDialogConstants.CANCEL_LABEL, false);

    }

    @Override
    protected void setShellStyle(int newShellStyle) {
        super.setShellStyle(SWT.CLOSE | SWT.BORDER | SWT.TITLE | SWT.RESIZE);
        setBlockOnOpen(false);
    }

}
