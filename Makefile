J = javac

SRC = src
SOURCES = $(shell find ./src/ -name *.java)

TARGET = bin
CP = ".:./libs/flatlaf-2.0.1.jar:./libs/flatlaf-intellij-themes-2.0.1.jar"

all: init run

init: 
	@mkdir -p bin

run: compile
	@cd bin/chatRMI && jar cvfe ../../libs/Client.jar chatRMI.client.Client client/* common/* remoteInterfaces/*
	@cd bin/chatRMI && jar cvfe ../../libs/Server.jar chatRMI.server.Server server/* common/* remoteInterfaces/*

compile: $(SOURCES:.%.java=.%.class)

%.class: %.java
	@echo ":: Compiling classes... ::"
	@echo "Compiling $*.java.."
	@$(J) -Xlint -sourcepath $(SRC) -cp $(CP) -d $(TARGET) -encoding utf-8 $*.java

clean:
	@rm -R -f bin/* libs/Server.jar libs/Client.jar