package models;

import java.util.ArrayList;

public class getCommandMode {
    ArrayList<Object> getCommand;

    public getCommandMode() {
    }

    public getCommandMode(ArrayList<Object> getCommand) {
        this.getCommand = getCommand;
    }

    public ArrayList<Object> getGetCommand() {
        return getCommand;
    }

    public void setGetCommand(ArrayList<Object> getCommand) {
        this.getCommand = getCommand;
    }
}
