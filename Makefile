.PHONY: all datamodel webserver run clean

MVN=mvn
CLASSPATH=./target/*.jar
JAVA_OPT=-cp $(CLASSPATH)
JAVA=java $(JAVA_OPT)
TARGET=fr.univparis.metro.Webserver
TERMINAL_TARGET=fr.univparis.metro.App
GRAPH_EXPORTER_TARGET=fr.univparis.metro.GraphExporter

# Target all builds the project.
all: datamodel webserver

# Target run executes the program and start with target all to build the
# project.
datamodel:
	cd datamodel && $(MVN) package install

webserver:
	cd webserver && $(MVN) compile test assembly:single

run :
	cd webserver && $(JAVA) $(TARGET)


run_terminal :
	cd webserver && $(JAVA) $(TERMINAL_TARGET)

export_to_dot :
	cd webserver && $(JAVA) $(GRAPH_EXPORTER_TARGET) $(ARGS)

test:
	cd datamodel && $(MVN) test
	cd webserver && $(MVN) test

diagram:
	cd datamodel && $(MVN) process-classes

# Target clean removes all files produced during build.
clean :
	cd datamodel && $(MVN) clean
	cd webserver && $(MVN) clean
