<h2 align="center"> Embedded browser for java desktop application on Windows platform. Browser EDGE.</h2>
<p>Sometimes a desktop application needs an embedded browser to display html pages and also to get various information like cookies, information from HTML DOM. <b>This example uses the library:</b> 
<b><a href="https://mvnrepository.com/artifact/org.eclipse.platform/org.eclipse.swt.win32.win32.x86_64">Standard Widget Toolkit For Windows</a></b>. The same library exists for other operating systems, such as <b><a href="https://mvnrepository.com/artifact/org.eclipse.platform/org.eclipse.swt.gtk.linux.x86_64">Linux</a></b>, <b><a href="https://mvnrepository.com/artifact/org.eclipse.platform/org.eclipse.swt.cocoa.macosx.x86_64">Mac OS X (Cocoa)</a></b>.</p>
<p>SWT for Windows uses <b>Internet Explorer by default</b>, but <a href="https://learn.microsoft.com/en-us/lifecycle/announcements/internet-explorer-11-end-of-support">support for Internet Explorer 11 ended on June 15, 2022</a>, and many sites have also dropped support for this browser. For example, when opening the Dropbox website using <b>IE</b>, you could see the following:</p>
<div align="center"><img src="https://github.com/yurievivan/EmbeddedBrowser/assets/11561851/dc860579-d36a-4a17-841e-def6461887d7"></div>
<br>
<p>Since version <b>3.116.0</b> it is possible to use <b>EDGE</b> as an embedded browser. The <b>EDGE</b> browser can be set as default via <b>system properties</b> as follows:</p>
<p>By VM arguments:
<code class="language-java">
-Dorg.eclipse.swt.browser.DefaultType="edge"
</code></p>
<p>In Java code:
<code class="language-java">
System.setProperty("org.eclipse.swt.browser.DefaultType", "edge");
</code></p>
<p>SWT uses <b>WebView2</b> to operate and manage the <b>EDGE</b> browser. More details about <b>WebView2 Support for SWT</b> can be found <a href="https://git.eclipse.org/r/plugins/gitiles/platform/eclipse.platform.swt/+/refs/heads/master/bundles/org.eclipse.swt/Readme.WebView2.md">here</a>. Documentation and more detailed information on Microsoft Edge WebView2: <a href="https://learn.microsoft.com/en-us/microsoft-edge/webview2/">https://learn.microsoft.com/en-us/microsoft-edge/webview2</a>.</p>
<p>The example shows the basic browser controls and getting cookies using a JS script and another case using the static getCookie method of the Browser class. 
Below are some examples from the code.</p>
<p>1. The <code>execute</code> method of the <code>Browser class</code> allows you to run JS scripts. This example shows a JS script for getting cookies, but it can be any other JS script. Below is an example.<code class="language-java">
  browser.execute(
  "getCookies(document.cookie.split(';').map(function(x) { "
    + "    return x.trim().split('='); "
    + "}));"
  );
</code> </br> To see the result of the work, just press the "<b>Cookie By JS</b>" button.</p>
<div align="center"><img src="https://github.com/yurievivan/EmbeddedBrowser/assets/11561851/5a258023-55b1-4450-b7b9-4e028860958b"></div>
</br>
<p>2. Using the static getCookie method of the Browser class, you can get cookie value by name. It is possible to get cookies even with HttpOnly and Secure attributes. Below is an example.<code class="language-java">
String cookieValue = Browser.getCookie(cookieName, url);
</code> </br> To see the result of the work, just click the "<b>Cookie</b>" button and enter the <b>Cookie Name</b>.</p></p>
<div align="center"><img src="https://github.com/yurievivan/EmbeddedBrowser/assets/11561851/4b3d213f-b099-4faf-b77c-33aeebcd2216"></div>
</br>
<p>A bug was found in version <b>3.116.0</b> of the SWT For Windows library, here is the link: <a href="https://bugs.eclipse.org/bugs/show_bug.cgi?id=572119">Bug 572119 - [Browser][Edge] cookie setting/getting does not work</a>. Version <b>3.117.0</b> and later no longer has this bug.</p>
