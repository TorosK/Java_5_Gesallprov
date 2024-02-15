Console command to compile all java files in a directory:

javac *.java

For javadoc execution into 'doc' folder:

javadoc -d doc *.java

___

run program by opening terminal in project directory and adapt to your individual credentials:

Usage: java EmailSender <host> <port> <username> <password> <to> <subject> <message>

Example 1:

java EmailSender smtp.example.com 465 myusername mypassword recipient@example.com "Test Subject" "This is a test message."

Example 2:

java EmailSender smtp.gmail.com 465 dittanvändarnamn@gmail.com dittlösenord mottagare@example.com "Test Subject" "This is a test message."
___

To create a runnable .jar file:

jar cmf manifest.txt EmailSender_Toros.jar com javax *.class META-INF

___

To run .jar file:

java -jar EmailSender_Toros.jar