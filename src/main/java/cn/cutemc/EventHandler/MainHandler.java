package cn.cutemc.EventHandler;

import cn.cutemc.EventHandler.Listener.Msg.GroupMsg;
import cn.cutemc.MCServerAssistant;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;

import java.util.List;
import java.util.Map;

public class MainHandler {

    public MainHandler(JavaPlugin plugin ,EventChannel<Event> channel) {

        //Get enable bot and group
        List<Long> bot = MCServerAssistant.config.getEnableBot();
        Map<Long, String> groupMap = MCServerAssistant.config.getEnableGroup();

        //Register event handler

        EventChannel<Event> subChannel;

        for (Long botId : bot) {
            subChannel = channel.filter(ev -> ((BotEvent) ev).getBot().getId() == botId);

            //Register Listener
            subChannel.subscribeAlways(GroupMessageEvent.class, event -> new GroupMsg(event,groupMap));
        }
    }
}
