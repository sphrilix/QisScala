# QisScala
To retrieve your marks at the university of Passau, you have to download a PDF. Since this a tedious task, this project does this automatically and prints your
results to the command line. It also can be configured to check every 15 minutes for updates. This is implemented, because you only get a notification of newly added
grades once per day. Therefore, a lot of students keep downloading their marks several times a day.
## Requirements
- Java
- Scala 3
- sbt

## Init
1. Clone the project.
2. Enter in the command line `sbt assembly` in the root directory of the project, which exports the project to a **JAR**. The generated **JAR** can be found
   in `./target/scala-3.X.X`.
3. After that you have to specify a new file with the initial parameters. A boilerplate for this file can be seen in the root directory of the project. There you
   have to specify your credentials for HisQis or Mail credentials to get a notification on updates.

## Usage
1. Start a terminal session.
2. Type `java -jar [path_to_jar/jar_name] -f [path to init file] (-p if you just want to print your grades and not let the programm check for updates)` and hit
   enter.
3. If your set -p your marks are now going to be nicely printed to your terminal.
4. Else, the program checks every 15 minutes whether you got new grades or not. If so, it notifies you over mail and you can print your grades to the terminal
   using the tool.