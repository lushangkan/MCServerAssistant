package cn.cutemc.Utils;

import com.github.steveice10.mc.auth.service.SessionService;
import com.github.steveice10.mc.protocol.MinecraftConstants;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.mc.protocol.data.status.handler.ServerInfoHandler;
import com.github.steveice10.mc.protocol.packet.status.clientbound.ClientboundStatusResponsePacket;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.event.session.SessionAdapter;
import com.github.steveice10.packetlib.packet.Packet;
import com.github.steveice10.packetlib.tcp.TcpClientSession;
import org.xbill.DNS.*;
import org.xbill.DNS.Record;

import java.net.Proxy;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ServerUtils {


    public static HashMap<String,Integer> getServerStatus(String host) throws TextParseException, UnknownHostException, InterruptedException {

        HashMap<String,Integer> returnMap = new HashMap<>();

        SessionService sessionService = new SessionService();
        MinecraftProtocol protocol = new MinecraftProtocol();

        String addr = getResolve(host);

        if (addr == null) throw new UnknownHostException();

        String resolveHost = addr.split(":")[0];
        int resolvePort = Integer.parseInt(addr.split(":")[1]);

        Session client = new TcpClientSession(resolveHost,resolvePort,protocol);

        client.setFlag(MinecraftConstants.SESSION_SERVICE_KEY, sessionService);

        client.addListener(new SessionAdapter() {
                               @Override
                               public void packetReceived(Session session, Packet packet) {
                                   if (packet instanceof ClientboundStatusResponsePacket) {
                                       returnMap.put("playercount", ((ClientboundStatusResponsePacket) packet).getInfo().getPlayerInfo().getOnlinePlayers());
                                       returnMap.put("maxplayercount", ((ClientboundStatusResponsePacket) packet).getInfo().getPlayerInfo().getMaxPlayers());
                                   }
                               }
                           });

        client.connect();


        for (int i = 0; i <= 100; i++) {
            Thread.sleep(100);
            if (!returnMap.isEmpty()) {
                return returnMap;
            }
        }

        return null;

    }

    public static HashMap<String,Integer> getServerStatus(String host, int port) throws TextParseException, UnknownHostException {

        HashMap<String,Integer> returnMap = new HashMap<>();

        SessionService sessionService = new SessionService();
        MinecraftProtocol protocol = new MinecraftProtocol();

        String addr = getResolve(host, port);

        if (addr == null) throw new UnknownHostException();

        String resolveHost = addr.split(":")[0];
        int resolvePort = Integer.parseInt(addr.split(":")[1]);

        Session client = new TcpClientSession(resolveHost,resolvePort,protocol);

        client.setFlag(MinecraftConstants.SESSION_SERVICE_KEY, sessionService);
        client.setFlag(MinecraftConstants.SERVER_INFO_HANDLER_KEY, (ServerInfoHandler) (session, info) -> {
            returnMap.put("playercount", info.getPlayerInfo().getPlayers().length);
            returnMap.put("maxplayercount", info.getPlayerInfo().getMaxPlayers());
        });

        client.connect();

        for (int i = 0; i <= 100; i++) {
            if (client.isConnected()) {
                return returnMap;
            }
        }

        return null;

    }

    public static String resolveSrv(String host) throws TextParseException {
        Record[] records = new Lookup("_minecraft._tcp." + host, Type.SRV).run();
        if (records == null) return null;

        SRVRecord srvRecord = (SRVRecord) records[0];

        return srvRecord.getTarget().toString(true) + ":" + srvRecord.getPort();
    }

    public static String resolveA(String host) throws TextParseException {
        Record[] records = new Lookup(host, Type.A).run();
        if (records == null) return null;

        return records[0].getName().toString(true);
    }

    public static String resolveCNAME(String host) throws TextParseException {
        Record[] records = new Lookup(host, Type.CNAME).run();
        if (records == null) return null;

        return records[0].getName().toString(true);
    }

    public static String getResolve(String host) throws TextParseException {
        String srv = resolveSrv(host);
        if (srv != null) return srv;

        String a = resolveA(host);
        if (a != null) return a + ":25565";

        String CNAME = resolveCNAME(host);
        if (CNAME != null) return CNAME + ":25565";

        return null;
    }

    public static String getResolve(String host,int port) throws TextParseException {
        String srv = resolveSrv(host);
        if (srv != null) return srv;

        String a = resolveA(host);
        if (a != null) return a + port;

        String CNAME = resolveCNAME(host);
        if (CNAME != null) return CNAME + port;

        return null;
    }
}
