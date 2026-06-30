package com.pvpshield.customitemreward.utils;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.protocol.packets.entities.EntityUpdates;
import com.hypixel.hytale.protocol.packets.player.ClientMovement;
import com.hypixel.hytale.server.core.io.adapter.PacketAdapters;
import com.hypixel.hytale.server.core.io.adapter.PlayerPacketFilter;

import java.lang.reflect.Field;
import java.util.Set;

public class PacketLogger {

    private static final Set<Class<?>> IGNORED_PACKETS = Set.of(
            ClientMovement.class,  // troppo verboso, commenta se vuoi vederlo
            EntityUpdates.class    // idem
    );

    public static void register() {
        PacketAdapters.registerInbound((PlayerPacketFilter) (playerRef, packet) -> {
            if (IGNORED_PACKETS.contains(packet.getClass())) {
                return false;
            }

            HytaleLogger.getLogger()
                    .atInfo()
                    .log("[PACKET] %s → %s | %s",
                            playerRef.getUsername(),
                            packet.getClass().getSimpleName(),
                            dumpFields(packet)
                    );

            return false;
        });
    }

    private static String dumpFields(Object packet) {
        StringBuilder sb = new StringBuilder("{");
        Field[] fields = packet.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                sb.append(field.getName())
                        .append("=")
                        .append(field.get(packet))
                        .append(", ");
            } catch (IllegalAccessException e) {
                sb.append(field.getName()).append("=<?>, ");
            }
        }

        if (sb.length() > 1) sb.setLength(sb.length() - 2); // rimuovi ultima ", "
        sb.append("}");
        return sb.toString();
    }
}