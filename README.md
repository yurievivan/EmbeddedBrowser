<h2 align="center"> Embedded browser for java desktop application on Windows platform.</h2>
<p>Sometimes a desktop application needs an embedded browser to display html pages and also to get various information like cookies, information from HTML DOM. <b>This example uses the library:</b> 
<b><a href="https://mvnrepository.com/artifact/org.eclipse.platform/org.eclipse.swt.win32.win32.x86_64">Standard Widget Toolkit For Windows</a></b>. The same library exists for other operating systems, such as <b><a href="https://mvnrepository.com/artifact/org.eclipse.platform/org.eclipse.swt.gtk.linux.x86_64">Linux</a></b>, <b><a href="https://mvnrepository.com/artifact/org.eclipse.platform/org.eclipse.swt.cocoa.macosx.x86_64">Mac OS X (Cocoa)</a></b>.</p>
<p>SWT for Windows uses <b>Internet Explorer by default</b>, but <a href="https://learn.microsoft.com/en-us/lifecycle/announcements/internet-explorer-11-end-of-support">support for Internet Explorer 11 ended on June 15, 2022</a>, and many sites have also dropped support for this browser. For example, when opening the Dropbox website using <b>IE</b>, you could see the following:</p>
<div align="center"><img src="https://github.com/yurievivan/EmbeddedBrowser/assets/11561851/dc860579-d36a-4a17-841e-def6461887d7"></div>
<br>
<p>Since version <b>3.116.0</b> it is possible to use <b>EDGE</b> as an embedded browser.</p>
