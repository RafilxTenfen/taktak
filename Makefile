
JFLAGS = -g
JC = javac
JV = java
MAIN = TakTak/App
SERVER = server/servidor.jar 

CLASSES =  $(shell find . -path TakTak -prune -o -name "*.java" -print)
JARS = TakTak/ngnrtFramework.jar
.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java


build: 
	javac -Xlint:deprecation -Xlint:unchecked -cp "$(JARS)" $(CLASSES)
# build: classes

run: classes 
	$(MAKE) runjava

classes: $(CLASSES:.java=.class)  

clean:
	$(RM) *.class

server-up:
	java -jar $(SERVER)