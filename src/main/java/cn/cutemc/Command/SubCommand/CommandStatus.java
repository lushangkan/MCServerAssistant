package cn.cutemc.Command.SubCommand;

import cn.cutemc.Command.Command;
import cn.cutemc.MCServerAssistant;
import cn.cutemc.Utils.MessageUtils;
import cn.cutemc.Utils.ServerUtils;
import lombok.Getter;
import lombok.Setter;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.xbill.DNS.TextParseException;

import java.net.UnknownHostException;
import java.util.HashMap;

@Getter
@Setter
public class CommandStatus extends Command {

    public GroupMessageEvent event;

    public CommandStatus(GroupMessageEvent event) {
        super(event);
        setEvent(event);
        CommandHandler();
    }

    public void CommandHandler() {

        String serverHost = MCServerAssistant.config.getEnableGroup().get(event.getGroup().getId());

        HashMap<String, Integer> status;

        try {
            status = ServerUtils.getServerStatus(serverHost);
        } catch (TextParseException | UnknownHostException e) {
            MessageUtils.sendReplyMsg(event.getGroup(),"无法解析服务器地址,请联系管理员", event.getSource());
            MCServerAssistant.INSTANCE.getLogger().info("服务器状态已发送给: " + event.getSender().getId() + " " + event.getSender().getNick());
            MCServerAssistant.INSTANCE.getLogger().info("无法解析服务器地址" + serverHost + "，请查看配置文件");
            return;
        } catch (InterruptedException e) {
            MessageUtils.sendReplyMsg(event.getGroup(),"连接超时,请重试或联系管理员", event.getSource());
            MCServerAssistant.INSTANCE.getLogger().info("服务器状态已发送给: " + event.getSender().getId() + " " + event.getSender().getNick());
            MCServerAssistant.INSTANCE.getLogger().info("连接服务器" + serverHost + "超时");
            return;
        }

        if (status == null) {
            MessageUtils.sendReplyMsg(event.getGroup(),"连接超时,请重试或联系管理员", event.getSource());
            MCServerAssistant.INSTANCE.getLogger().info("服务器状态已发送给: " + event.getSender().getId() + " " + event.getSender().getNick());
            MCServerAssistant.INSTANCE.getLogger().info("连接服务器" + serverHost + "超时");
            return;
        }

        int playerCount = status.get("playercount");
        int maxPlayerCount = status.get("maxplayercount");

        MessageUtils.sendReplyMsg(event.getGroup(),"服务器状态\n" +
                "在线人数: " + playerCount + "/" + maxPlayerCount, event.getSource());

        MCServerAssistant.INSTANCE.getLogger().info("服务器状态已发送给: " + event.getSender().getId() + " " + event.getSender().getNick());
    }
}
