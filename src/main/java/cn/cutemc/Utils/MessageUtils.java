package cn.cutemc.Utils;

import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.message.data.*;

public class MessageUtils {

    /**
     * 将文本转换为Reply消息
     * @param text 文本
     * @param source 源
     * @return 消息对象
     */
    public static Message toReplyMsg(String text, MessageSource source) {
        return new PlainText("")
                .plus(new QuoteReply(source))
                .plus(" ")
                .plus(text);
    }

    /**
     * 发送Reply信息
     * @param contact 接收者
     * @param text 文本
     * @param source 源
     * @return 消息回执
     */
    public static MessageReceipt<Contact> sendReplyMsg(Contact contact, String text, MessageSource source) {
        return contact.sendMessage(toReplyMsg(text, source));
    }

    /**
     * 发送Reply信息
     * @param contact 接收者
     * @param message 消息对象
     * @return 消息回执
     */
    public static MessageReceipt<Contact> sendReplyMsg(Contact contact, Message message) {
        return contact.sendMessage(message);
    }
}
