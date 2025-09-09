package maskun.aimanagedsrs.hexagon.chat.provided;

import maskun.aimanagedsrs.hexagon.chat.UserChatRequest;

public interface Chat {

    String send(UserChatRequest request);
}
