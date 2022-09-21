package cn.cutemc;

import cn.cutemc.Utils.ServerUtils;
import com.github.steveice10.mc.auth.service.SessionService;
import com.github.steveice10.mc.protocol.MinecraftConstants;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.mc.protocol.data.status.ServerStatusInfo;
import com.github.steveice10.mc.protocol.data.status.handler.ServerInfoHandler;
import com.github.steveice10.mc.protocol.packet.status.clientbound.ClientboundStatusResponsePacket;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.event.session.ConnectedEvent;
import com.github.steveice10.packetlib.event.session.DisconnectedEvent;
import com.github.steveice10.packetlib.event.session.SessionAdapter;
import com.github.steveice10.packetlib.packet.Packet;
import com.github.steveice10.packetlib.tcp.TcpClientSession;
import net.kyori.adventure.text.Component;
import org.junit.Test;
import org.xbill.DNS.*;
import org.xbill.DNS.Record;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class Test1 {

    @Test
    public void test1() throws TextParseException {
        Record[] records = new Lookup("_minecraft._tcp.play.toiletmc.net", Type.SRV).run();

        for (Record record : records) {
            SRVRecord srvRecord = (SRVRecord) record;
            System.out.println(srvRecord.getTarget().toString(true));
        }
    }

    @Test
    public void test2() throws UnknownHostException, TextParseException, InterruptedException {
        HashMap<String,Integer> returnMap = new HashMap<>();

        SessionService sessionService = new SessionService();
        MinecraftProtocol protocol = new MinecraftProtocol();

        String addr = ServerUtils.getResolve("play.toiletmc.net");

        if (addr == null) throw new UnknownHostException();

        String resolveHost = addr.split(":")[0];
        int resolvePort = Integer.parseInt(addr.split(":")[1]);

        Session client = new TcpClientSession(resolveHost,resolvePort,protocol);


        client.setFlag(MinecraftConstants.SESSION_SERVICE_KEY, sessionService);
        client.setFlag(MinecraftConstants.SERVER_INFO_HANDLER_KEY, new ServerInfoHandler() {
            @Override
            public void handle(Session session, ServerStatusInfo info) {
                System.out.println(info.getPlayerInfo().getPlayers().length);
            }
        });

        client.addListener(new SessionAdapter() {
            @Override
            public void packetReceived(Session session, Packet packet) {
                if (packet instanceof ClientboundStatusResponsePacket) {
                    ClientboundStatusResponsePacket statusPacket = (ClientboundStatusResponsePacket) packet;
                    System.out.println(statusPacket.getInfo().getPlayerInfo().getOnlinePlayers());
                }
            }

            @Override
            public void packetSent(Session session, Packet packet) {
                System.out.println("Sent packet: " + packet.getClass().getSimpleName());
            }

            @Override
            public void connected(ConnectedEvent event) {
                System.out.println("connected");
            }
        });
        client.connect();

        while (true) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }



}
