package uk.co.notnull.SingleMaterial;

import co.aikar.locales.MessageKey;
import co.aikar.locales.MessageKeyProvider;

public enum Messages implements MessageKeyProvider {
    MATERIAL__ASSIGNED,
	MATERIAL__REROLLED,
	MATERIAL__BYPASSED,
	MATERIAL__NONE_AVAILABLE,
	MATERIAL__COOLDOWN;


    /**
     * Message keys that grab from the config to send messages
     */
    private final MessageKey key = MessageKey.of(this.name().toLowerCase().replace("__", ".").replace("_", "-"));


    /**
     * Get the message get from the config
     * @return message key
     */
    public MessageKey getMessageKey() {
        return key;
    }
}
