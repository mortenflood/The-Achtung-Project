package com.achtung.game.multiplayer;

import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.listener.ChatRequestListener;

/**
 * Created by mortenflood on 01.03.16.
 */
public class ChatListener implements ChatRequestListener{

    WarpController callBack;

    public ChatListener(WarpController callBack) {
        this.callBack = callBack;
    }

    public void onSendChatDone(byte result) {
        if(result==WarpResponseResultCode.SUCCESS){
            callBack.onSendChatDone(true);
        }else{
            callBack.onSendChatDone(false);
        }
    }

    @Override
    public void onSendPrivateChatDone (byte arg0) {
        // TODO Auto-generated method stub

    }

}
