OUTDATED GUIDE! Since we switched to maven, this guide will be updated.

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
location is not important, but you will have your testserver there if you don't
reconfigure your personal AutoExport.xml, more on that later.
  
Install those in Eclipse once you've got it up and running:
	
	EGit - Git Integration for Eclipse (pretty much needed, should be pre-installed though)
	http://marketplace.eclipse.org/content/egit-git-team-provider
	
	Darkest Dark Theme with DevStyle (personal recommendation)
	http://marketplace.eclipse.org/content/darkest-dark-theme-devstyle
	
Now choose a folder for the source code from GitHub, it is neither needed to be
in your workspace nor to be on the same drive. Just remember the location ;)

In that folder open git bash (through the context menu) and execute:
git clone https://github.com/davidmc971/ModularMSMF.git
It should download the source code of ModularMSMF.

Next we will build Spigot using the Spigot BuildTools, follow the guide here:
https://www.spigotmc.org/wiki/buildtools/

After that, generate the javadoc for Spigot-API, follow this guide:
https://www.spigotmc.org/wiki/creating-offline-javadoc-for-the-spigot-api/

Now to the Eclipse setup part.
If you have at least some experience using Eclipse, it will be easier for you. :)
In Eclipse, we skip the welcome screen and create a new Java Project.
You can do so through the context menu of your package explorer for example.
Conveniently name your new project ModularMSMF and click "Finish".

Completely remove the existing src folder, right-click your project and
go into "Build Path > Configure Build Path...". Under the tab "Source" click
on "Link Source" and browse for the src folder in your cloned git repository.
Make sure you didn't select any of the subfolders in src so it looks similar
to this: "some-path\ModularMSMF\src" and click "Finish".
Next, under the "Libraries" tab click on classpath and then on
"Add External JARs...". Navigate to your Spigot BuildTools folder and select
the file




Navigate to your cloned git reporsitory and select the "src" folder.
Click next. Now we include the Spigot-API to the classpath of our project:
