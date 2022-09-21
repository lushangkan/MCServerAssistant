package cn.cutemc.Command.SubCommand;

import cn.cutemc.Command.Command;
import cn.cutemc.MCServerAssistant;
import cn.cutemc.Utils.MessageUtils;
import lombok.Getter;
import lombok.Setter;
import net.mamoe.mirai.event.events.GroupMessageEvent;

@Getter
@Setter
public class CommandHelp extends Command {

    public GroupMessageEvent event;

    public CommandHelp(GroupMessageEvent event) {
        super(event);
        setEvent(event);
        CommandHandler();
    }

    public void CommandHandler() {
        event.getGroup().sendMessage(MessageUtils.toReplyMsg("帮助信息\n" +
                "/help -- 查看帮助\n" +
                "/status -- 查看服务器状态" , event.getSource()));
        MCServerAssistant.INSTANCE.getLogger().info("帮助信息已发送给: " + event.getSender().getId() + " " + event.getSender().getNick());
    }
}
