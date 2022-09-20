package cn.cutemc.Utils;

import cn.cutemc.Command.CommandEnum;

public class CommandUtils {

    /**
     * 匹配命令并返回处理类
     * @param textCommand 命令
     * @return 类
     */
    public static Class matchCommand(String textCommand) {
        String mainCommand = getMainCommand(textCommand);

        return CommandEnum.getClassFile(mainCommand);
    }

    /**
     * 获取主命令
     * @param textCommand 命令
     * @return 主命令
     */
    public static String getMainCommand(String textCommand) {
        String[] args = textCommand.replace("/","").split(" ");
        return args[0];
    }

    /**
     * 获取子命令
     * @param textCommand 命令
     * @return 子命令
     */
    public static String[] getArgs(String textCommand) {
        String[] args = textCommand.replace("/","").split(" ");

        String[] newArgs = new String[args.length - 1];
        System.arraycopy(args, 1, newArgs, 0, args.length - 1);

        return newArgs;
    }

    /**
     * 判断字符串是否为指令
     * @param text 字符串
     * @return 是否为指令
     */
    public static Boolean isCommand(String text) {
        return text.matches("/.*");
    }
}
