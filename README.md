# CS410-Dining_Philosophers

Authors: Ryan Johnson, Dustin Gardner

    The dining-philosophers	problem is often used to discuss solutions related to deadlock avoidance, mutual exclusion, 
    and resource allocation strategies. It is a simple representation of the need to allocate several resources	
    among several processes	in a deadlock-free and starvation-free manner.	
    The Dining Philosophers problem is a classic concurrency problem in computer science, 
    originally formulated by E.W. Dijkstra in 1965. It illustrates synchronization issues 
    and the challenges of resource allocation in concurrent systems. 
    The problem arises when multiple philosophers simultaneously pick up forks and try to eat. If all philosophers 
    simultaneously pick up the fork to their left, for instance, they will all wait indefinitely for the fork to their 
    right to become available, resulting in a deadlock where no philosopher can eat.

Rules needed:
       
        1. Mutual Exclusion: Ensure that resources are accessed exclusively by only one process at a time. 
                This prevents multiple processes from accessing the same resource simultaneously.

        2. Hold and Wait: Processes should acquire all the necessary resources before starting execution to prevent
                holding resources while waiting for others. If a process cannot acquire all resources, it should
                release the acquired resources and retry later.

        3. No Preemption: Resources cannot be forcibly taken away from a process; they must be released voluntarily
                by the process holding them. This rule prevents situations where a process is waiting indefinitely 
                for a resource that another process is holding.

        4. Circular Wait: Avoid situations where processes are waiting for resources in a circular chain. This can 
                be achieved by imposing a total ordering on resources and requiring processes to request resources 
                in increasing order.

Lab Tasks:

        1. The solution must be uploaded to GitHub and the location of the
                repository emailed to the professor.

        2. Each student will grade both themselves and their partners.

        3. The presentation should include an explanation of the group’s
                solution and how it	solves the problem.

        4. The presentation should be 10-20 minutes long, and each student
                must speak during the presentation to describe some	part of	the
                problem and/or group’s solution.
       
        5. The philosophers are seperate threads and access a shared resource safely.

        6. Avoid starvation and deadlock allowing all philosophers to eat.

        7. Display information to the users:
                - Each philosopher:
                    - How many times the philosopher was able to eat.
                    - Chopstick right and left numbers assigned.
                    - Total thinking time and percent of time thinking compared to total dinning time allowed.
                    - Total eating time and percent of time eating compared to total dinning time allowed.

Dining Philosophers program design explanation:

	Dinnig Table class:

        1. Storage of Philosophers and Chopsticks: The DiningTable class stores information about each philosopher 
                participating in the dining process, along with their designated chopsticks.

        2. Process Management: The class is responsible for starting and stopping the dining time allowed.

        3. Dynamic Adjustment of Philosopher Count: The DiningTable class allows for the flexibility of changing 
                the number of philosophers participating in the dining process. While the traditional setup involves 
                five philosophers, this class permits adjusting the count. However, it enforces a minimum requirement
                of more than three philosophers for the program to function correctly. This feature provides 
                scalability and allows users to experiment with different configurations.

        4. Statistics Display: After the dining process is complete, the DiningTable class is responsible for 
                displaying statistics related to the philosophers' activities. This includes metrics such as 
                the total time spent eating, the total time spent thinking, chopstick assignment numbers, and
                total times they were able to eat.

    Philosopher class:

        1. Threaded Execution: Each philosopher is represented by a separate thread, allowing them to execute 
                concurrently with other philosophers. Upon initialization of a Philosopher instance, a thread is 
                immediately started to simulate the philosopher's actions.

        2. Chopstick Management: Each philosopher is assigned both a left and a right chopstick, which are essential 
                for them to eat. The philosopher must acquire both chopsticks in order to start eating.

        3. Thinking Process: Initially, the philosopher starts by thinking for a randomly chosen amount of time. 
                This thinking period is simulated by having the thread sleep for the designated amount of time. 
                During this time, the philosopher does not require any resources.

        4. Eating Process: After finishing the thinking phase, the philosopher attempts to pick up both chopsticks 
                to begin eating. If either chopstick is already held by another philosopher, 
                the philosopher releases any acquired chopstick and returns to the thinking state before attempting 
                to eat again. This mechanism ensures that the philosopher follows the rules of the 
                Dining Philosophers problem, avoiding deadlock and fairness for resources.


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