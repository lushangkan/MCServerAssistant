package cn.cutemc.Command.SubCommand;

import cn.cutemc.Command.Command;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import net.mamoe.mirai.message.data.QuoteReply;

public class CommandHelp extends Command {

    public GroupMessageEvent event;

    public CommandHelp(GroupMessageEvent event) {
        super(event);
        this.event = event;
        CommandHandler();
    }

    public void CommandHandler() {
        MessageChain text = new PlainText("")
                .plus(new QuoteReply(event.getSource()))
                .plus(" ")
                .plus("帮助信息")
                .plus("\n我是傻逼");
        event.getGroup().sendMessage(text);
    }
}
