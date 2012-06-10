Setup / Building

	- Third Party Requirements:
	
	Eclipse with Android SDK
	OpenCV 2.3.1 (for Android, also listed as OpenCV4Android)
	android-binding-0.49-update.jar
	Robotium Solo 3.2.1 (robotium-solo-3.2.1.jar)
	
	- Setup
	
	Import the Operation-Valkyrie Project, the Operation-Valkyrie-Test Project.
	
	Setup OpenCV (http://opencv.itseez.com/doc/tutorials/introduction/android_binary_package/android_binary_package.html)
		- Import OpenCV folder as exisiting project
		- Link the Operation-Valkyrie Project with OpenCV: Project Properties (Main Project) -> Android -> Library -> Add -> Choose OpenCV Project
	
	Setup android-binding-0.49-update.jar: Project Properties (Main Project) -> Java Build Path -> Librarys -> select jar location
	
	Setup Robotium Solo: Project Properties (Test Project) -> Java Build Path -> Librarys -> select jar location