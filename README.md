# CS410-Dining_Philosophers

Authors: Ryan Johnson, Dustin Gardner

    The dining-philosophers	problem	is considered a classic	synchronization	problem	
    because it is an example of a large class of concurrency-control	
    problems. It is	a simple representation	of the need to allocate	several	resources	
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

        Serves as the controller class for the Dining Philosophers program. A dining table stores each of the philosophers and
        their designated chopsticks, starts/stops the eating/thinking process, and displays the stats after the program has
        finished. Note that the number of philosophers may be changed away from the traditional 5 philosophers. However, the
        number of philosophers must be greater than 3 in order for the program to function correctly.


    Philosopher class:

        Class for representing a philosopher in the Dining Philosophers Solution. Each philosopher is run on a separate thread,
        which is started immediately upon the philosopher's initialization. Each philosopher is assigned both a left and right
        chopstick, both of which must be acquired in order for the philosopher to eat. The philosopher starts by thinking for
        a randomly chosen amount of time, simulated by having the thread sleep for the designated amount of time. Once the
        philosopher has finished thinking, it attempts to pick up both chopsticks to eat. If either is unavailable, any acquired
        chopstick is released before the philosopher returns to a thinking state before attempting to eat again.


	ChopStick class:

        Class for representing the chopsticks required for a philosopher to eat. Two chopsticks are assigned to each philosopher,
        one on the left and another on the right of their person. In order to eat, the philosopher must gain access to both
        of these chopsticks, which may only be used by a single philosopher thread at a time.

Setting up:

    GitHub's Dinning Philosophers application cloning:
    Ensure git is installed on your machine through terminal:
        macOS: git -v
        Windows: git --version
    Clone repository:
        git clone https://github.com/dustgard/CS410-Dining_Philosophers.git

Starting the Dinning_Philosophers application Command Prompt using Apache Ant:

    To start the application, a command prompt needs to be open in the directory
    for the program that was cloned.
        1. Ensure ant is installed: (Ant install instructions at bottom)
            MacOS: command: ant -v
            Windows: command: ant --version
        2. Command: to build a project using ant:
            macOS and Windows: ant
        3. Command: To run
            macOS and Windows: ant run

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