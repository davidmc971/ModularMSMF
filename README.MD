You need to have the following installed (when using Windows):
	
	Java Development Kit (JDK) (at least Java 8)
	Get it here: http://www.oracle.com/technetwork/java/javase/downloads/index.html
	
	Notepad++ (optional, but definitely recommended, install before git)
	Get it here: https://notepad-plus-plus.org/download/
	
	Git for Windows (choose Notepad++ as editor if installed)
	Get it here: https://git-scm.com/download/win
	
	Eclipse IDE for Java Developers
	Get the installer here: https://www.eclipse.org/downloads/
	
After installing all these, choose your eclipse workspace, for the plugin the
location is not important, but you will have your Testserver there if you don't
reconfigure your personal AutoExport.xml, more on that later.
  
Install those in Eclipse once you've got it up and running:
	
	EGit - Git Integration for Eclipse (pretty much needed)
	http://marketplace.eclipse.org/content/egit-git-team-provider
	
	Darkest Dark Theme with DevStyle (personal recommendation)
	http://marketplace.eclipse.org/content/darkest-dark-theme-devstyle
	
Now choose a folder for the source code from GitHub, it is neither needed to be
in your workspace nor to be on the same drive. Just remember the location ;)

In that folder execute:
git clone https://github.com/davidmc971/ModularMSMF.git
It should download the source code of ModularMSMF.

Next we will build Spigot using the Spigot BuildTools, follow the guide here:
https://www.spigotmc.org/wiki/buildtools/

After that, generate the javadoc for Spigot-API, follow this guide:
https://www.spigotmc.org/wiki/creating-offline-javadoc-for-the-spigot-api/

Now we go into eclipse and create a new Java Project.
Conveniently name it ModularMSMF and click next.
Remove the existing src folder and click on "Link to source".
Navigate to your cloned git reporsitory and select the "src" folder.
Click next. Now we include the Spigot-API to the classpath of our project:
