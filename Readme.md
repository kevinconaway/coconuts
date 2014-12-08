Please see my [solution notes](Solution.md) for the described [problem](Problem.md).

Java is my "native tongue" so to speak so I wrote a reference implementation in Java to save myself from having to both solve the problem and remember how to code python.  It has been a number of years since I've written a line of python so please excuse the Java idioms in the code.  I'm certain that are more "pythonic" ways to implement some of the logic that I wrote.

If you want to run the java solution, cd to the *java* directory and run

    export MAVEN_OPTS="-Xmx2048m -XX:MaxPermSize=512m"
    mvn clean compile exec:java

You can also run the test cases with

    mvn clean test

If you want to run the python solution, cd to the *python* directory and run

    python main.py data/flight_paths.txt

You can also run the test cases with:

    python test.py