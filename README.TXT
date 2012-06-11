
COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
© Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf

Operation-Valkyrie Ascii Camera

Description

  Operation Valkyrie is a camera application with an integrated ASCII filter. You
  can use a live preview and some filter settings like: 
    # color-mode
    # background color
    # fontsize
    # ..
    
  Your can access the pictures with our build-in gallery and with any file browser.

Credits

  Paul Neuhold
  Laurenz Theuerkauf
  Alexander Ritz
  Jakob Schweighofer
  Milo Tischler
  
Third Party Credits

  TouchImageView.java: Michael Ortiz, Patrick Lackemacher
  MultiDirectionSlidingDrawer.java: Alessandro Crugnola 

Setup / Building

	- Third Party Requirements:
	
	Eclipse with Android SDK (Min SDK API Level 8, Android 2.2)
	OpenCV 2.3.1 (for Android, also listed as OpenCV4Android)
	android-binding-0.49-update.jar
	Robotium Solo 3.2.1 (robotium-solo-3.2.1.jar)
	
	- Setup
	
	1) Import the Operation-Valkyrie Project, the Operation-Valkyrie-Test Project into eclipse, 
     it is saved as an eclipse project so you should have no troubles with that.
	
	2) Setup OpenCV (http://opencv.itseez.com/doc/tutorials/introduction/android_binary_package/android_binary_package.html)
	  - We also added a fully functional OpenCV distribution to the zip. You can just import this (libs folder).
    - 2.1) Import OpenCV folder as exisiting project 
           (under its project properties -> Android -> is Library -> check if not already checked)
		- 2.2) Link the Operation-Valkyrie Project with OpenCV: 
           Project Properties (Main Project) -> Android -> Library -> Add -> Choose OpenCV Project
	
	3) If the project still contains errors:
    - Setup android-binding-0.49-update.jar: 
      Project Properties (Main Project) -> Java Build Path -> Librarys -> select jar location
	
	4) Setup the GUI-Testing Framework - Robotium Solo:
    - Project Properties (Test Project) -> Java Build Path -> Librarys -> select jar location
    - In some cases, Robotium Solo is already referenced but there is a builderror anyway.. 
      you can try to remove the jar and re-add it
    - Further informations: http://code.google.com/p/robotium/wiki/Getting_Started 
    
Your project should now be ready to Go. Build and Run and enjoy the Operation-Valkyrie Ascii Camera.