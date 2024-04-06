# CS410-Dining_Philosophers

Authors: Ryan Johnson, Dustin Gardner

    The	dining-philosophers	problem	is considered a classic	synchronization	problem	
    because it is an example of a large class of concurrency-control	
    problems. It is	a simple representation	of the need	to allocate	several	resources	
    among several processes	in a deadlock-free and starvation-free manner.	

Lab Tasks:

       1. The solution must be uploaded to GitHub and the location of the
                repository emailed to the professor.

        2. Each student will grade both themselves and their partners.

        3. The presentation should include an explanation of the group’s
                solution and how it	solves the problem.

        4. The presentation should be 10-20 minutes long, and each student
                must speak during the presentation to describe some	part of	the
                problem and/or group’s solution.

Bell Choir program design explanation:

	Dinnig Table class:

        Serves as the controller class for the DiningPhilosophers program. A dining table stores each of the philosophers and
        their designated chopsticks, starts/stops the eating/thinking process, and displays the stats after the program has
        finished.


    Philosopher class:



	ChopStick class:

        ChopStick mutex class for the Philosophers class which are acquired in a set before use.
        The chopsticks are used to simulated eating utensils for each of the philosophers.The class, which is based off of
        a simple mutex, ensures that there is only one thread at a time that can access the methods.

Setting up:

    GitHub's Dinning Philosophers application cloning:
    Ensure git is installed on your machine through terminal:
        macOS: git -v
        Windows: git --version
    Clone repository:
        git clone https://github.com/dustgard/CS410-Dining_Philosophers.git

Starting the Bell Choir application Command Prompt using Apache Ant:

    To start the application, a command prompt needs to be open in the directory
    for the program that was cloned.
        1. Ensure ant is installed: (Ant install instructions at bottom)
            MacOS: command: ant -v
            Windows: command: ant --version
        2. Command: to build a project using ant:
            macOS and Windows: ant
        3. Command: to run java program with the default song "Mary Had a Little Lamb"
            macOS and Windows: ant run
        4. Command: to run java program with ant parameter using a custom song.
            macOS and Windows: ant -Dsong="[enter song location here]" run
            example ant -Dsong="C:\Users\dust\Drive\MaryLamb.txt" run

Installing Apache Ant:

    Instructions for installation found at: https://ant.apache.org/manual/install.html
    and https://www.tutorialspoint.com/ant/ant_environment.html

    When testing successful installation pay action to setting path variables/environmental variables.

    Possible error message after installing ant:

        Examples of macOS error fixes:
            After installing ant, move it to /usr/local/ using this
            command mv /Users/admin/Downloads/apache-ant-1.9.4  /usr/local/
            Now try to set environment variables using nano $HOME/.profile

            Check the below lines.
            Export PATH=$PATH:/usr/local/ant/bin/
            export ANT_HOME=/usr/local/ant
            export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.7.0._71..jdk/Contents/Home/bin