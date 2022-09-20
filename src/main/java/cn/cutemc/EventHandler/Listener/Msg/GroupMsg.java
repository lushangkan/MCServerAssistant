package cn.cutemc.EventHandler.Listener.Msg;

import cn.cutemc.Command.Command;
import cn.cutemc.MCServerAssistant;
import cn.cutemc.Utils.CommandUtils;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.MessageSource;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;

public class GroupMsg {

    public GroupMsg(GroupMessageEvent event, List<Long> group) {
        if (!group.contains(event.getGroup().getId())) return;

        //Get msg
        String msg = Objects.requireNonNull(event.getMessage().contentToString());

        //If this msg is not a command, then return
        if (!CommandUtils.isCommand(msg)) return;

        //Match command
        Class commandClass = CommandUtils.matchCommand(msg);

        //New commandClass Class
        try {
            commandClass.getConstructor(GroupMessageEvent.class).newInstance(event);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
