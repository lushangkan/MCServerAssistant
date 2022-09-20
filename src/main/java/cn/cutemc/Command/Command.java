package cn.cutemc.Command;

import cn.cutemc.Utils.CommandUtils;
import net.mamoe.mirai.event.events.MessageEvent;

public class Command {

    public String[] args;
    public String commandText;

    public Command(MessageEvent event) {
        //Get commandText
        commandText = event.getMessage().contentToString();
        //Get command's args
        args = CommandUtils.getArgs(commandText);
    }

    public void CommandHandler() {}
}
