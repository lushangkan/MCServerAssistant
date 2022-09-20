package cn.cutemc;

import cn.cutemc.Config.PluginConfig;
import cn.cutemc.EventHandler.MainHandler;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.BotEvent;

public final class MCServerAssistant extends JavaPlugin {
    public static final MCServerAssistant INSTANCE = new MCServerAssistant();

    //config
    public static PluginConfig config = PluginConfig.INSTANCE;

    private MCServerAssistant() {
        super(new JvmPluginDescriptionBuilder("cn.cutemc.MCServerAssistant", "0.1.0")
                .name("MCServerAssistant")
                .author("lushangkan")
                .build());


    }

    @Override
    public void onEnable() {
        getLogger().info("Loading config...");
        reloadPluginConfig(PluginConfig.INSTANCE);

        getLogger().info("Registering event handler...");
        EventChannel<Event> channel = GlobalEventChannel.INSTANCE.filter(ev -> ev instanceof BotEvent);

        MainHandler mainHandler = new MainHandler(this, channel);

        getLogger().info("Plugin loaded!");
    }
}