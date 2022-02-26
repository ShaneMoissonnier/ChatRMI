J = javac

SRC = src
SOURCES = $(shell find ./src/ -name *.java)

TARGET = bin
CP = ".:./libs/flatlaf-2.0.1.jar:./libs/flatlaf-intellij-themes-2.0.1.jar"

all: init run

init: 
	@mkdir -p bin

run: compile
	@cd bin/ && jar cvfe ../libs/Client.jar chatRMI.client.Client chatRMI/client/* chatRMI/common/* chatRMI/remoteInterfaces/*
	@cd bin/ && jar cvfe ../libs/Server.jar chatRMI.server.Server chatRMI/server/* chatRMI/common/* chatRMI/remoteInterfaces/*
	@cd bin/ && rmiregistry &

launch-server:
	@java -cp bin/:libs/Server.jar chatRMI.server.Server

launch-client:
	@java -cp bin/:libs/Client.jar:libs/flatlaf-2.0.1.jar:libs/flatlaf-intellij-themes-2.0.1.jar chatRMI.client.Client localhost default_name &

compile: $(SOURCES:.%.java=.%.class)

%.class: %.java
	@echo ":: Compiling classes... ::"
	@echo "Compiling $*.java.."
	@$(J) -Xlint -sourcepath $(SRC) -cp $(CP) -d $(TARGET) -encoding utf-8 $*.java

clean:
	@rm -R -f bin/chatRMI/* libs/Server.jar libs/Client.jar
	@pkill rmiregistry || true
	@pkill java || true