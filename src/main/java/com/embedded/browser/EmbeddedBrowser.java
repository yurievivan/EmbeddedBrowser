package com.embedded.browser;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.browser.StatusTextEvent;
import org.eclipse.swt.browser.WindowEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class EmbeddedBrowser {

    private Browser browser;
    private Display display;
    private Composite controls;
    private Map<String, String> cookieProp = new HashMap<>();

    public EmbeddedBrowser(String location) {
        display = new Display();
        Shell shell = new Shell(display);
        shell.setText("Embedded Browser");
        shell.setImage(new Image(display, getClass().getResourceAsStream("globe.png")));
        shell.setLayout(new FormLayout());

        controls = new Composite(shell, SWT.NONE);
        FormData data = new FormData();
        data.top = new FormAttachment(0, 0);
        data.left = new FormAttachment(0, 0);
        data.right = new FormAttachment(100, 0);
        controls.setLayoutData(data);

        Label status = new Label(shell, SWT.NONE);
        data = new FormData();
        data.left = new FormAttachment(0, 0);
        data.right = new FormAttachment(100, 0);
        data.bottom = new FormAttachment(100, 0);
        status.setLayoutData(data);

        browser = new Browser(shell, SWT.BORDER);
        data = new FormData();
        data.top = new FormAttachment(controls);
        data.bottom = new FormAttachment(status);
        data.left = new FormAttachment(0, 0);
        data.right = new FormAttachment(100, 0);
        browser.setLayoutData(data);

        controls.setLayout(new GridLayout(9, false));

        Button button = createButton("left.png", true, () ->  browser.back());
        button =  createButton("right.png", true, () -> browser.forward());
        button = createButton("refresh.png", true, () -> {
            browser.refresh();
            return null;
        });

        button = createButton("stop.png", true, () -> {
                browser.stop();
                return null;
        });

        button = createButton("Cookie", false, () -> {
            CookieDialog dialog = new CookieDialog(Display.getCurrent().getActiveShell(), browser.getUrl());
            dialog.create();
            dialog.open();
            return null;
        });

        button = createButton("Cookie By JS", false, ()
                -> browser.execute("getCookies(document.cookie.split(';').map(function(x) { "
                        + "    return x.trim().split('='); "
                        + "}));"));

        new BrowserFunction(browser, "getCookies") {
            @Override
            public Object function(Object[] objects) {
                cookieProp.clear();
                Object[] keyValuePairs = (Object[]) objects[0];
                for (Object keyValue : keyValuePairs) {
                    Object[] pair = (Object[]) keyValue;
                    cookieProp.put(pair[0].toString(), pair[1].toString());
                }
                CookieTableDialog dialog = new CookieTableDialog(Display.getCurrent().getActiveShell(), cookieProp);
                dialog.open();
                return null;
            }
        };

        Text url = new Text(controls, SWT.BORDER);
        url.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        url.setFocus();

        button = createButton("go.png", true, () -> browser.setUrl(url.getText()));
        shell.setDefaultButton(button);

        browser.addCloseWindowListener((WindowEvent event) -> ((Browser) event.widget).getShell().close());
        browser.addLocationListener(new LocationListener() {
            @Override
            public void changing(LocationEvent event) {
                url.setText(event.location);
            }

            @Override
            public void changed(LocationEvent event) {
                url.setText(event.location);
            }
        });
        browser.addStatusTextListener((StatusTextEvent event) -> status.setText(event.text));

        if (location != null) {
            browser.setUrl(location);
        }

        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }

    private Button createButton(String data, boolean isImage, Supplier<Object> f) {
        Button button = new Button(controls, SWT.PUSH);
        if (isImage) {
            button.setImage(new Image(display, getClass().getResourceAsStream(data)));
        } else {
            button.setText(data);
        }
        button.setLayoutData(new GridData(SWT.NONE, SWT.FILL, false, true));
        button.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                f.get();
            }
        });
        return button;
    }

    public static void main(String[] args) {
        System.setProperty("org.eclipse.swt.browser.DefaultType", "edge");
        new EmbeddedBrowser("https://500px.com/");
    }
}
