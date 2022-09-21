package cn.cutemc.Command;

import cn.cutemc.Command.SubCommand.CommandHelp;
import cn.cutemc.Command.SubCommand.CommandStatus;

public enum CommandEnum {

    //Command
    HELP("help", CommandHelp.class),
    STATUS("status", CommandStatus.class),
    ;

    private final String command;
    private final Class classFile;

    CommandEnum(String command, Class classFile) {
        this.command = command;
        this.classFile = classFile;
    }

    public String getCommand() {
        return command;
    }

    public Class getCommandClass() {
        return classFile;
    }

    /**
     * 通过Command获取对应类型
     * @param command 指令
     * @return 类型
     */
    public static Class getClassFile(String command) {
        for (CommandEnum value : CommandEnum.values()) {
            if (value.getCommand().equals(command)) {
                return value.getCommandClass();
            }
        }
        return null;
    }

}
